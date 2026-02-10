package com.liubo.domain.service.armory.business.data.impl;

import com.liubo.domain.adapter.repository.IAgentRepository;
import com.liubo.domain.model.entity.ArmoryCommandEntity;
import com.liubo.domain.model.valobj.*;
import com.liubo.domain.model.valobj.constant.AiAgentEnum;
import com.liubo.domain.service.armory.business.data.ILoadDataStrategy;
import com.liubo.domain.service.armory.factory.DefaultArmoryStrategyFactory;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

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
            return repository.queryAiClientModelVOByClientIds(clientIdList);
        }, threadPoolExecutor);

        CompletableFuture<List<AiClientToolMcpVO>> aiClientToolMcpListFuture = CompletableFuture.supplyAsync(() -> {
            log.info("查询配置数据(ai_client_tool_mcp) {}", clientIdList);
            return repository.queryAiClientToolMcpVOByClientIds(clientIdList);
        }, threadPoolExecutor);

        CompletableFuture<List<AiClientSystemPromptVO>> aiClientSystemPromptListFuture = CompletableFuture.supplyAsync(() -> {
            log.info("查询配置数据(ai_client_system_prompt) {}", clientIdList);
            return repository.queryAiClientSystemPromptVOByClientIds(clientIdList);
        }, threadPoolExecutor);

        CompletableFuture<List<AiClientAdvisorVO>> aiClientAdvisorListFuture = CompletableFuture.supplyAsync(() -> {
            log.info("查询配置数据(ai_client_advisor) {}", clientIdList);
            return repository.queryAiClientAdvisorVOByClientIds(clientIdList);
        }, threadPoolExecutor);

        CompletableFuture<List<AiClientVO>> aiClientListFuture = CompletableFuture.supplyAsync(() -> {
            log.info("查询配置数据(ai_client) {}", clientIdList);
            return repository.queryAiClientVOByClientIds(clientIdList);
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
            dynamicContext.setValue(AiAgentEnum.AI_CLIENT_SYSTEM_PROMPT.getDataName(), aiClientToolMcpListFuture.join());
            dynamicContext.setValue(AiAgentEnum.AI_CLIENT_TOOL_MCP.getDataName(), aiClientSystemPromptListFuture.join());
            dynamicContext.setValue(AiAgentEnum.AI_CLIENT_ADVISOR.getDataName(), aiClientAdvisorListFuture.join());
            dynamicContext.setValue(AiAgentEnum.AI_CLIENT.getDataName(), aiClientListFuture.join());
        }).join();

    }
}
