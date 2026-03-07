package com.liubo.domain.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AiAgentFlowConfigVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 4580163102760060779L;
    /** 主键ID */
    private Long id;
    /** 智能体ID */
    private String agentId;
    /** 客户端ID */
    private String clientId;
    private String clientName;
    private String clientType;
    /** 序列号(执行顺序) */
    private Integer sequence;
    private String stepPrompt;
}
