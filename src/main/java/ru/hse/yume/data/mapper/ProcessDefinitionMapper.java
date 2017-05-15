package ru.hse.yume.data.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import ru.hse.yume.data.entity.*;
import ru.hse.yume.data.entity.Process;

import java.util.Date;
import java.util.List;

/**
 * Author: Alexey Batrakov
 * Date: 13/04/17.
 */
public interface ProcessDefinitionMapper {
    @Select("SELECT * FROM act_re_procdef WHERE key_ = #{key} AND version_ = #{version}")
    ProcessDefinition getProcessDefinitionByKeyAndVersion(@Param("key") String key, @Param("version") Integer version);

    @Select("SELECT bytes_ FROM act_ge_bytearray WHERE name_ = #{name} AND deployment_id_ = #{deployment_id}")
    Object getXmlDiagramResource(@Param("name") String name, @Param("deployment_id") String deploymentId);

    @Select("SELECT MAX(version_) FROM act_re_procdef WHERE key_ = #{key}")
    Integer getLatestVersion(@Param("key") String key);

    //    @Select("SELECT DISTINCT name_, (SELECT EXTRACT(EPOCH FROM ('2017-05-13 12:00'::timestamp - '2017-05-13 11:00'::timestamp))) * 1000 as key_ FROM act_re_procdef")
    @Select("SELECT DISTINCT name_, key_ FROM act_re_procdef")
    List<Process> getProcessList();

    @Select("SELECT * FROM act_hi_actinst WHERE end_time_ IS NULL AND proc_def_id_ =  #{proc_def_id}")
    List<ActivityInstance> getCurrentActivities(@Param("proc_def_id") String key);

    @Select("SELECT count(*), act_id_ FROM act_hi_actinst WHERE end_time_ IS NULL AND proc_def_id_ LIKE #{proc_def_id} GROUP BY act_id_")
    List<ActivityInstanceCount> getCurrentActivitiesCount(@Param("proc_def_id") String procDefIdLike);

    @Select("SELECT count(*), act_id_ FROM act_hi_actinst WHERE proc_def_id_ LIKE #{proc_def_id} GROUP BY act_id_")
    List<ActivityInstanceCount> getActivitiesCount(@Param("proc_def_id") String procDefIdLike);

    @Select("SELECT\n" +
            "  count(*),\n" +
            "  act_id_\n" +
            "FROM act_hi_actinst act LEFT JOIN act_hi_taskinst task ON act.task_id_ = task.id_\n" +
            "WHERE task.end_time_ > task.due_date_ AND act.proc_def_id_ LIKE #{proc_def_id}\n" +
            "GROUP BY act_id_")
    List<ActivityInstanceCount> getCurrentActivitiesOverdueCount(@Param("proc_def_id") String procDefIdLike);

    @Select("SELECT * FROM act_re_procdef WHERE key_ = #{key}")
    List<ProcessDefinition> getAllDefinitionsByKey(@Param("key") String key);

    @Select("SELECT avg(duration_) as duration, assignee_ FROM act_hi_actinst " +
            "WHERE act_id_ = #{act_id} " +
            "and duration_ is not null " +
            "and proc_def_id_ like #{proc_def_id} " +
            "GROUP BY assignee_ ORDER BY duration ASC")
    List<TaskDuration> getAvgTaskDurationByAsignee(@Param("act_id") String actId,
                                                   @Param("proc_def_id") String key);




    @Select("SELECT count(*)\n" +
            "FROM act_ru_task task\n" +
            "WHERE task.proc_def_id_ LIKE #{proc_def_id}\n" +
            "      AND task.due_date_ IS NOT NULL\n" +
            "      AND task.due_date_ <= #{current_date}")
    Integer getOverdueTaskCountForDefinition(@Param("proc_def_id") String key,
                                             @Param("current_date") Date currentDate);


    @Select("SELECT count(*)\n" +
            "FROM act_ru_task task\n" +
            "WHERE task.proc_def_id_ LIKE #{proc_def_id}\n" +
            "      AND task.due_date_ IS NOT NULL\n" +
            "      AND task.due_date_ > #{current_date}\n" +
            "      AND (SELECT extract(EPOCH FROM (task.due_date_ - #{current_date}))) * 1000 <\n" +
            "          (SELECT avg(actinst.duration_)\n" +
            "           FROM act_hi_actinst \"actinst\"\n" +
            "           WHERE actinst.act_id_ = task.task_def_key_\n" +
            "                 AND proc_def_id_ LIKE #{proc_def_id});")
    Integer getAtRiskTaskCountForDefinition(@Param("proc_def_id") String key,
                                            @Param("current_date") Date currentDate);

    @Select("SELECT *\n" +
            "FROM act_ru_task task\n" +
            "WHERE task.proc_def_id_ LIKE #{proc_def_id}\n" +
            "      AND task.due_date_ IS NOT NULL\n" +
            "      AND task.due_date_ <= #{current_date}")
    List<Task> getOverdueTasksForDefinition(@Param("proc_def_id") String key,
                                             @Param("current_date") Date currentDate);

    @Select("SELECT count(*)\n" +
            "FROM act_ru_task task\n" +
            "WHERE task.proc_def_id_ LIKE #{proc_def_id}\n" +
            "      AND task.due_date_ IS NOT NULL\n" +
            "      AND task.due_date_ > #{current_date}\n" +
            "      AND (SELECT extract(EPOCH FROM (task.due_date_ - #{current_date}))) * 1000 <\n" +
            "          (SELECT avg(actinst.duration_)\n" +
            "           FROM act_hi_actinst \"actinst\"\n" +
            "           WHERE actinst.act_id_ = task.task_def_key_\n" +
            "                 AND proc_def_id_ LIKE #{proc_def_id});")
    List<Task> getAtRiskTaskыForDefinition(@Param("proc_def_id") String key,
                                            @Param("current_date") Date currentDate);


}
