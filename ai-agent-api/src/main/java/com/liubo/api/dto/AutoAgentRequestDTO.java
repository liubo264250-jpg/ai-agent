package com.liubo.api.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author 68
 * 2026/3/1 10:14
 */
@Data
public class AutoAgentRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 973172718362751463L;

    private String aiAgentId;

    private String message;

    private String sessionId;

    private Integer maxStep;
}
