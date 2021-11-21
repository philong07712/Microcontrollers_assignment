package com.example.DAVDK.resources.endpoint;

import com.example.DAVDK.models.Chess;
import com.example.DAVDK.models.encoder.ChessDecoder;
import com.example.DAVDK.models.encoder.ChessEncoder;
import com.example.DAVDK.utils.Constants;
import com.example.DAVDK.utils.StringUtil;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ServerEndpoint(value = "/chess/{roomId}/{username}", decoders = ChessDecoder.class, encoders = ChessEncoder.class)
public class ChessEndpoint {
    private Session session;
    private static Map<String, Set<ChessEndpoint>> mapEndpoints = new HashMap<>();
    private static Map<String, Set<Chess>> mapChess = new HashMap<>();
    private static Map<String, String> users = new HashMap<>();
    @OnOpen
    public void onOpen(Session session, @PathParam("roomId") String roomId, @PathParam("username") String username)
            throws IOException {
        this.session = session;
        if (!mapEndpoints.containsKey(roomId)) {
            mapEndpoints.put(roomId, new HashSet<>());
            mapChess.put(roomId, new HashSet<>());
        }
        mapEndpoints.get(roomId).add(this);
        users.put(session.getId(), username);
        Chess chess = new Chess();
        chess.setUsername(username);
        if (mapEndpoints.get(roomId).size() == 1) {
            // first player will get type black
            chess.setType(Constants.chess.BLACK);
        } else {
            for (Chess chess1 : mapChess.get(roomId)) {
                if (chess1.getType().equals(Constants.chess.BLACK)) {
                    chess.setType(Constants.chess.RED);
                } else {
                    chess.setType(Constants.chess.BLACK);
                }
            }
        }
        chess.setMessage(Constants.chess.CONNECT);
        mapChess.get(roomId).add(chess);
        broadcast(chess, roomId);
        // if 2 player then we will sent start message
        if (mapChess.get(roomId).size() == 2) {
            String startMessage = Constants.chess.START;
            for (Chess chess1 : mapChess.get(roomId)) {
                startMessage += "," + chess1.getUsername();
            }
            chess.setMessage(startMessage);

            broadcast(chess, roomId);
        }
        //TODO: check room if is 2 people then we will start the game
    }

    @OnMessage
    public void onMessage(Session session, Chess chess, @PathParam("roomId") String roomId,
                          @PathParam("username") String username) throws IOException {
        // Handle new messages
        //TODO: will validate coordinate here
        System.out.println(chess);
        broadcast(chess, roomId);
    }

    @OnClose
    public void onClose(Session session, @PathParam("roomId") String roomId, @PathParam("username") String username)
            throws IOException {
        mapEndpoints.get(roomId).remove(this);
        if (mapEndpoints.get(roomId).isEmpty()) mapEndpoints.remove(roomId);
        if (mapChess.get(roomId).isEmpty()) mapChess.remove(roomId);
        for (Chess chess1 : mapChess.get(roomId)) {
            if (chess1.getUsername().equals(username)) {
                boolean isSuccess = mapChess.get(roomId).remove(chess1);
                break;
            }
        }
        users.remove(session.getId());

        Chess chess = new Chess();
        chess.setUsername(username);
        chess.setMessage(Constants.chess.DISCONNECT);
        broadcast(chess, roomId);
        // WebSocket connection closes
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("Socket error: " + throwable.getMessage());
        throwable.printStackTrace();
    }

    private static void broadcast(Chess chess, String roomId) {
        if (!mapEndpoints.containsKey(roomId)) return;
        Set<ChessEndpoint> chessEndpoints = mapEndpoints.get(roomId);
        chessEndpoints.forEach(chessEndpoint -> {
            synchronized (chessEndpoint) {
                try {
                    chessEndpoint.session.getBasicRemote().sendObject(chess);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
