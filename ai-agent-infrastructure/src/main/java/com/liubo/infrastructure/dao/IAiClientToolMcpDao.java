package com.liubo.infrastructure.dao;

import com.liubo.domain.model.valobj.AiClientToolMcpVO;
import com.liubo.infrastructure.dao.po.AiClientToolMcp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * MCP客户端配置表 DAO
 *
 * @author ai-agent
 */
@Mapper
public interface IAiClientToolMcpDao {

    int insert(AiClientToolMcp record);

    AiClientToolMcp selectById(@Param("id") Long id);

    int updateById(AiClientToolMcp record);

    int deleteById(@Param("id") Long id);

    List<AiClientToolMcp> selectList();

    /**
     * 根据 clientId 列表，通过 config(a) -> config(b) -> tool_mcp(c) 关联查询 MCP 配置 VO 列表
     */
    List<AiClientToolMcpVO> queryAiClientToolMcpVOByClientIds(@Param("clientIdList") List<String> clientIdList);
}
