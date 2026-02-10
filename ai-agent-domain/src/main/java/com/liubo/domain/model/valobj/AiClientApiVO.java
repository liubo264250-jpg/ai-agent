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
public class AiClientApiVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 4413543465245844437L;
    /** 自增主键ID */
    private Long id;
    /** 全局唯一配置ID */
    private String apiId;
    /** API基础URL */
    private String baseUrl;
    /** API密钥 */
    private String apiKey;
    /** 补全API路径 */
    private String completionsPath;
    /** 嵌入API路径 */
    private String embeddingsPath;
}
