package com.example.gaby_.weatherapplication.api;

import java.util.List;

public class WeatherListResponse {
    private Integer count;
    private List<WeatherResponse> list;

    public Integer getCount() {
        return count;
    }

    public List<WeatherResponse> getList() {
        return list;
    }
}
