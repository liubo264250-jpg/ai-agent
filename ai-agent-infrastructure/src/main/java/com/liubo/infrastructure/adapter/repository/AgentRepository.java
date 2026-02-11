package com.liubo.infrastructure.adapter.repository;

import com.liubo.domain.adapter.repository.IAgentRepository;
import com.liubo.domain.model.valobj.*;
import com.liubo.infrastructure.dao.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author 68
 * 2026/2/10 08:23
 */
@Slf4j
@Repository
public class AgentRepository implements IAgentRepository {

    @Resource
    private IAiAgentDao aiAgentDao;

    @Resource
    private IAiAgentFlowConfigDao aiAgentFlowConfigDao;

    @Resource
    private IAiAgentTaskScheduleDao aiAgentTaskScheduleDao;

    @Resource
    private IAiClientAdvisorDao aiClientAdvisorDao;

    @Resource
    private IAiClientApiDao aiClientApiDao;

    @Resource
    private IAiClientConfigDao aiClientConfigDao;

    @Resource
    private IAiClientDao aiClientDao;

    @Resource
    private IAiClientModelDao aiClientModelDao;

    @Resource
    private IAiClientRagOrderDao aiClientRagOrderDao;

    @Resource
    private IAiClientSystemPromptDao aiClientSystemPromptDao;

    @Resource
    private IAiClientToolMcpDao aiClientToolMcpDao;

    @Override
    public List<AiClientApiVO> queryAiClientApiVOListByClientIds(List<String> clientIdList) {
        if (CollectionUtils.isEmpty(clientIdList)) return List.of();
        return aiClientApiDao.queryAiClientApiVOListByClientIds(clientIdList);
    }

    @Override
    public List<AiClientModelVO> queryAiClientModelVOByClientIds(List<String> clientIdList) {
        return List.of();
    }

    @Override
    public List<AiClientToolMcpVO> queryAiClientToolMcpVOByClientIds(List<String> clientIdList) {
        if (CollectionUtils.isEmpty(clientIdList)) return List.of();
        return aiClientToolMcpDao.queryAiClientToolMcpVOByClientIds(clientIdList);
    }

    @Override
    public List<AiClientSystemPromptVO> queryAiClientSystemPromptVOByClientIds(List<String> clientIdList) {
        return List.of();
    }

    @Override
    public List<AiClientAdvisorVO> queryAiClientAdvisorVOByClientIds(List<String> clientIdList) {
        return List.of();
    }

    @Override
    public List<AiClientVO> queryAiClientVOByClientIds(List<String> clientIdList) {
        return List.of();
    }
}
