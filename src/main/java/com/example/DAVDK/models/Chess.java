package com.example.DAVDK.models;

import lombok.Data;

import java.awt.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class Chess {
    String type;
    Point newPoint;
    Point oldPoint;
    String message;
    String username;
//    public Map<String, Object> toJSONObject() throws Exception {
//        Map<String, Object> jo = new HashMap<>();
//        jo.put("content", content);
//        jo.put("sender", sender);
//        jo.put("received", received.toString());
//        return jo;
//    }
    @Override
    public boolean equals(Object object) {
        Chess chess = (Chess) object;
        return this.username.equals(chess.username);
    }
}
