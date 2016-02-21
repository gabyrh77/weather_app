package com.example.gaby_.weatherapplication.utils;

import android.content.Context;
import android.text.TextUtils;

import com.example.gaby_.weatherapplication.R;
import com.example.gaby_.weatherapplication.api.WeatherService;

/**
 * Created by gaby_ on 20/2/2016.
 */
public class Utils {

    public static String getImageUrl(Context context, String icon){
        String url="";
        if(!TextUtils.isEmpty(icon)){
            url = WeatherService.API_URL +
                    context.getString(R.string.image_url, icon);
        }
        return url;
    }
}
