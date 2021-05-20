package com.example.DAVDK.data;


import com.example.DAVDK.utils.Constants;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlConnection {
    private static MySqlConnection instance;
    private Connection connection;

    public static MySqlConnection getInstance() {
        if (instance == null) {
            instance = new MySqlConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        String url = Constants.database.URL;
        String username = Constants.database.USERNAME;
        String password = Constants.database.PASSWORD;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close(AutoCloseable... autoCloseables) {
        try {
            for (AutoCloseable autoCloseable : autoCloseables) {
                if (autoCloseable != null) {
                    autoCloseable.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
