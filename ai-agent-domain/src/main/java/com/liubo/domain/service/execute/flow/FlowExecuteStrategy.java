package com.liubo.domain.service.execute.flow;

import com.liubo.domain.model.entity.ExecuteCommandEntity;
import com.liubo.domain.service.execute.IExecuteStrategy;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

public class FlowExecuteStrategy implements IExecuteStrategy{

    @Override
    public void execute(ExecuteCommandEntity requestParameter, ResponseBodyEmitter emitter) throws Exception {

    }
}
