package com.example.gaby_.weatherapplication.api;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;

public class WeatherService {
    public static final String API_URL = "http://api.openweathermap.org/";
    private static final String API_KEY = "";

    public static Observable<WeatherListResponse> fetchWeatherByCoordinates(String units, double latitude, double longitude){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        WeatherAPI api = retrofit.create(WeatherAPI.class);


        return api.fetchWeather(API_KEY, units, latitude, longitude);
    }
}
