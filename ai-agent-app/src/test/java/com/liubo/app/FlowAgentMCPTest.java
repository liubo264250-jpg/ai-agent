package com.liubo.app;

import com.alibaba.fastjson.JSON;
import com.liubo.domain.model.entity.ExecuteCommandEntity;
import com.liubo.domain.model.valobj.AiAgentTaskScheduleVO;
import com.liubo.domain.service.IAgentDispatchService;
import com.liubo.domain.service.ITaskService;
import com.liubo.types.job.model.TaskScheduleVO;
import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.ServerParameters;
import io.modelcontextprotocol.client.transport.StdioClientTransport;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 68
 * 2026/3/7 14:29
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class FlowAgentMCPTest {

    @Resource
    private ITaskService taskService;

    @Resource
    private IAgentDispatchService dispatchService;

    @Test
    public void test() {
        OpenAiChatModel chatModel = OpenAiChatModel.builder()
                .openAiApi(OpenAiApi.builder()
                        .baseUrl("https://apis.itedus.cn")
                        .apiKey("sk-rHYRhuAbH9FVTPI3409a5e97Dd85435cAf14E3E0C2F15e55")
                        .completionsPath("v1/chat/completions")
                        .embeddingsPath("v1/embeddings")
                        .build())
                .defaultOptions(OpenAiChatOptions.builder()
                        .model("gpt-4.1")
                        .toolCallbacks(new SyncMcpToolCallbackProvider(stdioMcpClientGrafana()).getToolCallbacks())
                        .build())
                .build();
        ChatResponse call = chatModel.call(Prompt.builder().messages(new UserMessage("有哪些工具可以使用")).build());
        log.info("测试结果:{}", JSON.toJSONString(call.getResult()));
    }

    public McpSyncClient stdioMcpClientElasticsearch() {
        Map<String, String> env = new HashMap<>();
        env.put("ES_HOST", "http://127.0.0.1:9200");
        var stdioParams = ServerParameters.builder("npx")
                .args("-y", "@awesome-ai/elasticsearch-mcp")
                .env(env)
                .build();
        McpSyncClient mcpSyncClient = McpClient.sync(new StdioClientTransport(stdioParams)).requestTimeout(Duration.ofSeconds(100)).build();
        mcpSyncClient.initialize();
        return mcpSyncClient;
    }
    public McpSyncClient stdioMcpClientGrafana() {
        Map<String, String> env = new HashMap<>();
        env.put("GRAFANA_URL", "http://127.0.0.1:4000");
        env.put("GRAFANA_API_KEY", "TOKEN");
        var stdioParams = ServerParameters.builder("docker")
                .args("run",
                        "--rm",
                        "-i",
                        "-e",
                        "GRAFANA_URL",
                        "-e",
                        "GRAFANA_API_KEY",
                        "mcp/grafana",
                        "-t",
                        "stdio")
                .env(env)
                .build();
        McpSyncClient mcpSyncClient = McpClient.sync(new StdioClientTransport(stdioParams)).requestTimeout(Duration.ofSeconds(100)).build();
        mcpSyncClient.initialize();
        return mcpSyncClient;
    }

    @Test
    public void  test1() throws Exception {
        List<AiAgentTaskScheduleVO> aiAgentTaskScheduleVOS = taskService.queryAllValidTaskSchedule();
        List<TaskScheduleVO> result = new ArrayList<>();
        for (AiAgentTaskScheduleVO taskScheduleVO : aiAgentTaskScheduleVOS) {
            dispatchService.dispatch(
                    ExecuteCommandEntity.builder()
                            .aiAgentId(taskScheduleVO.getAgentId())
                            .sessionId(String.valueOf(System.nanoTime()))
                            .message(taskScheduleVO.getTaskParam())
                            .maxStep(1)
                            .build(), new ResponseBodyEmitter());
        }
    }
}
