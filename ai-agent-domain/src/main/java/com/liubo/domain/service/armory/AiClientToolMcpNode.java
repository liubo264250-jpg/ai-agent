package com.liubo.domain.service.armory;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.alibaba.fastjson.JSON;
import com.liubo.domain.model.entity.ArmoryCommandEntity;
import com.liubo.domain.model.valobj.AiClientToolMcpVO;
import com.liubo.domain.model.valobj.constant.AiAgentEnum;
import com.liubo.domain.service.armory.factory.DefaultArmoryStrategyFactory;
import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;
import io.modelcontextprotocol.client.transport.ServerParameters;
import io.modelcontextprotocol.client.transport.StdioClientTransport;
import io.modelcontextprotocol.spec.McpSchema;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import static com.liubo.domain.model.valobj.constant.Constant.TRANSPORT_TYPE_SSE;
import static com.liubo.domain.model.valobj.constant.Constant.TRANSPORT_TYPE_STDIO;

/**
 * @author 68
 * 2026/2/11 09:44
 */
@Service
@Slf4j
public class AiClientToolMcpNode extends AbstractArmorySupport {

    @Resource
    private AiClientModelNode aiClientModelNode;

    @Override
    protected String doApply(ArmoryCommandEntity requestParameter, DefaultArmoryStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("Ai Agent 构建，mcp 构建节点 {}", JSON.toJSONString(requestParameter));
        List<AiClientToolMcpVO> aiClientToolMcpVOList = dynamicContext.getValue(getDataName());
        if (CollectionUtils.isEmpty(aiClientToolMcpVOList)) {
            log.warn("没有需要被初始化的 ai client mcp");
            return router(requestParameter, dynamicContext);
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
                HttpClientSseClientTransport sseClientTransport = HttpClientSseClientTransport
                        .builder(baseUri)
                        .sseEndpoint(sseEndpoint)
                        .build();
                McpSyncClient mcpSyncClient = McpClient.sync(sseClientTransport).requestTimeout(Duration.ofMinutes(mcpVO.getRequestTimeout())).build();
                McpSchema.InitializeResult init_sse = mcpSyncClient.initialize();
                log.info("Tool SSE MCP Initialized {}", init_sse);
                return mcpSyncClient;
            }
            case TRANSPORT_TYPE_STDIO -> {
                AiClientToolMcpVO.TransportConfigStdio transportConfigStdio = mcpVO.getTransportConfigStdio();
                Map<String, AiClientToolMcpVO.TransportConfigStdio.Stdio> stdioMap = transportConfigStdio.getStdio();
                AiClientToolMcpVO.TransportConfigStdio.Stdio stdio = stdioMap.get(mcpVO.getMcpName());
                ServerParameters serverParameters = ServerParameters
                        .builder(stdio.getCommand())
                        .args(stdio.getArgs())
                        .env(stdio.getEnv())
                        .build();
                StdioClientTransport stdioClientTransport = new StdioClientTransport(serverParameters);
                McpSyncClient mcpClient = McpClient.sync(stdioClientTransport).requestTimeout(Duration.ofMinutes(mcpVO.getRequestTimeout())).build();
                McpSchema.InitializeResult init_stdio = mcpClient.initialize();
                log.info("Tool Stdio MCP Initialized {}", init_stdio);
                return mcpClient;
            }
        }
        throw new RuntimeException("err! transportType " + transportType + " not exist!");
    }

    @Override
    public StrategyHandler<ArmoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext, String> get(ArmoryCommandEntity armoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return aiClientModelNode;
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
