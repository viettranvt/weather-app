package com.example.weather.api;

import com.example.weather.model.WeatherInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    //created gson (parse json to string)
   Gson gson = new GsonBuilder()
            .setDateFormat("dd-MM-yyyy HH:mm:ss")
            .create();

    //created method., get weather info
    @GET("data/2.5/onecall")
    Call<WeatherInfo> getWeatherInfo(@Query("lat") Double lat,
                                     @Query("lon") Double lon,
                                     @Query("units") String units,
                                     @Query("appid") String appid,
                                     @Query("lang") String lang);
}
