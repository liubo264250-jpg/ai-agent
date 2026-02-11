package com.liubo.infrastructure.dao;

import com.liubo.domain.model.valobj.AiClientModelVO;
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

    /**
     * 根据 clientId 列表，通过 config -> model 关联查询模型配置 VO 列表（包含关联的 MCP 工具）
     */
    List<AiClientModelVO> queryAiClientModelVOByClientIds(@Param("clientIdList") List<String> clientIdList);
}
