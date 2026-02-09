package com.liubo.infrastructure.dao;

import com.liubo.infrastructure.dao.po.AiClientApi;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * OpenAI API配置表 DAO
 *
 * @author ai-agent
 */
@Mapper
public interface IAiClientApiDao {

    int insert(AiClientApi record);

    AiClientApi selectById(@Param("id") Long id);

    int updateById(AiClientApi record);

    int deleteById(@Param("id") Long id);

    List<AiClientApi> selectList();
}
