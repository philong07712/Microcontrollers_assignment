package com.example.DAVDK.resources;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/authorize")
@Produces(MediaType.APPLICATION_JSON)
public class AuthorizeResource {
    @POST
    public Response verifyAccount(@FormParam("username") String name,
                                  @FormParam("password") String password) {
        return Response.status(404).build();
    }
}
