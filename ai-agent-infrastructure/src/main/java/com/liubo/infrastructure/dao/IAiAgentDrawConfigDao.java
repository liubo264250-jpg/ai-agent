package com.liubo.infrastructure.dao;

import com.liubo.infrastructure.dao.po.AiAgentDrawConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AI智能体拖拉拽配置主表 DAO
 *
 * 表名: ai_agent_draw_config
 */
@Mapper
public interface IAiAgentDrawConfigDao {

    int insert(AiAgentDrawConfig record);

    AiAgentDrawConfig selectById(@Param("id") Long id);

    int updateById(AiAgentDrawConfig record);

    int deleteById(@Param("id") Long id);

    List<AiAgentDrawConfig> selectList();

    AiAgentDrawConfig queryByConfigId(@Param("configId") String configId);

    List<AiAgentDrawConfig> queryByConfigName(@Param("configName") String configName);

    AiAgentDrawConfig queryByAgentId(@Param("agentId") String agentId);

    List<AiAgentDrawConfig> queryEnabledConfigs();

    List<AiAgentDrawConfig> queryAll();

    int updateByConfigId(AiAgentDrawConfig record);

    int deleteByConfigId(@Param("configId") String configId);
}

