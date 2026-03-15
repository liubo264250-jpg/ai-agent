package com.liubo.infrastructure.dao;

import com.liubo.domain.model.valobj.AiClientApiVO;
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

    AiClientApi queryById(@Param("id") Long id);

    int updateById(AiClientApi record);

    int deleteById(@Param("id") Long id);

    List<AiClientApi> queryAll();
    /**
     * 根据 clientId 列表，通过 config -> model -> api 关联查询去重后的 API 配置 VO 列表
     */
    List<AiClientApiVO> queryAiClientApiVOListByClientIds(@Param("clientIdList") List<String> clientIdList);

    int updateByApiId(AiClientApi record);

    int deleteByApiId(@Param("apiId") String apiId);

    AiClientApi queryByApiId(@Param("apiId") String apiId);

    List<AiClientApi> queryEnabledApis();
}
