package ru.hse.yume.rest.service;

import ru.hse.yume.data.dao.ProcessInstanceDao;
import ru.hse.yume.data.entity.ActivityInstance;
import ru.hse.yume.data.entity.ProcessInstance;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

/**
 * Author: Alexey Batrakov
 * Date: 11/04/17.
 */
@Path("/hello")
public class HelloWorldService {

    @GET
    @Path("/{param}")
    public Response getMsg(@PathParam("param") String msg,
                           @QueryParam("user") String user) throws IOException {

        ActivityInstance activityInstance = new ActivityInstance();
        activityInstance.setId("1");
        activityInstance.setActId("22");

        ProcessInstanceDao processInstanceDao = new ProcessInstanceDao();
        List<ProcessInstance> a = processInstanceDao.getRunningProcessInstanceForDefinition("hdItgeneral");
        System.out.println(a);

        Integer b = processInstanceDao.getNumberOfRunningProcessInstanceForDefinition("hdItgeneral");

        return Response
                .status(200)
                .type(MediaType.APPLICATION_JSON)
                .entity(b)
                .build();

    }

}