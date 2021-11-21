package com.example.DAVDK.resources;

import com.example.DAVDK.Main;
import com.example.DAVDK.models.Movement;
import com.example.DAVDK.utils.StringUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("chess")
@Produces(MediaType.APPLICATION_JSON)
public class ChessResource {
    public static int maxHeight = 10;
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAIMove(String boardJson) {
        Main.generateScores();
        char[][] board = null;
        Movement movement = new Movement();
        try {
            board = StringUtil.convertBoard(boardJson);
            movement = Main.CPUMiniMaxTurn(board, true, 3);
            movement.oldPoint.y = 9 - movement.oldPoint.y;
            movement.newPoint.y = 9 - movement.newPoint.y;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (board == null) return Response.status(501).build();
        return Response.status(200).entity(movement).build();
    }
}
