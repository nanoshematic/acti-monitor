package ru.hse.yume.data.dao;

import org.apache.ibatis.session.SqlSession;
import ru.hse.yume.data.entity.ProcessInstance;
import ru.hse.yume.data.entity.TaskInstance;
import ru.hse.yume.data.mapper.ProcessInstanceMapper;

import java.util.List;

/**
 * Author: Alexey Batrakov
 * Date: 16/04/17.
 */
public class ProcessInstanceDao implements IProcessInstanceDao {

    @Override
    public List<ProcessInstance> getRunningProcessInstanceForDefinition(String processDefId) {
        try (SqlSession session = AbstractDao.getSessionFactory().openSession()) {
            ProcessInstanceMapper mapper = session.getMapper(ProcessInstanceMapper.class);
            List<ProcessInstance> list = mapper.getRunningProcessInstance("%" + processDefId + "%");
            return list;
        }
    }

    @Override
    public Integer getNumberOfRunningProcessInstanceForDefinition(String processDefId) {
        try (SqlSession session = AbstractDao.getSessionFactory().openSession()) {
            ProcessInstanceMapper mapper = session.getMapper(ProcessInstanceMapper.class);
            Integer count = mapper.getNumberOfRunningProcessInstance("%" + processDefId + "%");
            return count;
        }
    }

    @Override
    public List<TaskInstance> getActivitiesForProcessInstance(String processInstanceId) {
        try (SqlSession session = AbstractDao.getSessionFactory().openSession()) {
            ProcessInstanceMapper mapper = session.getMapper(ProcessInstanceMapper.class);
            List<TaskInstance> instances = mapper.getActivitiesForProcessInstance(processInstanceId);
            return instances;
        }
    }

    @Override
    public List<TaskInstance> getActivityInfoForProcessInstance(String processInstanceId, String activityId) {
        try (SqlSession session = AbstractDao.getSessionFactory().openSession()) {
            ProcessInstanceMapper mapper = session.getMapper(ProcessInstanceMapper.class);
            List<TaskInstance> instances = mapper.getActivityInfoForProcessInstance(processInstanceId, activityId);
            return instances;
        }
    }
}
