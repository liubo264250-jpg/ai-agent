package com.liubo.domain.service.armory;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.alibaba.fastjson.JSON;
import com.liubo.domain.model.entity.ArmoryCommandEntity;
import com.liubo.domain.model.valobj.AiClientModelVO;
import com.liubo.domain.model.valobj.constant.AiAgentEnum;
import com.liubo.domain.service.armory.factory.DefaultArmoryStrategyFactory;
import io.modelcontextprotocol.client.McpSyncClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 68
 * 2026/2/11 09:44
 */
@Service
@Slf4j
public class AiClientModelNode extends AbstractArmorySupport {
    @Override
    protected String doApply(ArmoryCommandEntity requestParameter, DefaultArmoryStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("Ai Agent 构建，model 构建节点 {}", JSON.toJSONString(requestParameter));
        List<AiClientModelVO> aiClientModelVOList = dynamicContext.getValue(getDataName());
        if (CollectionUtils.isEmpty(aiClientModelVOList)) {
            log.warn("没有需要被初始化的 ai client model");
            return null;
        }
        for (AiClientModelVO modelVO : aiClientModelVOList) {
            OpenAiApi openAiApi = getBean(AiAgentEnum.AI_CLIENT_API.getBeanName(modelVO.getApiId()));
            if (null == openAiApi) {
                throw new RuntimeException("mode api is null");
            }
            List<McpSyncClient> mcpSyncClientList = modelVO.getToolMcpIds()
                    .stream()
                    .map(mcpId -> this.<McpSyncClient>getBean(AiAgentEnum.AI_CLIENT_TOOL_MCP.getBeanName(mcpId)))
                    .collect(Collectors.toList());
            OpenAiChatModel openAiChatModel = OpenAiChatModel
                    .builder()
                    .openAiApi(openAiApi)
                    .defaultOptions(
                            OpenAiChatOptions
                                    .builder()
                                    .model(modelVO.getModelName())
                                    .toolCallbacks(new SyncMcpToolCallbackProvider(mcpSyncClientList).getToolCallbacks())
                                    .build())
                    .build();
            registerBean(getBeanName(modelVO.getModelId()), OpenAiChatModel.class, openAiChatModel);
        }

        return router(requestParameter, dynamicContext);
    }

    @Override
    public StrategyHandler<ArmoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext, String> get(ArmoryCommandEntity armoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return defaultStrategyHandler;
    }


    @Override
    protected String getDataName() {
        return AiAgentEnum.AI_CLIENT_MODEL.getDataName();
    }

    @Override
    protected String getBeanName(String beanId) {
        return AiAgentEnum.AI_CLIENT_MODEL.getBeanName(beanId);
    }
}
