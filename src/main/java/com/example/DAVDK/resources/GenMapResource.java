package com.example.DAVDK.resources;

import com.example.DAVDK.NewPath;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Path("/map")
@Produces(MediaType.APPLICATION_JSON)
public class GenMapResource {
    @GET
    public Response genMap() {
        NewPath.init();
        NewPath.Node[][] node = NewPath.genMap();
        List<NewPath.Node> list = new ArrayList<>();
        for (int i = 0; i < node.length; i++) {
            list.addAll(Arrays.asList(node[i]));
        }
        return Response.ok().entity(list).build();
    }
}
