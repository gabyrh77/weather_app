package com.example.gaby_.weatherapplication.utils;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.gaby_.weatherapplication.api.WeatherResponse;

/**
 * Created by gaby_ on 20/2/2016.
 */
public class WeatherParcelable implements Parcelable {
    private String imageData;
    private String cityNameData;
    private String weatherDescData;
    private float temperatureData;
    private float humidityData;
    private float windSpeedData;

    public WeatherParcelable(WeatherResponse response){
        if(response!=null){
            if(response.getWeather()!=null && response.getWeather().size()>0){
                WeatherResponse.WeatherData wData = response.getWeather().get(0);
                imageData = wData.getIcon()!=null?
                        wData.getIcon():"";
                weatherDescData = wData.getDescription();
            }
            cityNameData = response.getName();
            if(response.getMain()!=null){
                humidityData = response.getMain().getHumidity()!=null?
                        response.getMain().getHumidity():0;
                temperatureData = response.getMain().getTemp()!=null?
                        response.getMain().getTemp():0;
            }
            if(response.getWind()!=null){
                windSpeedData = response.getWind().getSpeed()!=null?
                        response.getWind().getSpeed():0;
            }
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageData);
        dest.writeString(cityNameData);
        dest.writeString(weatherDescData);
        dest.writeFloat(temperatureData);
        dest.writeFloat(humidityData);
        dest.writeFloat(windSpeedData);
    }

    public static final Parcelable.Creator<WeatherParcelable> CREATOR
            = new Parcelable.Creator<WeatherParcelable>() {
        public WeatherParcelable createFromParcel(Parcel in) {
            return new WeatherParcelable(in);
        }

        public WeatherParcelable[] newArray(int size) {
            return new WeatherParcelable[size];
        }
    };

    private WeatherParcelable(Parcel in) {
        imageData = in.readString();
        cityNameData = in.readString();
        weatherDescData = in.readString();
        temperatureData = in.readFloat();
        humidityData = in.readFloat();
        windSpeedData = in.readFloat();
    }

    public String getImageData() {
        return imageData;
    }

    public String getCityNameData() {
        return cityNameData;
    }

    public String getWeatherDescData() {
        return weatherDescData;
    }

    public float getTemperatureData() {
        return temperatureData;
    }

    public float getHumidityData() {
        return humidityData;
    }

    public float getWindSpeedData() {
        return windSpeedData;
    }
}
