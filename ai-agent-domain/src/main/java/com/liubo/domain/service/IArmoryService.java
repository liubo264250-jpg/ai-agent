package com.liubo.domain.service;

import com.liubo.domain.model.valobj.AiAgentVO;

import java.util.List;

/**
 * @author 68
 * 2026/3/8 13:36
 */
public interface IArmoryService {
    List<AiAgentVO> acceptArmoryAllAvailableAgents();

    void acceptArmoryAgent(String agentId);

    List<AiAgentVO> queryAvailableAgents();

    void acceptArmoryAgentClientModelApi(String apiId);
}
