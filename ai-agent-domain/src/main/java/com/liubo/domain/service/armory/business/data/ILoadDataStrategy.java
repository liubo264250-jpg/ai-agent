package com.liubo.domain.service.armory.business.data;

import com.liubo.domain.model.entity.ArmoryCommandEntity;
import com.liubo.domain.service.armory.factory.DefaultArmoryStrategyFactory;

/**
 * @author 68
 * 2026/2/10 09:02
 */
public interface ILoadDataStrategy {

    void loadData(ArmoryCommandEntity armoryCommandEntity, DefaultArmoryStrategyFactory.DynamicContext dynamicContext);
}
