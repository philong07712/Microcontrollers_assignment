package com.example.DAVDK.data.dao;

import com.example.DAVDK.data.MySqlConnection;

public class BaseDao {
    protected MySqlConnection mysql;
    public BaseDao() {mysql = MySqlConnection.getInstance();
    }
}
