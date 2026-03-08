package com.liubo.types.job.provider;

import com.liubo.types.job.model.TaskScheduleVO;

import java.util.List;

/**
 * @author 68
 * 2026/3/8 10:06
 */
public interface ITaskDataProvider {
    /**
     * 查询所有有效的任务调度配置
     * @return 任务调度配置列表
     */
    List<TaskScheduleVO> queryAllValidTaskSchedule();

    /**
     * 查询所有无效的任务ID
     * @return 无效任务ID列表
     */
    List<Long> queryAllInvalidTaskScheduleIds();
}
