package com.example.DAVDK.data.dao;

import com.example.DAVDK.models.Data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataDao extends BaseDao {
    public List<Data> getData() {
        List<Data> list = new ArrayList<>();
        Connection connection = mysql.getConnection();
        ResultSet rs = null;
        try {
            Statement stmt = connection.createStatement();
            String sql = "select * from data";
            rs = stmt.executeQuery(sql);
            if (rs != null) {
                while (rs.next()) {
                    Timestamp time = rs.getTimestamp(2);
                    time.setTime(time.getTime() - 25200000);
                    int aqi = rs.getInt(3);
                    double co2 = rs.getDouble(4);
                    double co = rs.getDouble(5);
                    double pm = rs.getDouble(6);
                    double temperature = rs.getDouble(7);
                    double humidity = rs.getDouble(8);
                    //todo: still missing date time
                    Data data = new Data(aqi, co2, co, pm, temperature, humidity);
                    data.setTime(time);
                    list.add(data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mysql.close(rs, connection);
        }
        return list;
    }

    public Data getLatestData() {
        Data data = new Data();
        Connection connection = mysql.getConnection();
        ResultSet rs = null;
        try {
            Statement stmt = connection.createStatement();
            String sql = "select * from data where time in (select max(time) from data)";
            rs = stmt.executeQuery(sql);
            if (rs != null) {
                while (rs.next()) {
                    Timestamp time = rs.getTimestamp(2);
                    time.setTime(time.getTime() - 25200000);
                    int aqi = rs.getInt(3);
                    double co2 = rs.getDouble(4);
                    double co = rs.getDouble(5);
                    double pm = rs.getDouble(6);
                    double temperature = rs.getDouble(7);
                    double humidity = rs.getDouble(8);
                    //todo: still missing date time
                    data = new Data(aqi, co2, co, pm, temperature, humidity);
                    data.setTime(time);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mysql.close(rs, connection);
        }
        return data;
    }

    public Data addData(Data data) {
        Connection connection = mysql.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("INSERT INTO data(time, aqi, " +
                            "co2, co, pm, temperature, humidity)" + " VALUES (?, ?, ?, ?, ?, ?, ?)"
                    , PreparedStatement.RETURN_GENERATED_KEYS);
            // set value to profile
            ps.setTimestamp(1, data.getTime());
            ps.setInt(2, data.getAqi());
            ps.setDouble(3, data.getCo2());
            ps.setDouble(4, data.getCo());
            ps.setDouble(5, data.getPm());
            ps.setDouble(6, data.getTemperature());
            ps.setDouble(7, data.getHumidity());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
            }
            // after update
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mysql.close(ps, connection);
        }
        return data;
    }
}

