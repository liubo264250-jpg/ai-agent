package com.liubo.domain.adapter.repository;

import com.liubo.domain.model.valobj.AiClientApiVO;

import java.util.List;

/**
 * @author 68
 * 2026/2/10 08:16
 */
public interface IAgentRepository {

    List<AiClientApiVO> queryAiClientApiVOListByClientIds(List<String> clientIdList);
}
