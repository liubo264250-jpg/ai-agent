package com.liubo.infrastructure.dao;

import com.liubo.infrastructure.dao.po.AiClient;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AI客户端配置表 DAO
 *
 * @author ai-agent
 */
@Mapper
public interface IAiClientDao {

    int insert(AiClient record);

    AiClient selectById(@Param("id") Long id);

    int updateById(AiClient record);

    int deleteById(@Param("id") Long id);

    List<AiClient> selectList();
}
