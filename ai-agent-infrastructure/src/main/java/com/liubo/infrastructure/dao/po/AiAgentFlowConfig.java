package com.liubo.infrastructure.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 智能体-客户端关联表
 * 表名: ai_agent_flow_config
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AiAgentFlowConfig {

    /** 主键ID */
    private Long id;
    /** 智能体ID */
    private Long agentId;
    /** 客户端ID */
    private Long clientId;
    /** 序列号(执行顺序) */
    private Integer sequence;
    /** 创建时间 */
    private LocalDateTime createTime;
}
