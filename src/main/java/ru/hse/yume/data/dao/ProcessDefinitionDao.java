package ru.hse.yume.data.dao;

import org.apache.ibatis.session.SqlSession;
import ru.hse.yume.data.entity.*;
import ru.hse.yume.data.entity.Process;
import ru.hse.yume.data.mapper.ProcessDefinitionMapper;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * Author: Alexey Batrakov
 * Date: 13/04/17.
 */
public class ProcessDefinitionDao implements IProcessDefinitionDao {

    @Override
    public String getProcessDiagramXml(String key, int version) {
        try (SqlSession session = AbstractDao.getSessionFactory().openSession()) {
            ProcessDefinitionMapper mapper = session.getMapper(ProcessDefinitionMapper.class);
            ProcessDefinition definition = mapper.getProcessDefinitionByKeyAndVersion(key, version);
            Object xml = mapper.getXmlDiagramResource(definition.getResourceName(), definition.getDeploymentId());

            String res = new String((byte[]) xml, "UTF-8");
            // ToDo: analyse subprocesses, mark is expanded
            res = res.replace("<bpmndi:BPMNShape bpmnElement=\"subprocess1\" id=\"BPMNShape_subprocess1\">",
                    "<bpmndi:BPMNShape bpmnElement=\"subprocess1\" id=\"BPMNShape_subprocess1\" isExpanded=\"true\">");
            return res;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public int getLatestVersionForProcessDefinition(String key) {
        try (SqlSession session = AbstractDao.getSessionFactory().openSession()) {
            ProcessDefinitionMapper mapper = session.getMapper(ProcessDefinitionMapper.class);
            return mapper.getLatestVersion(key);
        }
    }

    @Override
    public List<Process> getProcessList() {
        try (SqlSession session = AbstractDao.getSessionFactory().openSession()) {
            ProcessDefinitionMapper mapper = session.getMapper(ProcessDefinitionMapper.class);
            return mapper.getProcessList();
        }
    }

    @Override
    public List<ActivityInstance> getCurrentActivities(String processDefId) {
        try (SqlSession session = AbstractDao.getSessionFactory().openSession()) {
            ProcessDefinitionMapper mapper = session.getMapper(ProcessDefinitionMapper.class);
            return mapper.getCurrentActivities(processDefId);
        }
    }

    @Override
    public List<ActivityInstanceCount> getCurrentActivitiesCount(String key, String version) {
        try (SqlSession session = AbstractDao.getSessionFactory().openSession()) {
            ProcessDefinitionMapper mapper = session.getMapper(ProcessDefinitionMapper.class);
            String like = key;
            if (version != null) {
                like += ":" + version;
            }
            return mapper.getCurrentActivitiesCount(like + "%");
        }
    }

    @Override
    public List<ActivityInstanceCount> getActivitiesCount(String key) {
        try (SqlSession session = AbstractDao.getSessionFactory().openSession()) {
            ProcessDefinitionMapper mapper = session.getMapper(ProcessDefinitionMapper.class);
            return mapper.getActivitiesCount(key + "%");
        }
    }

    @Override
    public List<ProcessDefinition> getAllDefinitionsByKey(String key) {
        try (SqlSession session = AbstractDao.getSessionFactory().openSession()) {
            ProcessDefinitionMapper mapper = session.getMapper(ProcessDefinitionMapper.class);
            return mapper.getAllDefinitionsByKey(key);
        }
    }

    @Override
    public List<TaskDuration> getAvgTaskDurationByAsignee(String actId, String key) {
        try (SqlSession session = AbstractDao.getSessionFactory().openSession()) {
            ProcessDefinitionMapper mapper = session.getMapper(ProcessDefinitionMapper.class);
            return mapper.getAvgTaskDurationByAsignee(actId, key + "%");
        }
    }

    @Override
    public Integer getOverdueTaskCountForDefinition(String key) {
        try (SqlSession session = AbstractDao.getSessionFactory().openSession()) {
            ProcessDefinitionMapper mapper = session.getMapper(ProcessDefinitionMapper.class);
            return mapper.getOverdueTaskCountForDefinition(key + "%", new Date());
        }
    }

    @Override
    public Integer getAtRiskTaskCountForDefinition(String key) {
        try (SqlSession session = AbstractDao.getSessionFactory().openSession()) {
            ProcessDefinitionMapper mapper = session.getMapper(ProcessDefinitionMapper.class);
            return mapper.getAtRiskTaskCountForDefinition(key + "%", new Date());
        }
    }

    @Override
    public List<Task> getOverdueTasksForDefinition(String key) {
        try (SqlSession session = AbstractDao.getSessionFactory().openSession()) {
            ProcessDefinitionMapper mapper = session.getMapper(ProcessDefinitionMapper.class);
            return mapper.getOverdueTasksForDefinition(key + "%", new Date());
        }
    }

    @Override
    public List<Task> getAtRiskTasksForDefinition(String key) {
        try (SqlSession session = AbstractDao.getSessionFactory().openSession()) {
            ProcessDefinitionMapper mapper = session.getMapper(ProcessDefinitionMapper.class);
            return mapper.getAtRiskTaskыForDefinition(key + "%", new Date());
        }
    }
}
