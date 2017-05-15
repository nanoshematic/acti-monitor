package ru.hse.yume.data.dao;

import ru.hse.yume.data.entity.*;
import ru.hse.yume.data.entity.Process;

import java.util.List;

/**
 * Author: Alexey Batrakov
 * Date: 13/04/17.
 */
public interface IProcessDefinitionDao {

    public String getProcessDiagramXml(String key, int version);

    public int getLatestVersionForProcessDefinition(String key);

    List<Process> getProcessList();

    List<ActivityInstance> getCurrentActivities(String processDefId);

    List<ActivityInstanceCount> getCurrentActivitiesCount(String key, String version);

    List<ActivityInstanceCount> getActivitiesCount(String key);

    List<ProcessDefinition> getAllDefinitionsByKey(String key);

    List<TaskDuration> getAvgTaskDurationByAsignee(String actId, String key);

    Integer getOverdueTaskCountForDefinition(String key);

    Integer getAtRiskTaskCountForDefinition(String key);

    List<Task> getOverdueTasksForDefinition(String key);

    List<Task> getAtRiskTasksForDefinition(String key);
}
