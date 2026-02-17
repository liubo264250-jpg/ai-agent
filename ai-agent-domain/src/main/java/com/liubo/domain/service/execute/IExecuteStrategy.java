package com.liubo.domain.service.execute;

import com.liubo.domain.model.entity.ExecuteCommandEntity;

public interface IExecuteStrategy {

    void execute(ExecuteCommandEntity executeCommandEntity) throws Exception ;
}
