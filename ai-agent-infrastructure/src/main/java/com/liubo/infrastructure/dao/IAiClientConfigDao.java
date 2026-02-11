package com.liubo.infrastructure.dao;

import com.liubo.domain.model.valobj.AiClientConfigVO;
import com.liubo.infrastructure.dao.po.AiClientConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AI客户端统一关联配置表 DAO
 *
 * @author ai-agent
 */
@Mapper
public interface IAiClientConfigDao {

    int insert(AiClientConfig record);

    AiClientConfig selectById(@Param("id") Long id);

    int updateById(AiClientConfig record);

    int deleteById(@Param("id") Long id);

    List<AiClientConfig> selectList();

    /**
     * 根据 modelId 列表，查询关联的 MCP ID 列表
     */
    List<AiClientConfigVO> queryAiClientToolMcpIdsByModelIds(@Param("modelIdList") List<String> modelIdList);
}
