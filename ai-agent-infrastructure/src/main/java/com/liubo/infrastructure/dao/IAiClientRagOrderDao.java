package com.liubo.infrastructure.dao;

import com.liubo.infrastructure.dao.po.AiClientRagOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 知识库配置表 DAO
 *
 * @author ai-agent
 */
@Mapper
public interface IAiClientRagOrderDao {

    int insert(AiClientRagOrder record);

    AiClientRagOrder selectById(@Param("id") Long id);

    int updateById(AiClientRagOrder record);

    int deleteById(@Param("id") Long id);

    List<AiClientRagOrder> selectList();
}
