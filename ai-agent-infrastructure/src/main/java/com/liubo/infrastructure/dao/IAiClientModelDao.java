package com.liubo.infrastructure.dao;

import com.liubo.infrastructure.dao.po.AiClientModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 聊天模型配置表 DAO
 *
 * @author ai-agent
 */
@Mapper
public interface IAiClientModelDao {

    int insert(AiClientModel record);

    AiClientModel selectById(@Param("id") Long id);

    int updateById(AiClientModel record);

    int deleteById(@Param("id") Long id);

    List<AiClientModel> selectList();
}
