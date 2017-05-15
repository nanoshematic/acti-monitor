package ru.hse.yume.rest.service;

import ru.hse.yume.data.dao.IProcessDefinitionDao;
import ru.hse.yume.data.dao.ProcessDefinitionDao;
import ru.hse.yume.data.dao.ProcessInstanceDao;
import ru.hse.yume.data.entity.*;
import ru.hse.yume.data.entity.Process;
import ru.hse.yume.rest.object.ProcessDefResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Alexey Batrakov
 * Date: 16/04/17.
 */
@Path("/process-definition")
public class ProcessDefinitionService {

    @GET
    @Path("/all")
    public Response getProcessDefinitionList(
            @QueryParam("user") String user) throws IOException {

        List<ProcessDefResponse> definitions = new ArrayList<>();
        ProcessDefinitionDao processDefinitionDao = new ProcessDefinitionDao();
        ProcessInstanceDao processInstanceDao = new ProcessInstanceDao();
        List<Process> processes = processDefinitionDao.getProcessList();
        processes.forEach(e -> {
            Integer count = processInstanceDao.getNumberOfRunningProcessInstanceForDefinition(e.getKey());
            Integer atRiskCount = processDefinitionDao.getAtRiskTaskCountForDefinition(e.getKey());
            Integer overdueCount = processDefinitionDao.getOverdueTaskCountForDefinition(e.getKey());
            ProcessDefResponse processDefResponse = new ProcessDefResponse();
            processDefResponse.setCount(count);
            processDefResponse.setKey(e.getKey());
            processDefResponse.setName(e.getName());
            processDefResponse.setAtRiskCount(atRiskCount);
            processDefResponse.setOverdueCount(overdueCount);
            definitions.add(processDefResponse);
        });

        return Response
                .status(200)
                .type(MediaType.APPLICATION_JSON)
                .entity(definitions)
                .build();

    }

    @GET
    @Path("/overdue-tasks")
    public Response getOverdueTasksForProcessDefinition(
            @QueryParam("processDef") String procDef) {

        IProcessDefinitionDao processDefinitionDao = new ProcessDefinitionDao();
        List<Task> overdueTaskList = processDefinitionDao.getOverdueTasksForDefinition(procDef);

        return Response
                .status(200)
                .type(MediaType.APPLICATION_JSON)
                .entity(overdueTaskList)
                .build();

    }

    @GET
    @Path("/atrisk-tasks")
    public Response getAtRiskTasksForProcessDefinition(
            @QueryParam("processDef") String procDef) {

        IProcessDefinitionDao processDefinitionDao = new ProcessDefinitionDao();
        List<Task> atRiskTaskList = processDefinitionDao.getAtRiskTasksForDefinition(procDef);

        return Response
                .status(200)
                .type(MediaType.APPLICATION_JSON)
                .entity(atRiskTaskList)
                .build();

    }


    @GET
    @Path("/activities")
    public Response getCurrentActivities(
            @QueryParam("processDef") String procDef) throws IOException {

        ProcessDefinitionDao processDefinitionDao = new ProcessDefinitionDao();
        List<ActivityInstance> activities = processDefinitionDao.getCurrentActivities(procDef);

        return Response
                .status(200)
                .type(MediaType.APPLICATION_JSON)
                .entity(activities)
                .build();

    }

    @GET
    @Path("/activities-count")
    public Response getCurrentActivitiesCount(
            @QueryParam("key") String key,
            @QueryParam("version") String version) throws IOException {

        ProcessDefinitionDao processDefinitionDao = new ProcessDefinitionDao();
        List<ActivityInstanceCount> activities = processDefinitionDao.getCurrentActivitiesCount(key, version);

        return Response
                .status(200)
                .type(MediaType.APPLICATION_JSON)
                .entity(activities)
                .build();

    }

    @GET
    @Path("/activities-count-all")
    public Response getActivitiesCount(
            @QueryParam("key") String key
            ) throws IOException {

        ProcessDefinitionDao processDefinitionDao = new ProcessDefinitionDao();
        List<ActivityInstanceCount> activities = processDefinitionDao.getActivitiesCount(key);

        return Response
                .status(200)
                .type(MediaType.APPLICATION_JSON)
                .entity(activities)
                .build();

    }

    @GET
    @Path("/versions")
    public Response getVersionsForProcessDefinition(
            @QueryParam("key") String key) throws IOException {
        ProcessDefinitionDao processDefinitionDao = new ProcessDefinitionDao();
        List<ProcessDefinition> activities = processDefinitionDao.getAllDefinitionsByKey(key);

        return Response
                .status(200)
                .type(MediaType.APPLICATION_JSON)
                .entity(activities)
                .build();
    }

    @GET
    @Path("/instances")
    public Response getInstancesForProcessDefinition(
            @QueryParam("key") String key,
            @QueryParam("version") String version) throws IOException {
        ProcessInstanceDao processInstanceDao = new ProcessInstanceDao();
        List<ProcessInstance> instances = processInstanceDao.getRunningProcessInstanceForDefinition(key + ":" + version);
        return Response
                .status(200)
                .type(MediaType.APPLICATION_JSON)
                .entity(instances)
                .build();
    }

    @GET
    @Path("/tasks/duration")
    public Response getAvgTaskDurationByAsignee(
            @QueryParam("activity") String activiti,
            @QueryParam("key") String key) throws IOException {
        ProcessDefinitionDao processDefinitionDao = new ProcessDefinitionDao();

        List<TaskDuration> result = processDefinitionDao.getAvgTaskDurationByAsignee(activiti, key);

        return Response
                .status(200)
                .type(MediaType.APPLICATION_JSON)
                .entity(result)
                .build();
    }

}
