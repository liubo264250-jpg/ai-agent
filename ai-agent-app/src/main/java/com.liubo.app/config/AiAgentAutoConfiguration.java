package com.liubo.app.config;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.liubo.domain.model.entity.ArmoryCommandEntity;
import com.liubo.domain.model.valobj.constant.AiAgentEnum;
import com.liubo.domain.service.armory.node.factory.DefaultArmoryStrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
@EnableConfigurationProperties(AiAgentAutoConfigProperties.class)
@ConditionalOnProperty(prefix = "spring.ai.agent.auto-config", name = "enabled", havingValue = "true")
public class AiAgentAutoConfiguration implements ApplicationListener<ApplicationReadyEvent> {
    @Resource
    private AiAgentAutoConfigProperties aiAgentAutoConfigProperties;

    @Resource
    private DefaultArmoryStrategyFactory defaultArmoryStrategyFactory;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            log.info("AI Agent 自动装配开始，配置: {}", aiAgentAutoConfigProperties);

            // 检查配置是否有效
            if (!aiAgentAutoConfigProperties.isEnabled()) {
                log.info("AI Agent 自动装配未启用");
                return;
            }

            String clientIds = aiAgentAutoConfigProperties.getClientIds();
            if (StringUtils.isBlank(clientIds)) {
                log.warn("AI Agent 自动装配配置的客户端ID列表为空");
                return;
            }
            List<String> commandIdList = Arrays.stream(clientIds.split(",")).toList();
            log.info("开始自动装配AI客户端，客户端ID列表: {}", commandIdList);
            StrategyHandler<ArmoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext, String> handler =
                    defaultArmoryStrategyFactory.armoryStrategyHandler();
            String result = handler.apply(ArmoryCommandEntity.builder()
                    .commandType(AiAgentEnum.AI_CLIENT.getCode())
                    .commandIdList(commandIdList)
                    .build(), new DefaultArmoryStrategyFactory.DynamicContext());
            log.info("AI Agent 自动装配完成，结果: {}", result);
        } catch (Exception e) {
            log.error("AI Agent 自动装配失败", e);
        }
    }
}
