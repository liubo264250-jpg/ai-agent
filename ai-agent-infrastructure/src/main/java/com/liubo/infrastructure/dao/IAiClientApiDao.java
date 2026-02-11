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

    AiClientApi selectById(@Param("id") Long id);

    int updateById(AiClientApi record);

    int deleteById(@Param("id") Long id);

    List<AiClientApi> selectList();
    /**
     * 根据 clientId 列表，通过 config -> model -> api 关联查询去重后的 API 配置 VO 列表
     */
    List<AiClientApiVO> queryAiClientApiVOListByClientIds(@Param("clientIdList") List<String> clientIdList);
}
