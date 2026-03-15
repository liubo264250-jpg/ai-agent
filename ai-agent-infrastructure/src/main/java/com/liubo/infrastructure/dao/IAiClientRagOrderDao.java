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

    AiClientRagOrder queryById(@Param("id") Long id);

    int updateById(AiClientRagOrder record);

    int deleteById(@Param("id") Long id);

    List<AiClientRagOrder> queryAll();

    int updateByRagId(AiClientRagOrder record);

    int deleteByRagId(@Param("ragId") String ragId);

    AiClientRagOrder queryByRagId(@Param("ragId") String ragId);

    List<AiClientRagOrder> queryEnabledRagOrders();

    List<AiClientRagOrder> queryByKnowledgeTag(@Param("knowledgeTag") String knowledgeTag);
}
