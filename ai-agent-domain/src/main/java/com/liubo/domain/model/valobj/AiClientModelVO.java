package com.liubo.domain.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AiClientModelVO {
    /**
     * 自增主键ID
     */
    private Long id;
    /**
     * 全局唯一模型ID
     */
    private String modelId;
    /**
     * 关联的API配置ID
     */
    private String apiId;
    /**
     * 模型名称
     */
    private String modelName;
    /**
     * 模型类型：openai、deepseek、claude
     */
    private String modelType;
    /**
     * 工具 mcp ids
     */
    private List<String> toolMcpIds;
}
