package com.example.DAVDK.models;

public class Predict {
    int f1, f8, f24;
    public Predict(int f1, int f8, int f24) {
        this.f1 = f1;
        this.f8 = f8;
        this.f24 = f24;
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
