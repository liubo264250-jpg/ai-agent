package com.liubo.domain.service.armory.business.data.impl;

import com.alibaba.fastjson.JSON;
import com.liubo.domain.adapter.repository.IAgentRepository;
import com.liubo.domain.model.entity.ArmoryCommandEntity;
import com.liubo.domain.model.valobj.*;
import com.liubo.domain.model.valobj.constant.AiAgentEnum;
import com.liubo.domain.service.armory.business.data.ILoadDataStrategy;
import com.liubo.domain.service.armory.factory.DefaultArmoryStrategyFactory;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import static com.liubo.domain.model.valobj.constant.Constant.*;

/**
 * @author 68
 * 2026/2/10 09:03
 */
@Service
@Slf4j
public class AiClientLoadDataStrategy implements ILoadDataStrategy {

    @Resource
    private IAgentRepository repository;

    @Resource
    protected ThreadPoolExecutor threadPoolExecutor;

    @Override
    public void loadData(ArmoryCommandEntity armoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext dynamicContext) {
        List<String> clientIdList = armoryCommandEntity.getCommandIdList();

        CompletableFuture<List<AiClientApiVO>> aiClientApiListFuture = CompletableFuture.supplyAsync(() -> {
            log.info("查询配置数据(ai_client_api) {}", clientIdList);
            return repository.queryAiClientApiVOListByClientIds(clientIdList);
        }, threadPoolExecutor);

        CompletableFuture<List<AiClientModelVO>> aiClientModelListFuture = CompletableFuture.supplyAsync(() -> {
            log.info("查询配置数据(ai_client_model) {}", clientIdList);
            List<AiClientModelVO> aiClientModelVOList = repository.queryAiClientModelVOByClientIds(clientIdList);
            if (CollectionUtils.isEmpty(aiClientModelVOList)) return List.of();
            List<String> modelIdList = aiClientModelVOList.stream().map(AiClientModelVO::getModelId).collect(Collectors.toList());
            Map<String, List<AiClientConfigVO>> mcpIdMap =  repository.queryAiClientConfigVOBySourceTypeAndId(AiAgentEnum.AI_CLIENT_MODEL.getCode(), modelIdList)
                    .stream()
                    .collect(Collectors.groupingBy(AiClientConfigVO::getSourceId));
            aiClientModelVOList.forEach(model -> {
                List<AiClientConfigVO> aiClientConfigVOList = mcpIdMap.get(model.getModelId());
                if (!CollectionUtils.isEmpty(aiClientConfigVOList)) {
                    List<String> mcpIdList = aiClientConfigVOList.stream().map(AiClientConfigVO::getTargetId).collect(Collectors.toList());
                    model.setToolMcpIds(mcpIdList);
                }
            });
            return aiClientModelVOList;
        }, threadPoolExecutor);

        CompletableFuture<List<AiClientToolMcpVO>> aiClientToolMcpListFuture = CompletableFuture.supplyAsync(() -> {
            log.info("查询配置数据(ai_client_tool_mcp) {}", clientIdList);
            List<AiClientToolMcpVO> aiClientToolMcpVOList = repository.queryAiClientToolMcpVOByClientIds(clientIdList);
            if (CollectionUtils.isEmpty(aiClientToolMcpVOList)) return List.of();
            for (AiClientToolMcpVO mcpVO : aiClientToolMcpVOList) {
                String transportType = mcpVO.getTransportType();
                if (TRANSPORT_TYPE_SSE.equals(transportType)) {
                    // 解析SSE配置
                    AiClientToolMcpVO.TransportConfigSse transportConfigSse = JSON.parseObject(mcpVO.getTransportConfig(), AiClientToolMcpVO.TransportConfigSse.class);
                    mcpVO.setTransportConfigSse(transportConfigSse);
                } else if (TRANSPORT_TYPE_STDIO.equals(transportType)) {
                    // 解析STDIO配置
                    Map<String, AiClientToolMcpVO.TransportConfigStdio.Stdio> stdio =
                            JSON.parseObject(mcpVO.getTransportConfig(), new TypeReference<>() {
                            });
                    AiClientToolMcpVO.TransportConfigStdio transportConfigStdio = new AiClientToolMcpVO.TransportConfigStdio();
                    transportConfigStdio.setStdio(stdio);
                    mcpVO.setTransportConfigStdio(transportConfigStdio);
                }
            }
            return aiClientToolMcpVOList;
        }, threadPoolExecutor);

        CompletableFuture<Map<String, AiClientSystemPromptVO>> aiClientSystemPromptListFuture = CompletableFuture.supplyAsync(() -> {
            log.info("查询配置数据(ai_client_system_prompt) {}", clientIdList);
            Map<String, AiClientSystemPromptVO> systemPromptVOMap = repository.queryAiClientSystemPromptVOByClientIds(clientIdList).stream()
                    .collect(Collectors.toMap(AiClientSystemPromptVO::getPromptId,
                            item -> item, (v1, v2) -> v1));
            return systemPromptVOMap;
        }, threadPoolExecutor);

        CompletableFuture<List<AiClientAdvisorVO>> aiClientAdvisorListFuture = CompletableFuture.supplyAsync(() -> {
            log.info("查询配置数据(ai_client_advisor) {}", clientIdList);
            List<AiClientAdvisorVO> aiClientAdvisorVOList = repository.queryAiClientAdvisorVOByClientIds(clientIdList);
            if (CollectionUtils.isEmpty(aiClientAdvisorVOList)) return List.of();{}
            for (AiClientAdvisorVO advisorVO : aiClientAdvisorVOList) {
                String advisorType = advisorVO.getAdvisorType();
                if (ADVISOR_TYPE_CHAT_MEMORY.equals(advisorType)) {
                    AiClientAdvisorVO.ChatMemory chatMemory = JSON.parseObject(advisorVO.getExtParam(), AiClientAdvisorVO.ChatMemory.class);
                    advisorVO.setChatMemory(chatMemory);
                } else if (ADVISOR_TYPE_RAG_ANSWER.equals(advisorType)) {
                    AiClientAdvisorVO.RagAnswer ragAnswer = JSON.parseObject(advisorVO.getExtParam(), AiClientAdvisorVO.RagAnswer.class);
                    advisorVO.setRagAnswer(ragAnswer);
                }
            }
            return aiClientAdvisorVOList;
        }, threadPoolExecutor);

        CompletableFuture<List<AiClientVO>> aiClientListFuture = CompletableFuture.supplyAsync(() -> {
            log.info("查询配置数据(ai_client) {}", clientIdList);
            List<AiClientVO> aiClientVOList = repository.queryAiClientVOByClientIds(clientIdList);
            List<AiClientConfigVO> aiClientConfigVOList = repository.queryAiClientConfigVOBySourceTypeAndId(AiAgentEnum.AI_CLIENT.getCode(), clientIdList);
            List<String> mcpIdList = aiClientConfigVOList.stream()
                    .filter(config -> AiAgentEnum.AI_CLIENT_MODEL.getCode().equals(config.getTargetType()))
                    .map(AiClientConfigVO::getTargetId)
                    .collect(Collectors.toList());
            Map<String, List<AiClientConfigVO>> clientConfigMap = aiClientConfigVOList.stream().collect(Collectors.groupingBy(AiClientConfigVO::getSourceId));

            Map<String, List<AiClientConfigVO>> modelConfigMap = repository.queryAiClientConfigVOBySourceTypeAndId(AiAgentEnum.AI_CLIENT_MODEL.getCode(), mcpIdList)
                    .stream().collect(Collectors.groupingBy(AiClientConfigVO::getSourceId));
            for (AiClientVO aiClientVO : aiClientVOList) {
                List<AiClientConfigVO> clientConfigList = clientConfigMap.get(aiClientVO.getClientId());
                if (CollectionUtils.isEmpty(clientConfigList)) continue;

                List<String> configAdvisorIdList = new ArrayList<>();
                List<String> configMcpIdList = new ArrayList<>();
                List<String> configPromptIdList = new ArrayList<>();
                String modelId = null;
                for (AiClientConfigVO clientConfigVO : clientConfigList) {
                    String targetType = clientConfigVO.getTargetType();
                    if (AiAgentEnum.AI_CLIENT_MODEL.getCode().equals(targetType)) {
                        modelId = clientConfigVO.getTargetId();
                        List<AiClientConfigVO> modelConfigList = modelConfigMap.get(clientConfigVO.getTargetId());
                        if (!CollectionUtils.isEmpty(modelConfigList)) {
                            modelConfigList.stream().map(AiClientConfigVO::getTargetId).forEach(configMcpIdList::add);
                        }
                    }else if (AiAgentEnum.AI_CLIENT_ADVISOR.getCode().equals(targetType)){
                        configAdvisorIdList.add(clientConfigVO.getTargetId());
                    }else if (AiAgentEnum.AI_CLIENT_SYSTEM_PROMPT.getCode().equals(targetType)){
                        configPromptIdList.add(clientConfigVO.getTargetId());
                    }
                }
                aiClientVO.setModelId(modelId);
                aiClientVO.setAdvisorIdList(configAdvisorIdList);
                aiClientVO.setPromptIdList(configPromptIdList);
                aiClientVO.setMcpIdList(configMcpIdList);
            }
            return aiClientVOList;
        }, threadPoolExecutor);

        CompletableFuture.allOf(
                aiClientApiListFuture,
                aiClientModelListFuture,
                aiClientToolMcpListFuture,
                aiClientSystemPromptListFuture,
                aiClientAdvisorListFuture,
                aiClientListFuture
        ).thenRun(() -> {
            dynamicContext.setValue(AiAgentEnum.AI_CLIENT_API.getDataName(), aiClientApiListFuture.join());
            dynamicContext.setValue(AiAgentEnum.AI_CLIENT_MODEL.getDataName(), aiClientModelListFuture.join());
            dynamicContext.setValue(AiAgentEnum.AI_CLIENT_SYSTEM_PROMPT.getDataName(), aiClientSystemPromptListFuture.join());
            dynamicContext.setValue(AiAgentEnum.AI_CLIENT_TOOL_MCP.getDataName(), aiClientToolMcpListFuture.join());
            dynamicContext.setValue(AiAgentEnum.AI_CLIENT_ADVISOR.getDataName(), aiClientAdvisorListFuture.join());
            dynamicContext.setValue(AiAgentEnum.AI_CLIENT.getDataName(), aiClientListFuture.join());
        }).join();

    }
}
