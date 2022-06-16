package com.example.DAVDK.utils;

import com.example.DAVDK.models.Movement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.awt.*;
import java.util.Arrays;

public class StringUtil {
    private static final String ALPHA_NUMERIC_STRING = "0123456789";
    private static final String ALPHABET_CHARACTER = "abcdefghijkmnopqrstuvwxyz0123456789";

    public static String getRandom(int num) {
        int count = num;
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHABET_CHARACTER.length());
            builder.append(ALPHABET_CHARACTER.charAt(character));
        }
        return builder.toString();
    }

    public static char[][] convertBoard(String json) {
        char[][] board = new char[10][9];
        JSONObject obj = (JSONObject) JSONValue.parse(json);
        JSONArray rows = (JSONArray) obj.get("board");
        for(int row = 0; row < rows.size(); row++) {
            JSONArray cols = (JSONArray) rows.get(row);
            char[] temp = new char[cols.size()];
            for(int col=0; col < cols.size(); col++) {
                temp[col] = cols.get(col).toString().charAt(0);
            }
            board[row] = temp;
        }
        return board;
    }
}
