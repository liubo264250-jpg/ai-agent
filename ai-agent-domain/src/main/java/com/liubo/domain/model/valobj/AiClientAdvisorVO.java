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
public class AiClientAdvisorVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 702196551199028966L;
    /** 主键ID */
    private Long id;
    /** 顾问ID */
    private String advisorId;
    /** 顾问名称 */
    private String advisorName;
    /** 顾问类型(PromptChatMemory/RagAnswer/SimpleLoggerAdvisor等) */
    private String advisorType;
    /** 顺序号 */
    private Integer orderNum;
    /** 扩展参数配置，json 记录 */
    private String extParam;
    private ChatMemory chatMemory;
    private RagAnswer ragAnswer;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChatMemory {
        private Integer maxMessages;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RagAnswer {
        private Integer topK;
        private String filterExpression;
    }
}
