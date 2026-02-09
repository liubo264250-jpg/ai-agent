package com.liubo.infrastructure.dao;

import com.liubo.infrastructure.dao.po.AiAgentFlowConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 智能体-客户端关联表 DAO
 *
 * @author ai-agent
 */
@Mapper
public interface IAiAgentFlowConfigDao {

    int insert(AiAgentFlowConfig record);

    AiAgentFlowConfig selectById(@Param("id") Long id);

    int updateById(AiAgentFlowConfig record);

    int deleteById(@Param("id") Long id);

    List<AiAgentFlowConfig> selectList();
}
