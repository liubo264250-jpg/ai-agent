package com.liubo.infrastructure.dao;

import com.liubo.domain.model.valobj.AiClientSystemPromptVO;
import com.liubo.infrastructure.dao.po.AiClientSystemPrompt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统提示词配置表 DAO
 *
 * @author ai-agent
 */
@Mapper
public interface IAiClientSystemPromptDao {

    int insert(AiClientSystemPrompt record);

    AiClientSystemPrompt selectById(@Param("id") Long id);

    int updateById(AiClientSystemPrompt record);

    int deleteById(@Param("id") Long id);

    List<AiClientSystemPrompt> selectList();

    List<AiClientSystemPromptVO> queryAiClientSystemPromptVOByClientIds(@Param("clientIdList") List<String> clientIdList);
}
