package ru.hse.yume.rest.service;

import ru.hse.yume.data.dao.ProcessDefinitionDao;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Author: Alexey Batrakov
 * Date: 11/04/17.
 */
@Path("/process-diagram")
public class ProcessDiagramService {

    @GET
    public Response getDiagram(@QueryParam("key") String key,
                               @QueryParam("version") Integer version) {

        ProcessDefinitionDao processDefinitionDao = new ProcessDefinitionDao();

        String output = null;
        if (version == null) {
            version = processDefinitionDao.getLatestVersionForProcessDefinition(key);
        }
        output = processDefinitionDao.getProcessDiagramXml(key, version);

        output = output.replaceAll("\\$\\{docLabel\\} ", "");
        output = output.replaceAll(" \\$\\{d\\}", "");
//        output = output.replaceAll(" \\{d\\}", "");

        Response response;
        if (output != null) {
            response = Response
                    .status(200)
                    .type(MediaType.TEXT_XML)
                    .entity(output)
                    .build();
        } else {
            response = Response
                    .status(400)
                    .type(MediaType.TEXT_PLAIN)
                    .entity("error in get request")
                    .build();
        }

        return response;

    }

}