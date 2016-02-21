package com.example.gaby_.weatherapplication.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gaby_.weatherapplication.R;
import com.example.gaby_.weatherapplication.utils.Utils;
import com.example.gaby_.weatherapplication.utils.WeatherParcelable;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {
    private static final String ARG_WEATHER_INFO = "param_weather";

    private WeatherParcelable mWeatherInfo;

    public DetailFragment() {
    }

    public static DetailFragment newInstance(WeatherParcelable weatherInfo) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_WEATHER_INFO, weatherInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState==null) {
            if (getArguments() != null) {
                mWeatherInfo = getArguments().getParcelable(ARG_WEATHER_INFO);
            }
        }else{
            mWeatherInfo = savedInstanceState.getParcelable(ARG_WEATHER_INFO);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARG_WEATHER_INFO, mWeatherInfo);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        TextView cityNameView = (TextView) view.findViewById(R.id.city_name_text);
        TextView weatherDescView = (TextView) view.findViewById(R.id.weather_desc_text);
        TextView tempView = (TextView) view.findViewById(R.id.temperature_text);
        TextView humidityView = (TextView) view.findViewById(R.id.humidity_text);
        TextView windView = (TextView) view.findViewById(R.id.wind_text);
        ImageView weatherIconView = (ImageView) view.findViewById(R.id.weather_icon_view);
        Glide.with(getContext()).load(Utils.getImageUrl(getContext(), mWeatherInfo.getImageData())).into(weatherIconView);
        cityNameView.setText(mWeatherInfo.getCityNameData());
        weatherDescView.setText(mWeatherInfo.getWeatherDescData());
        tempView.setText(getString(R.string.temperature_format, mWeatherInfo.getTemperatureData()));
        humidityView.setText(getString(R.string.percent_format, mWeatherInfo.getHumidityData()));
        windView.setText(getString(R.string.speed_format, mWeatherInfo.getWindSpeedData()));
        return view;
    }
}
