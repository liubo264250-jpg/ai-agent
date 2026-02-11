package com.liubo.domain.model.valobj.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 68
 * 2026/2/10 08:28
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum AiAgentEnum {

    AI_CLIENT_API("对话API", "api", "ai_client_api_", "ai_client_api_data_list", "aiClientApiLoadDataStrategy"),
    AI_CLIENT_MODEL("对话模型", "model", "ai_client_model_", "ai_client_model_data_list", "aiClientModelLoadDataStrategy"),
    AI_CLIENT_SYSTEM_PROMPT("提示词", "prompt", "ai_client_system_prompt_", "ai_client_system_prompt_data_list", "aiClientSystemPromptLoadDataStrategy"),
    AI_CLIENT_TOOL_MCP("mcp工具", "tool_mcp", "ai_client_tool_mcp_", "ai_client_tool_mcp_data_list", "aiClientToolMCPLoadDataStrategy"),
    AI_CLIENT_ADVISOR("顾问角色", "advisor", "ai_client_advisor_", "ai_client_advisor_data_list", "aiClientAdvisorLoadDataStrategy"),
    AI_CLIENT("客户端", "client", "ai_client_", "ai_client_data_list", "aiClientLoadDataStrategy"),
    ;

    /**
     * 名称
     */
    private String name;

    /**
     * code
     */
    private String code;

    /**
     * Bean 对象名称标签
     */
    private String beanNameTag;

    /**
     * 数据名称
     */
    private String dataName;

    /**
     * 装配数据策略
     */
    private String loadDataStrategy;


    private static final Map<String, AiAgentEnum> ENUM_MAP = new HashMap<>();

    static {
        for (AiAgentEnum aiAgentEnum : values()) {
            ENUM_MAP.put(aiAgentEnum.getCode(), aiAgentEnum);
        }
    }

    /**
     * 根据 code 获取对应枚举
     *
     * @param code 枚举 code
     * @return 匹配的枚举
     * @throws IllegalArgumentException code 为空或找不到对应枚举时抛出
     */
    public static AiAgentEnum getByCode(String code) {
        if (code == null || code.isEmpty()) {
            throw new RuntimeException("code value is Empty!");
        }
        AiAgentEnum result = ENUM_MAP.get(code);
        if (result == null) {
            throw new RuntimeException("code value " + code + " not exist!");
        }
        return result;
    }

    public String getBeanName(String id) {
        return this.beanNameTag + id;
    }
}
