package com.example.DAVDK.data.service;

import com.example.DAVDK.data.dao.DataDao;
import com.example.DAVDK.models.Data;
import com.example.DAVDK.utils.Constants;
import com.example.DAVDK.utils.DataManagement;

import java.sql.Timestamp;
import java.util.List;

public class DataService {
    DataDao dao = new DataDao();
    public static DataService instance;

    private DataService() {

    }

    public static DataService getInstance() {
        if (instance == null) {
            instance = new DataService();
        }
        return instance;
    }

    public List<Data> getAllData() {
        return dao.getData();
    }

    public Data getLatestData() {
        return dao.getLatestData();
    }

    public void addData(Data data, int coAqi, int pmAqi) {
        dao.addData(data);
        boolean isCo;
        String sensitive = "", effect = "", caution = "";
        isCo = data.getAqi() == coAqi;
        int aqi = data.getAqi();
        if (aqi > 0) {
            if (isCo) {
                sensitive = Constants.good.coSensitive;
                effect = Constants.good.coEffect;
                caution = Constants.good.coCaution;
            } else {
                sensitive = Constants.good.pmSensitive;
                effect = Constants.good.pmEffect;
                caution = Constants.good.pmCaution;
            }
        }
        if (aqi > 50) {
            if (isCo) {
                sensitive = Constants.moderate.coSensitive;
                effect = Constants.moderate.coEffect;
                caution = Constants.moderate.coCaution;
            } else {
                sensitive = Constants.moderate.pmSensitive;
                effect = Constants.moderate.pmEffect;
                caution = Constants.moderate.pmCaution;
            }
        }
        if (aqi > 100) {
            if (isCo) {
                sensitive = Constants.unhealthySensitive.coSensitive;
                effect = Constants.unhealthySensitive.coEffect;
                caution = Constants.unhealthySensitive.coCaution;
            } else {
                sensitive = Constants.unhealthySensitive.pmSensitive;
                effect = Constants.unhealthySensitive.pmEffect;
                caution = Constants.unhealthySensitive.pmCaution;
            }
        }
        if (aqi > 150) {
            if (isCo) {
                sensitive = Constants.unhealthy.coSensitive;
                effect = Constants.unhealthy.coEffect;
                caution = Constants.unhealthy.coCaution;
            } else {
                sensitive = Constants.unhealthy.pmSensitive;
                effect = Constants.unhealthy.pmEffect;
                caution = Constants.unhealthy.pmCaution;
            }
        }
        if (aqi > 200) {
            if (isCo) {
                sensitive = Constants.veryUnhealthy.coSensitive;
                effect = Constants.veryUnhealthy.coEffect;
                caution = Constants.veryUnhealthy.coCaution;
            } else {
                sensitive = Constants.veryUnhealthy.pmSensitive;
                effect = Constants.veryUnhealthy.pmEffect;
                caution = Constants.veryUnhealthy.pmCaution;
            }
        }
        if (aqi > 300) {
            if (isCo) {
                sensitive = Constants.hazardous.coSensitive;
                effect = Constants.hazardous.coEffect;
                caution = Constants.hazardous.coCaution;
            } else {
                sensitive = Constants.hazardous.pmSensitive;
                effect = Constants.hazardous.pmEffect;
                caution = Constants.hazardous.pmCaution;
            }
        }
        DataManagement.getInstance().setSensitive(sensitive);
        DataManagement.getInstance().setEffect(effect);
        DataManagement.getInstance().setCaution(caution);
        DataManagement.getInstance().setAqi(aqi);
    }
}


class main {
    public static void main(String[] args) {
    }
}