package com.example.DAVDK.models;

public class Recommend {
    int aqi;
    String sensitive;
    String effect;
    String caution;
    public Recommend(int aqi, String sensitive, String effect, String caution) {
        this.aqi = aqi;
        this.sensitive = sensitive;
        this.effect = effect;
        this.caution = caution;
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
}
