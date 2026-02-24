package com.liubo.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "pring.ai.agent.auto-config")
public class AiAgentAutoConfigProperties {

    private boolean enabled;

    private String clientIds;
}
