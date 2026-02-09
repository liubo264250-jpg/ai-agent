package com.liubo.infrastructure.dao;

import com.liubo.infrastructure.dao.po.AiAgent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AI智能体配置表 DAO
 *
 * @author ai-agent
 */
@Mapper
public interface IAiAgentDao {

    int insert(AiAgent record);

    AiAgent selectById(@Param("id") Long id);

    int updateById(AiAgent record);

    int deleteById(@Param("id") Long id);

    List<AiAgent> selectList();
}
