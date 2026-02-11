package com.liubo.app;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.liubo.domain.model.entity.ArmoryCommandEntity;
import com.liubo.domain.model.valobj.AiClientToolMcpVO;
import com.liubo.domain.model.valobj.constant.AiAgentEnum;
import com.liubo.domain.service.armory.factory.DefaultArmoryStrategyFactory;
import com.liubo.infrastructure.dao.IAiClientToolMcpDao;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

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
    public void clientToolMcpDao() {
        List<AiClientToolMcpVO> aiClientToolMcpVOS = iAiClientToolMcpDao.queryAiClientToolMcpVOByClientIds(List.of("3001"));
        System.out.println(aiClientToolMcpVOS);
    }
}
