package com.liubo.domain.model.entity;

import com.liubo.domain.model.valobj.constant.AiAgentEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author 68
 * 2026/2/10 08:13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArmoryCommandEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -7472995024351328454L;
    /**
     * 命令类型
     */
    private String commandType;

    /**
     * 命令索引（clientId、modelId、apiId...）
     */
    private List<String> commandIdList;

    public String getLoadDataStrategy() {
        return AiAgentEnum.getByCode(commandType).getLoadDataStrategy();
    }
}
