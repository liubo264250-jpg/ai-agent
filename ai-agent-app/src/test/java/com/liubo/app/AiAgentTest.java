package com.liubo.app;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.liubo.domain.model.entity.ArmoryCommandEntity;
import com.liubo.domain.model.valobj.constant.AiAgentEnum;
import com.liubo.domain.service.armory.factory.DefaultArmoryStrategyFactory;
import com.liubo.infrastructure.dao.IAiClientToolMcpDao;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @author 68
 * 2026/2/10 22:24
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AiAgentTest {

    @Autowired
    private DefaultArmoryStrategyFactory defaultArmoryStrategyFactory;

    @Autowired
    private ApplicationContext applicationContext;

    @Resource
    private IAiClientToolMcpDao iAiClientToolMcpDao;

    @Test
    public void test_aiClientApiNode() throws Exception {
        StrategyHandler<ArmoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext, String> strategyHandler
                = defaultArmoryStrategyFactory.armoryStrategyHandler();

        String apply = strategyHandler.apply(
                ArmoryCommandEntity.
                        builder()
                        .commandType(AiAgentEnum.AI_CLIENT.getCode())
                        .commandIdList(List.of("3001"))
                        .build()
                , new DefaultArmoryStrategyFactory.DynamicContext());
        OpenAiApi openAiApi = (OpenAiApi) applicationContext.getBean(AiAgentEnum.AI_CLIENT_API.getBeanName("1001"));
        log.info("测试结果：{}", openAiApi);
    }

    @Test
    public void test_aiClientModelNode() throws Exception {
        StrategyHandler<ArmoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext, String> strategyHandler
                = defaultArmoryStrategyFactory.armoryStrategyHandler();

        String apply = strategyHandler.apply(
                ArmoryCommandEntity.
                        builder()
                        .commandType(AiAgentEnum.AI_CLIENT.getCode())
                        .commandIdList(List.of("3001"))
                        .build()
                , new DefaultArmoryStrategyFactory.DynamicContext());
        OpenAiChatModel openAiChatModel = (OpenAiChatModel) applicationContext.getBean(AiAgentEnum.AI_CLIENT_MODEL.getBeanName("2001"));
        log.info("模型构建:{}", openAiChatModel);
    }

    @Test
    public void test_aiClient() throws Exception {
        StrategyHandler<ArmoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext, String> armoryStrategyHandler =
                defaultArmoryStrategyFactory.armoryStrategyHandler();
        String apply = armoryStrategyHandler.apply(
                ArmoryCommandEntity.builder()
                        .commandType(AiAgentEnum.AI_CLIENT.getCode())
                        .commandIdList(Arrays.asList("3001"))
                        .build(),
                new DefaultArmoryStrategyFactory.DynamicContext());
        ChatClient chatClient = (ChatClient) applicationContext.getBean(AiAgentEnum.AI_CLIENT.getBeanName("3001"));
        log.info("客户端构建:{}", chatClient);
        String content = chatClient.prompt(Prompt.builder()
                .messages(new UserMessage(
                        """
                                有哪些工具可以使用
                                """))
                .build()).call().content();

        log.info("测试结果(call):{}", content);
    }
}
