package com.liubo.infrastructure.dao;

import com.liubo.domain.model.valobj.AiClientAdvisorVO;
import com.liubo.infrastructure.dao.po.AiClientAdvisor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 顾问配置表 DAO
 *
 * @author ai-agent
 */
@Mapper
public interface IAiClientAdvisorDao {

    int insert(AiClientAdvisor record);

    AiClientAdvisor queryById(@Param("id") Long id);

    int updateById(AiClientAdvisor record);

    int deleteById(@Param("id") Long id);

    List<AiClientAdvisor> queryAll();

    List<AiClientAdvisorVO> queryAiClientAdvisorVOByClientIds(@Param("clientIdList") List<String> clientIdList);

    int updateByAdvisorId(AiClientAdvisor record);

    int deleteByAdvisorId(@Param("advisorId") String advisorId);

    AiClientAdvisor queryByAdvisorId(@Param("advisorId") String advisorId);

    List<AiClientAdvisor> queryByStatus(@Param("status") Integer status);

    List<AiClientAdvisor> queryByAdvisorType(@Param("advisorType") String advisorType);
}
