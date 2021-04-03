package com.example.DAVDK.resources;

import com.example.DAVDK.utils.DataManagement;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/data")
@Produces(MediaType.APPLICATION_JSON)
public class DataResource {
    @GET
    public Response getData() {
        double data = DataManagement.getInstance().getData();
        return Response.ok().entity(data).build();
    }
    @PUT
    public Response setData(@FormParam("data") double data) {
        DataManagement.getInstance().setData(data);
        return Response.ok().build();
    }
}
