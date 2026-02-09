package com.liubo.infrastructure.dao;

import com.liubo.infrastructure.dao.po.AiClientAdvisor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 顾问配置表 DAO
 *
 * @author ai-agent
 */
@Mapper
public interface IAiClientAdvisorDao {

    int insert(AiClientAdvisor record);

    AiClientAdvisor selectById(@Param("id") Long id);

    int updateById(AiClientAdvisor record);

    int deleteById(@Param("id") Long id);

    List<AiClientAdvisor> selectList();
}
