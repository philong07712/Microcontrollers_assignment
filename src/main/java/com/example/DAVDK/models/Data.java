package com.example.DAVDK.models;

import java.sql.Timestamp;

public class Data {
    Timestamp time;
    int aqi;
    double co2;
    double co;
    double pm;
    double temperature;
    double humidity;

    public Data() {
    }

    public Data(int aqi, double co2, double co, double pm, double temperature, double humidity) {
        this.aqi = aqi;
        this.co2 = co2;
        this.co = co;
        this.pm = pm;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getAqi() {
        return aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    public double getCo2() {
        return co2;
    }

    public void setCo2(double co2) {
        this.co2 = co2;
    }

    public double getCo() {
        return co;
    }

    public void setCo(double co) {
        this.co = co;
    }

    public double getPm() {
        return pm;
    }

    public void setPm(double pm) {
        this.pm = pm;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
}