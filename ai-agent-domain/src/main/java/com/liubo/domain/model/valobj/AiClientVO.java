package com.liubo.domain.model.valobj;

import com.liubo.domain.model.valobj.constant.AiAgentEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AiClientVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 2864505342166459698L;

    /** 主键ID */
    private Long id;
    /** 客户端ID */
    private String clientId;
    /** 客户端名称 */
    private String clientName;
    /** 描述 */
    private String description;
    /**
     * 全局唯一模型ID
     */
    private String modelId;

    /**
     * Prompt ID List
     */
    private List<String> promptIdList;

    /**
     * MCP ID List
     */
    private List<String> mcpIdList;

    /**
     * 顾问ID List
     */
    private List<String> advisorIdList;

    public String getModelBeanName() {
        return AiAgentEnum.AI_CLIENT_MODEL.getBeanName(modelId);
    }

    public List<String> getMcpBeanNameList() {
        List<String> mcpBeanNameList = new ArrayList<>();
        for (String mcpId : mcpIdList) {
            mcpBeanNameList.add(AiAgentEnum.AI_CLIENT_TOOL_MCP.getBeanName(mcpId));
        }
        return mcpBeanNameList;
    }

    public List<String> getAdvisorBeanNameList() {
        List<String> advisorBeanNameList = new ArrayList<>();
        for (String advisorId : advisorIdList) {
            advisorBeanNameList.add(AiAgentEnum.AI_CLIENT_ADVISOR.getBeanName(advisorId));
        }
        return advisorBeanNameList;
    }
}
