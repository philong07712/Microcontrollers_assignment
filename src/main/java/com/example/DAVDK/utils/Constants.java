package com.example.DAVDK.utils;

public class Constants {
    public static interface  chess {
        String BLACK = "Black";
        String RED = "Red";
        String START = "Start";
        String CONNECT = "Connect";
        String DISCONNECT = "Disconnect";
    }

    public static interface database {
        String URL = "jdbc:mysql://us-cdbr-east-03.cleardb.com/heroku_b078670462eab24?reconnect=true&characterEncoding=utf8";
        String USERNAME = "b682757775bfd9";
        String PASSWORD = "31d29d1d";
        String SCHEMA_NAME = "heroku_b078670462eab24";
    }

    public static interface good {
        String coSensitive = "People with asthma are the group most at risk.";
        String coEffect = "None";
        String coCaution = "None";
        String pmSensitive = "People with respiratory or heart disease," +
                " the elderly and children are the groups most at risk.";
        String pmEffect = "None";
        String pmCaution = "None";
    }

    public static interface moderate {
        String coSensitive = "People with asthma are the group most at risk.";
        String coEffect = "None";
        String coCaution = "None";
        String pmSensitive = "People with respiratory or heart disease," +
                " the elderly and children are the groups most at risk.";
        String pmEffect = "Unusually sensitive people should consider reducing prolonged or heavy exertion.";
        String pmCaution = "Unusually sensitive people should consider reducing prolonged or heavy exertion.";
    }

    public static interface unhealthySensitive {
        String coSensitive = "People with asthma are the group most at risk.";
        String coEffect = "Increasing likelihood of respiratory symptoms, such as chest tightness and breathing" +
                " discomfort, in people with asthma.";
        String coCaution = "People with asthma should consider limiting outdoor exertion.";
        String pmSensitive = "People with respiratory or heart disease," +
                " the elderly and children are the groups most at risk.";
        String pmEffect = "Increasing likelihood of respiratory symptoms in sensitive individuals, aggravation of heart" +
                " or lung disease and premature mortality in persons with cardiopulmonary disease and the elderly.";
        String pmCaution = "People with respiratory or heart disease, the elderly and children should " +
                "limit prolonged exertion.";
    }

    public static interface unhealthy {
        String coSensitive = "People with heart disease are the group most at risk.";
        String coEffect = "Reduced exercise tolerance due to increased cardiovascular symptoms," +
                " such as chest pain, in people with cardiovascular disease.";
        String coCaution = "People with cardiovascular disease, such as angina, should limit moderate exertion " +
                "and avoid sources of CO, such as heavy traffic.";
        String pmSensitive = "People with respiratory or heart disease," +
                " the elderly and children are the groups most at risk.";
        String pmEffect = "Increased aggravation of heart or lung disease and premature mortality in persons with" +
                " cardiopulmonary disease and the elderly; increased respiratory effects in general population.";
        String pmCaution = "People with respiratory or heart disease, the elderly and children should avoid prolonged " +
                "exertion; everyone else should limit prolonged exertion.";
    }

   public static interface veryUnhealthy {
       String coSensitive = "People with heart disease are the group most at risk.";
       String coEffect = "Significant aggravation of cardiovascular symptoms, such as chest pain," +
               " in people with cardiovascular disease.";
       String coCaution = "People with cardiovascular disease, such as angina, should avoid exertion and sources of CO," +
               " such as heavy traffic.";
       String pmSensitive = "People with respiratory or heart disease, " +
               "the elderly and children are the groups most at risk.";
       String pmEffect = "Significant aggravation of heart or lung disease and premature mortality in persons with " +
               "cardiopulmonary disease and the elderly; significant increase in respiratory effects " +
               "in general population.";
       String pmCaution = "People with respiratory or heart disease, the elderly and children should avoid any outdoor " +
               "activity; everyone else should avoid prolonged exertion.";
   }

   public static interface hazardous {
       String coSensitive = "People with heart disease are the group most at risk.";
       String coEffect = " Serious aggravation of cardiovascular symptoms, such as chest pain, in people with " +
               "cardiovascular disease; impairment of strenuous activities in general population.";
       String coCaution = "People with cardiovascular disease, such as angina, should avoid exertion and sources of CO," +
               " such as heavy traffic; everyone else should limit heavy exertion.";
       String pmSensitive = "People with respiratory or heart disease, " +
               "the elderly and children are the groups most at risk.";
       String pmEffect = "Serious aggravation of heart or lung disease and premature mortality in persons with " +
               "cardiopulmonary disease and the elderly; serious risk of respiratory effects in general population.";
       String pmCaution = "Everyone should avoid any outdoor exertion; people with respiratory or heart disease, " +
               "the elderly and children should remain indoors.";
   }
}
