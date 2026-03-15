package com.liubo.domain.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 68
 * 2026/3/8 13:39
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AiRagOrderVO {
    /** 主键ID */
    private Long id;
    /** 知识库ID */
    private String ragId;
    /** 知识库名称 */
    private String ragName;
    /** 知识标签 */
    private String knowledgeTag;
}
