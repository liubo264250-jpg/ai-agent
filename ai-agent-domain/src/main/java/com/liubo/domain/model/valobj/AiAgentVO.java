package com.liubo.domain.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 68
 * 2026/3/8 09:10
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AiAgentVO {
    /** 主键ID */
    private Long id;
    /** 智能体ID */
    private String agentId;
    /** 智能体名称 */
    private String agentName;
    /** 描述 */
    private String description;
    /** 渠道类型(agent，chat_stream) */
    private String channel;
    /** 策略 **/
    private String strategy;
    private Integer status;
}
