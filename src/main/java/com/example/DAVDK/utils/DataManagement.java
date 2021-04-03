package com.example.DAVDK.utils;

public class DataManagement {
    double data;

    public static DataManagement instance;
    private DataManagement() {
        this.data = 0.0;

    }

    public static DataManagement getInstance() {
        if (instance == null) instance = new DataManagement();
        return instance;
    }

    public double getData() {
        return data;
    }

    public void setData(double data) {
        this.data = data;
    }
}
