package com.liubo.domain.service.armory;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.alibaba.fastjson.JSON;
import com.liubo.domain.model.entity.ArmoryCommandEntity;
import com.liubo.domain.model.valobj.AiClientAdvisorVO;
import com.liubo.domain.model.valobj.constant.AiAgentEnum;
import com.liubo.domain.model.valobj.constant.AiClientAdvisorTypeEnum;
import com.liubo.domain.service.armory.factory.DefaultArmoryStrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class AiClientAdvisorNode extends AbstractArmorySupport {

    @Resource
    private AiClientNode aiClientNode;

    @Resource
    private VectorStore vectorStore;

    @Override
    protected String doApply(ArmoryCommandEntity requestParameter, DefaultArmoryStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("Ai Agent 构建，Advisor 构建节点 {}", JSON.toJSONString(requestParameter));
        List<AiClientAdvisorVO> aiClientAdvisorVOList = dynamicContext.getValue(getDataName());
        if (CollectionUtils.isEmpty(aiClientAdvisorVOList)) {
            log.warn("没有需要被初始化的 ai client advisor");
            return router(requestParameter, dynamicContext);
        }
        for (AiClientAdvisorVO aiClientAdvisorVO : aiClientAdvisorVOList) {
            // 构建顾问访问对象
            Advisor advisor = createAdvisor(aiClientAdvisorVO);
            // 注册Bean对象
            registerBean(getBeanName(aiClientAdvisorVO.getAdvisorId()), Advisor.class, advisor);
        }
        return router(requestParameter, dynamicContext);
    }

    private Advisor createAdvisor(AiClientAdvisorVO aiClientAdvisorVO) {
        String advisorType = aiClientAdvisorVO.getAdvisorType();
        AiClientAdvisorTypeEnum advisorTypeEnum = AiClientAdvisorTypeEnum.getByCode(advisorType);
        return advisorTypeEnum.createAdvisor(aiClientAdvisorVO, vectorStore);
    }

    @Override
    public StrategyHandler<ArmoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext, String> get(ArmoryCommandEntity armoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return aiClientNode;
    }

    @Override
    protected String getDataName() {
        return AiAgentEnum.AI_CLIENT_ADVISOR.getDataName();
    }

    @Override
    protected String getBeanName(String beanId) {
        return AiAgentEnum.AI_CLIENT_ADVISOR.getBeanName(beanId);
    }
}
