package ru.hse.yume.data.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import ru.hse.yume.data.entity.ActivityInstance;
import ru.hse.yume.data.entity.ProcessInstance;
import ru.hse.yume.data.entity.TaskInstance;

import java.util.List;

/**
 * Author: Alexey Batrakov
 * Date: 16/04/17.
 */
public interface ProcessInstanceMapper {
    @Select("SELECT * FROM act_hi_procinst WHERE end_time_ IS NULL AND proc_def_id_ LIKE #{proc_def_id}")
    List<ProcessInstance> getRunningProcessInstance(@Param("proc_def_id") String processDefIdForLikeQuery);

    @Select("SELECT count(*) FROM act_hi_procinst WHERE end_time_ IS NULL AND proc_def_id_ LIKE #{proc_def_id}")
    Integer getNumberOfRunningProcessInstance(@Param("proc_def_id") String processDefIdForLikeQuery);

//    @Select("SELECT * FROM activiti.act_hi_actinst WHERE act_type_ = 'userTask' and proc_inst_id_ = #{proc_inst_id}")
    @Select("SELECT act.*, task.due_date_, task.due_date_ < task.end_time_ AS over_due_\n" +
            "FROM activiti.act_hi_actinst act LEFT JOIN activiti.act_hi_taskinst task ON act.task_id_ = task.id_\n" +
            "WHERE act.act_type_ = 'userTask' AND act.proc_inst_id_ = #{proc_inst_id}")
//    @Select("SELECT *, due_date_ < end_time_ as over_due_ FROM activiti.act_hi_taskinst WHERE proc_inst_id_ = #{proc_inst_id}")
    List<TaskInstance> getActivitiesForProcessInstance(@Param("proc_inst_id") String processInstanceId);

    @Select("SELECT act.*, task.due_date_, task.due_date_ < task.end_time_ AS over_due_\n" +
            "FROM activiti.act_hi_actinst act LEFT JOIN activiti.act_hi_taskinst task ON act.task_id_ = task.id_\n" +
            "WHERE act.act_type_ = 'userTask' AND act.proc_inst_id_ = #{proc_inst_id} AND act.act_id_ = #{act_id}")
    List<TaskInstance> getActivityInfoForProcessInstance(@Param("proc_inst_id") String processInstanceId,
                                                         @Param("act_id") String activityId);
//    List<ActivityInstance> getActivitiesForProcessInstance(@Param("proc_inst_id") String processInstanceId);
}
