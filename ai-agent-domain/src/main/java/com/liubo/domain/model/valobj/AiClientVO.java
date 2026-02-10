package com.liubo.domain.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

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
}
