package com.example.DAVDK.models.encoder;

import com.example.DAVDK.models.Chess;
import com.google.gson.Gson;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class ChessDecoder implements Decoder.Text<Chess>{
    @Override
    public Chess decode(String s) throws DecodeException {
        Gson gson = new Gson();
        return gson.fromJson(s, Chess.class);
    }

    @Override
    public boolean willDecode(String s) {
        return (s != null);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
