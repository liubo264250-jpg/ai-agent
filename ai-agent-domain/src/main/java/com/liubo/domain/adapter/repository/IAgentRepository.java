package com.liubo.domain.adapter.repository;

import com.liubo.domain.model.valobj.*;

import java.util.List;
import java.util.Map;

/**
 * @author 68
 * 2026/2/10 08:16
 */
public interface IAgentRepository {

    List<AiClientApiVO> queryAiClientApiVOListByClientIds(List<String> clientIdList);

    List<AiClientModelVO> queryAiClientModelVOByClientIds(List<String> clientIdList);

    List<AiClientToolMcpVO> queryAiClientToolMcpVOByClientIds(List<String> clientIdList);

    List<AiClientSystemPromptVO> queryAiClientSystemPromptVOByClientIds(List<String> clientIdList);

    List<AiClientAdvisorVO> queryAiClientAdvisorVOByClientIds(List<String> clientIdList);

    List<AiClientVO> queryAiClientVOByClientIds(List<String> clientIdList);

    List<AiClientConfigVO> queryAiClientConfigVOBySourceTypeAndId(String sourceType,List<String> sourceIdList);

    List<AiAgentFlowConfigVO> queryAiAgentClientFlowConfigByAgentId(String aiAgentId);

    Map<String,AiAgentFlowConfigVO> queryAiAgentClientFlowConfigMapByAgentId(String aiAgentId);
}
