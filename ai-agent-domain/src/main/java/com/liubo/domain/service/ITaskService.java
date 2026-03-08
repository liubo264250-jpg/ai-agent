package com.liubo.domain.service;

import com.liubo.domain.model.valobj.AiAgentTaskScheduleVO;

import java.util.List;

/**
 * @author 68
 * 2026/3/8 10:21
 */
public interface ITaskService {
    List<AiAgentTaskScheduleVO> queryAllValidTaskSchedule();

    List<Long> queryAllInvalidTaskScheduleIds();
}
