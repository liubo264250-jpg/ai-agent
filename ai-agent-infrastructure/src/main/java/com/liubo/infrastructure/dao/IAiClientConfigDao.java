package com.liubo.infrastructure.dao;

import com.liubo.domain.model.valobj.AiClientApiVO;
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
     * 根据 clientId 列表，通过 config -> model -> api 关联查询去重后的 API 配置 VO 列表
     */
    List<AiClientApiVO> queryAiClientApiVOListByClientIds(@Param("clientIdList") List<String> clientIdList);
}
