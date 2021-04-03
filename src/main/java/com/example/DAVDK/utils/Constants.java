package com.example.DAVDK.utils;

public class Constants {
    public static interface database {
        String URL = "jdbc:mysql://us-cdbr-east-03.cleardb.com/heroku_b078670462eab24?reconnect=true&characterEncoding=utf8";
        String USERNAME = "b682757775bfd9";
        String PASSWORD = "31d29d1d";
        String SCHEMA_NAME = "heroku_b078670462eab24";
    }
    public static interface QR {
        String QR_BASE_URL = "https://chart.googleapis.com/chart?chs=300x300&cht=qr&choe=UTF-8&chl=";
    }

    public static interface EMAIL {
        String HOST = "smtp.gmail.com";
        String PORT = "587";
//        String FROM = "philong07712@gmail.com";
//        String PASSWORD = "Kid516223";
        String FROM = "hugoclub.dut@gmail.com";
        String PASSWORD = "anhthuongem100";
    }

    public static interface GOOGLE_SHEET {
        String PROJECT_ID =  "1cw2bR6mMiZzKCOg1UD7ih-ufR60LgscUwzTM1xRJNgo";
        String EMAIL_ID = "1cw2bR6mMiZzKCOg1UD7ih-ufR60LgscUwzTM1xRJNgo";
        int MAX_ROW = 100;
    }
}
