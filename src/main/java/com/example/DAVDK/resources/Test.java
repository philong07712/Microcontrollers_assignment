package com.example.DAVDK.resources;

import com.example.DAVDK.data.service.SheetService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
public class Test {
    Map<String, String> map = new HashMap<>();

    void init() {
        if (map.isEmpty()) {
            map.put("admin", "admin");
        }
    }
    @Path("/{username}/{password}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addData(@PathParam("username") String username,@PathParam("password") String password) {
        init();
        boolean result = login(username, password);
        if (result) {
            return Response.ok().build();
        } else {
            return Response.status(404).build();
        }
    }


    boolean login(String username, String password) {
        if (map.containsKey(username)) {
            if (password.equals(map.get(username))) {
                return true;
            }
        }
        return false;
    }
}
