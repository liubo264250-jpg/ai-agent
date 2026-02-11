package com.liubo.domain.service.armory;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.alibaba.fastjson.JSON;
import com.liubo.domain.model.entity.ArmoryCommandEntity;
import com.liubo.domain.model.valobj.AiClientToolMcpVO;
import com.liubo.domain.model.valobj.constant.AiAgentEnum;
import com.liubo.domain.service.armory.factory.DefaultArmoryStrategyFactory;
import io.modelcontextprotocol.client.McpSyncClient;
import lombok.extern.slf4j.Slf4j
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.liubo.domain.model.valobj.constant.Constant.TRANSPORT_TYPE_SSE;
import static com.liubo.domain.model.valobj.constant.Constant.TRANSPORT_TYPE_STDIO;

/**
 * @author 68
 * 2026/2/11 09:44
 */
@Service
@Slf4j
public class AiClientToolMcpNode extends AbstractArmorySupport {
    @Override
    protected String doApply(ArmoryCommandEntity requestParameter, DefaultArmoryStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("Ai Agent 构建，mcp 构建节点 {}", JSON.toJSONString(requestParameter));
        List<AiClientToolMcpVO> aiClientToolMcpVOList = dynamicContext.getValue(getDataName());
        if (CollectionUtils.isEmpty(aiClientToolMcpVOList)) {
            log.warn("没有需要被初始化的 ai client mcp");
            return null;
        }
        for (AiClientToolMcpVO mcpVO : aiClientToolMcpVOList) {
            // 创建 MCP 服务
            McpSyncClient mcpSyncClient = createMcpSyncClient(mcpVO);

            // 注册 MCP 对象
            registerBean(getBeanName(mcpVO.getMcpId()), McpSyncClient.class, mcpSyncClient);
        }
        return router(requestParameter, dynamicContext);
    }

    protected McpSyncClient createMcpSyncClient(AiClientToolMcpVO mcpVO) {
        String transportType = mcpVO.getTransportType();

        switch (transportType) {
            case TRANSPORT_TYPE_SSE -> {
                AiClientToolMcpVO.TransportConfigSse transportConfigSse = mcpVO.getTransportConfigSse();
                // http://127.0.0.1:9999/sse?apikey=DElk89iu8Ehhnbu
                String originalBaseUri = transportConfigSse.getBaseUri();
                String baseUri;
                String sseEndpoint;

                int queryParamStartIndex = originalBaseUri.indexOf("sse");
                if (queryParamStartIndex != -1) {
                    baseUri = originalBaseUri.substring(0, queryParamStartIndex - 1);
                    sseEndpoint = originalBaseUri.substring(queryParamStartIndex - 1);
                } else {
                    baseUri = originalBaseUri;
                    sseEndpoint = transportConfigSse.getSseEndpoint();
                }

                sseEndpoint = StringUtils.isBlank(sseEndpoint) ? "/sse" : sseEndpoint;
                return null;
            }
            case TRANSPORT_TYPE_STDIO -> {
                return null;
            }
        }
        throw new RuntimeException("err! transportType " + transportType + " not exist!");
    }

    @Override
    public StrategyHandler<ArmoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext, String> get(ArmoryCommandEntity armoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return defaultStrategyHandler;
    }

    @Override
    protected String getDataName() {
        return AiAgentEnum.AI_CLIENT_TOOL_MCP.getDataName();
    }

    @Override
    protected String getBeanName(String beanId) {
        return AiAgentEnum.AI_CLIENT_TOOL_MCP.getBeanName(beanId);
    }

}
