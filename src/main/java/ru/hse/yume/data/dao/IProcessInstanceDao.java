package ru.hse.yume.data.dao;

import ru.hse.yume.data.entity.ProcessInstance;
import ru.hse.yume.data.entity.TaskInstance;

import java.util.List;

/**
 * Author: Alexey Batrakov
 * Date: 16/04/17.
 */
public interface IProcessInstanceDao {
    Integer getNumberOfRunningProcessInstanceForDefinition(String processDefId);

    List<ProcessInstance> getRunningProcessInstanceForDefinition(String processDefId);

    List<TaskInstance> getActivitiesForProcessInstance(String processInstanceId);

    List<TaskInstance> getActivityInfoForProcessInstance(String processInstanceId, String activityId);
}
