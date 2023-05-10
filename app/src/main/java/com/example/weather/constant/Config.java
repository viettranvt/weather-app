package com.example.weather.constant;

import android.Manifest;

public class Config {
    public static final String[] PERMISSION_ALL = {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};
    public static final Integer PERMISSION_CODE = 1;
    public static final Integer PLACE_CODE = 100;
    public static final String PREFERENCE_WEATHER_INFO_FILE  = "weatherInfoFile";
    public static final String WEATHER_INFO_KEY = "weatherInfo";
    public static final String PREFERENCE_ADDRESS_INFO_FILE  = "addressInfoFile";
    public static final String ADDRESS_INFO_KEY = "addressInfo";
    public static final String API_KEY = "AIzaSyAxuA9fRTgCO3-kTuO1pKRwLBi-7IjAvtg";
}
