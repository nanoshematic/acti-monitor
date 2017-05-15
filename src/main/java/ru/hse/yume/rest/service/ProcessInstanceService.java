package ru.hse.yume.rest.service;

import ru.hse.yume.data.dao.ProcessInstanceDao;
import ru.hse.yume.data.entity.TaskInstance;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

/**
 * Author: Alexey Batrakov
 * Date: 23/04/17.
 */
@Path("/process-instance")
public class ProcessInstanceService {
    @GET
    @Path("/activities")
    public Response getActivitiesForProcessInstance(
            @QueryParam("instance") String instance) throws IOException {

        ProcessInstanceDao processInstanceDao = new ProcessInstanceDao();
        List<TaskInstance> activies = processInstanceDao.getActivitiesForProcessInstance(instance);

        return Response
                .status(200)
                .type(MediaType.APPLICATION_JSON)
                .entity(activies)
                .build();
    }
    @GET
    @Path("/activity")
    public Response getActivityInfoForProcessInstance(
            @QueryParam("instance") String instance,
            @QueryParam("activity") String activity) throws IOException {

        ProcessInstanceDao processInstanceDao = new ProcessInstanceDao();
        List<TaskInstance> activies = processInstanceDao.getActivityInfoForProcessInstance(instance, activity);

        return Response
                .status(200)
                .type(MediaType.APPLICATION_JSON)
                .entity(activies)
                .build();
    }
}

