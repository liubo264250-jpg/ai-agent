package com.liubo.domain.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 68
 * 2026/3/8 10:23
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AiAgentTaskScheduleVO {
    /** 主键ID */
    private Long id;
    /** 智能体ID */
    private String agentId;
    /** 任务名称 */
    private String taskName;
    /** 任务描述 */
    private String description;
    /** 时间表达式(如: 0/3 * * * * *) */
    private String cronExpression;
    /** 任务入参配置(JSON格式) */
    private String taskParam;
    /** 状态(0:无效,1:有效) */
    private Integer status;
}
