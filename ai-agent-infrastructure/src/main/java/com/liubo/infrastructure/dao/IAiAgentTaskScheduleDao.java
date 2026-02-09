package com.liubo.infrastructure.dao;

import com.liubo.infrastructure.dao.po.AiAgentTaskSchedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 智能体任务调度配置表 DAO
 *
 * @author ai-agent
 */
@Mapper
public interface IAiAgentTaskScheduleDao {

    int insert(AiAgentTaskSchedule record);

    AiAgentTaskSchedule selectById(@Param("id") Long id);

    int updateById(AiAgentTaskSchedule record);

    int deleteById(@Param("id") Long id);

    List<AiAgentTaskSchedule> selectList();
}
