package com.example.DAVDK.models.encoder;

import com.example.DAVDK.models.Chess;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.simple.parser.JSONParser;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.util.HashMap;

public class ChessEncoder implements Encoder.Text<Chess>{
    @Override
    public String encode(Chess chess) throws EncodeException {
        Gson gson = new Gson();
        return gson.toJson(chess);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
