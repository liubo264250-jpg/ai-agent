package com.liubo.domain.service.task;

import com.liubo.domain.adapter.repository.IAgentRepository;
import com.liubo.domain.model.valobj.AiAgentTaskScheduleVO;
import com.liubo.domain.service.ITaskService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 68
 * 2026/3/8 10:22
 */
@Service
public class AiAgentTaskService implements ITaskService {
    @Resource
    private IAgentRepository repository;

    @Override
    public List<AiAgentTaskScheduleVO> queryAllValidTaskSchedule() {
        return repository.queryAllValidTaskSchedule();
    }

    @Override
    public List<Long> queryAllInvalidTaskScheduleIds() {
        return repository.queryAllInvalidTaskScheduleIds();
    }

}
