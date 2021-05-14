package com.example.DAVDK.utils;


public class DataManagement {
    int f1, f8, f24;
    int aqi;
    String sensitive;
    String effect;
    String caution;

    public static DataManagement instance;
    private DataManagement() {
        f1 = 33;
        f8 = 51;
        f24 = 42;
        aqi = 30;
        sensitive = Constants.good.coSensitive;
        effect = Constants.good.coEffect;
        caution = Constants.good.coCaution;
    }

    public static DataManagement getInstance() {
        if (instance == null) instance = new DataManagement();
        return instance;
    }

    public int getAqi() {
        return aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    public String getSensitive() {
        return sensitive;
    }

    public void setSensitive(String sensitive) {
        this.sensitive = sensitive;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getCaution() {
        return caution;
    }

    public void setCaution(String caution) {
        this.caution = caution;
    }

    public int getF1() {
        return f1;
    }

    public void setF1(int f1) {
        this.f1 = f1;
    }

    public int getF8() {
        return f8;
    }

    public void setF8(int f8) {
        this.f8 = f8;
    }

    public int getF24() {
        return f24;
    }

    public void setF24(int f24) {
        this.f24 = f24;
    }
}
