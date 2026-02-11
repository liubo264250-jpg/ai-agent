package com.liubo.domain.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AiClientConfigVO implements Serializable {
    /**
     * 源类型（model、client）
     */
    private String sourceType;
    /**
     * 源ID（如 chatModelId、chatClientId 等）
     */
    private String sourceId;
    /**
     * 目标类型（model、client）
     */
    private String targetType;
    /**
     * 目标ID（如 openAiApiId、chatModelId、systemPromptId、advisorId 等）
     */
    private String targetId;
    /**
     * 扩展参数（JSON格式）
     */
    private String extParam;
}
