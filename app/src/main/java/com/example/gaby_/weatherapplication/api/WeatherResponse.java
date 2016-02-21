package com.example.gaby_.weatherapplication.api;

import java.util.List;

public class WeatherResponse {
    private String name;
    private List<WeatherData> weather;
    private WindData wind;
    private MainData main;

    public String getName() {
        return name;
    }

    public List<WeatherData> getWeather() {
        return weather;
    }

    public WindData getWind() {
        return wind;
    }

    public MainData getMain() {
        return main;
    }

    public class WeatherData {
        private Long id;
        private String main;
        private String description;
        private String icon;

        public String getMain() {
            return main;
        }

        public String getDescription() {
            return description;
        }

        public Long getId() {
            return id;
        }

        public String getIcon() {
            return icon;
        }
    }

    public class WindData {
        private Float speed;
        private Float deg;

        public Float getSpeed() {
            return speed;
        }

        public Float getDeg() {
            return deg;
        }
    }

    public class MainData {
        private Float temp;
        private Float pressure;
        private Float humidity;

        public Float getTemp() {
            return temp;
        }

        public Float getPressure() {
            return pressure;
        }

        public Float getHumidity() {
            return humidity;
        }
    }
}


