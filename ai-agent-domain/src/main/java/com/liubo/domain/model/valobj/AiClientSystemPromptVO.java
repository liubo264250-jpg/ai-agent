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
public class AiClientSystemPromptVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 7532342934747893736L;
    /** 主键ID */
    private Long id;
    /** 提示词ID */
    private String promptId;
    /** 提示词名称 */
    private String promptName;
    /** 提示词内容 */
    private String promptContent;
    /** 描述 */
    private String description;
}
