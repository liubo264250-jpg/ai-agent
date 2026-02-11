package com.liubo.domain.service.armory;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.alibaba.fastjson.JSON;
import com.liubo.domain.model.entity.ArmoryCommandEntity;
import com.liubo.domain.model.valobj.AiClientApiVO;
import com.liubo.domain.model.valobj.constant.AiAgentEnum;
import com.liubo.domain.service.armory.factory.DefaultArmoryStrategyFactory;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
public class AiClientApiNode extends AbstractArmorySupport {

    @Resource
    private AiClientToolMcpNode aiClientToolMcpNode;

    @Override
    protected String doApply(ArmoryCommandEntity requestParameter, DefaultArmoryStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("Ai Agent 构建，API 构建节点 {}", JSON.toJSONString(requestParameter));
        List<AiClientApiVO> aiClientApiVOList = dynamicContext.getValue(getDataName());
        if (CollectionUtils.isEmpty(aiClientApiVOList)) {
            log.warn("没有需要被初始化的 ai client api");
            return null;
        }
        for (AiClientApiVO aiClientApiVO : aiClientApiVOList) {
            // 构建 OpenAiApi
            OpenAiApi openAiApi = OpenAiApi.builder()
                    .apiKey(aiClientApiVO.getApiKey())
                    .baseUrl(aiClientApiVO.getBaseUrl())
                    .completionsPath(aiClientApiVO.getCompletionsPath())
                    .embeddingsPath(aiClientApiVO.getEmbeddingsPath())
                    .build();
            // 注册 OpenAiApi Bean 对象
            registerBean(getBeanName(aiClientApiVO.getApiId()), OpenAiApi.class, openAiApi);
        }
        return router(requestParameter, dynamicContext);
    }

    @Override
    public StrategyHandler<ArmoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext, String> get(ArmoryCommandEntity armoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return aiClientToolMcpNode;
    }

    @Override
    protected String getDataName() {
        return AiAgentEnum.AI_CLIENT_API.getDataName();
    }

    @Override
    protected String getBeanName(String beanId) {
        return AiAgentEnum.AI_CLIENT_API.getBeanName(beanId);
    }
}
