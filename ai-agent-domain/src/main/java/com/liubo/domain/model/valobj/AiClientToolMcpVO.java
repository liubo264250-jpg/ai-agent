package com.liubo.domain.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AiClientToolMcpVO {
    /** 主键ID */
    private Long id;
    /** MCP名称 */
    private String mcpId;
    /** MCP名称 */
    private String mcpName;
    /** 传输类型(sse/stdio) */
    private String transportType;
    /** 传输配置(sse/stdio) */
    private String transportConfig;
    /** 请求超时时间(分钟) */
    private Integer requestTimeout;
}
