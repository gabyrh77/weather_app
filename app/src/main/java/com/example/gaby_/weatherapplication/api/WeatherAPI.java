package com.example.gaby_.weatherapplication.api;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface WeatherAPI {

    String API_PATH = "data/2.5/find";

    @GET(API_PATH)
    Observable<WeatherListResponse> fetchWeather(@Query("APPID") String apiKey,
                                                 @Query("units") String units,
                                                 @Query("lat") double latitude,
                                                 @Query("lon") double longitude);
}
