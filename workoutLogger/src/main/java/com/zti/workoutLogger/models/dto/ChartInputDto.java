package com.zti.workoutLogger.models.dto;

import java.util.Map;

public class ChartInputDto {
    private static final String KEY_NAME = "key";

    Map<String, String> data;

    public ChartInputDto() {
    }

    public ChartInputDto(String key, Map<String, String> data) {
        data.put(KEY_NAME, key);
        this.data = data;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}