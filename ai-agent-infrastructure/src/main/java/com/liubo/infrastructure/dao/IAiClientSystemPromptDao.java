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

    AiClientSystemPrompt queryById(@Param("id") Long id);

    int updateById(AiClientSystemPrompt record);

    int deleteById(@Param("id") Long id);

    List<AiClientSystemPrompt> queryAll();

    List<AiClientSystemPromptVO> queryAiClientSystemPromptVOByClientIds(@Param("clientIdList") List<String> clientIdList);

    int updateByPromptId(AiClientSystemPrompt record);

    int deleteByPromptId(@Param("promptId") String promptId);

    AiClientSystemPrompt queryByPromptId(@Param("promptId") String promptId);

    List<AiClientSystemPrompt> queryEnabledPrompts();

    List<AiClientSystemPrompt> queryByPromptName(@Param("promptName") String promptName);
}
