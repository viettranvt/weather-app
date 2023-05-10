package com.example.weather.service;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.weather.R;
import com.example.weather.constant.Config;
import com.example.weather.constant.Format;
import com.example.weather.model.Response;
import com.example.weather.constant.Status;
import com.example.weather.model.WeatherInfo;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Services {
    public static boolean checkPermission(Context context) {
        Log.i("INFO", "checkPermission::is called");
        try{
            for(int i = 0; i< Config.PERMISSION_ALL.length; i++){
                int result = ContextCompat.checkSelfPermission(context, Config.PERMISSION_ALL[i]);
                if(result == PackageManager.PERMISSION_GRANTED){
                    continue;
                }
                else {
                    Log.i("INFO", "checkPermission::permission denied");
                    return false;
                }
            }

            Log.i("INFO", "checkPermission::permission accepted");
            return true;
        }catch (Exception e){
            Log.e("ERROR::checkPermission", "" + e.getMessage());
            return false;
        }
    }

    public static Response<String> convertUnixTimestampToDateString (
            Long unixTimestamp,
            String format
    ){
        Log.i("INFO", "convertUnixTimestampToDateString::is called");
        try{
            Date date = new java.util.Date(unixTimestamp*1000L);
            SimpleDateFormat sdf = new java.text.SimpleDateFormat(format);
            TimeZone tz = TimeZone.getDefault();
            sdf.setTimeZone(tz);

            Log.i("INFO", "convertUnixTimestampToDateString::success");
            return new Response<String>(
                    Status.SUCCESS,
                    "",
                    sdf.format(date).toString()
            );
        }catch (Exception e){
            Log.e("ERROR::convertUnixTimestampToDateString", "" + e.getMessage());
            return new Response<String>(
                    Status.INTERNAL_ERROR,
                    "" + e.getMessage(),
                    null
            );
        }
    }

    public static Response<String> getDayInWeek (Long unixTimestamp){
        Log.i("INFO", "convertUnixTimestampToDateString::is called");
        try{
            Date date = new java.util.Date(unixTimestamp*1000L);
            String dayInWeek = "";
            SimpleDateFormat sdf = new java.text.SimpleDateFormat(Format.DAY_IN_WEEK);
            TimeZone tz = TimeZone.getDefault();
            sdf.setTimeZone(tz);

            switch (sdf.format(date).toString()){
                case "2":
                    dayInWeek = "T3";
                    break;
                case "3":
                    dayInWeek = "T4";
                    break;
                case "4":
                    dayInWeek = "T5";
                    break;
                case "5":
                    dayInWeek = "T6";
                    break;
                case "6":
                    dayInWeek = "T7";
                    break;
                case "7":
                    dayInWeek = "CN";
                    break;
                case "1":
                    dayInWeek = "T2";
                    break;
                default:
                    dayInWeek = sdf.format(date).toString();
                    break;
            }

            Log.i("INFO", "convertUnixTimestampToDateString::success");
            return new Response<String>(
                    Status.SUCCESS,
                    "",
                    dayInWeek
            );
        }catch (Exception e){
            Log.e("ERROR::convertUnixTimestampToDateString", "" + e.getMessage());
            return new Response<String>(
                    Status.INTERNAL_ERROR,
                    "" + e.getMessage(),
                    null
            );
        }
    }

    public static Response<Integer> getWeatherIcon (String icon){
        Log.i("INFO", "getWeatherIcon::is called");
        try{
            Response<Integer> iconResponse = new Response<>(
                    Status.SUCCESS,
                    "",
                    null
            );

            if(icon.equals("01d")){
                Log.i("INFO", "getWeatherIcon::clear_sky_day::success");
                iconResponse.setData(R.drawable.ic_clear_sky_d);

                return iconResponse;
            }

            if(icon.equals("01n")){
                Log.i("INFO", "getWeatherIcon::clear_sky_night::success");
                iconResponse.setData(R.drawable.ic_clear_sky_n);

                return iconResponse;
            }

            if(icon.equals("02d")){
                Log.i("INFO", "getWeatherIcon::few_clouds_day::success");
                iconResponse.setData(R.drawable.ic_few_clouds_d);

                return iconResponse;
            }

            if(icon.equals("02n")){
                Log.i("INFO", "getWeatherIcon::few_clouds_night::success");
                iconResponse.setData(R.drawable.ic_few_clouds_n);

                return iconResponse;
            }

            if(icon.equals("03d") || icon.equals("03n")){
                Log.i("INFO", "getWeatherIcon::scattered_clouds::success");
                iconResponse.setData(R.drawable.ic_scattered_clouds);

                return iconResponse;
            }

            if(icon.equals("04d") || icon.equals("04n")){
                Log.i("INFO", "getWeatherIcon::broken_clouds::success");
                iconResponse.setData(R.drawable.ic_broken_clouds);

                return iconResponse;
            }

            if(icon.equals("09d") || icon.equals("09n")){
                Log.i("INFO", "getWeatherIcon::shower_rain::success");
                iconResponse.setData(R.drawable.ic_shower_rain);

                return iconResponse;
            }

            if(icon.equals("10d")){
                Log.i("INFO", "getWeatherIcon::rain_day::success");
                iconResponse.setData(R.drawable.ic_rain_d);

                return iconResponse;
            }

            if(icon.equals("10n")){
                Log.i("INFO", "getWeatherIcon::rain_night::success");
                iconResponse.setData(R.drawable.ic_rain_n);

                return iconResponse;
            }

            if(icon.equals("11d")){
                Log.i("INFO", "getWeatherIcon::thunderstorm_day::success");
                iconResponse.setData(R.drawable.ic_thunderstorm_d);

                return iconResponse;
            }

            if(icon.equals("11n")){
                Log.i("INFO", "getWeatherIcon::thunderstorm_night::success");
                iconResponse.setData(R.drawable.ic_thunderstorm_n);

                return iconResponse;
            }

            if(icon.equals("13d")){
                Log.i("INFO", "getWeatherIcon::snow_day::success");
                iconResponse.setData(R.drawable.ic_snow_d);

                return iconResponse;
            }

            if(icon.equals("13n")){
                Log.i("INFO", "getWeatherIcon::snow_night::success");
                iconResponse.setData(R.drawable.ic_snow_n);

                return iconResponse;
            }

            if(icon.equals("50d"))
            {
                Log.i("INFO", "getWeatherIcon::mist_day::success");
                iconResponse.setData(R.drawable.ic_mist_d);

                return iconResponse;
            }

            Log.i("INFO", "getWeatherIcon::mist_night::success");
            iconResponse.setData(R.drawable.ic_mist_n);

            return iconResponse;
        }catch (Exception e){
            Log.e("ERROR::getWeatherIcon", "" + e.getMessage());
            return new Response<Integer>(
                    Status.INTERNAL_ERROR,
                    "" + e.getMessage(),
                    null
            );
        }
    }

    public static void saveStateData(
            Context context,
            String data,
            String preferenceNameFile,
            String keyData
    ) {
        Log.i("INFO", "saveStateData::is called");
        try{
            //this is a little <key,value> table permanently kept in memory
            SharedPreferences myPrefContainer = context.getSharedPreferences(
                    preferenceNameFile,
                    Activity.MODE_PRIVATE
            );
            //pair <key,value> to be stored represents our 'important' data
            SharedPreferences.Editor myPrefEditor = myPrefContainer.edit();

            String key = keyData , value = data;

            myPrefEditor.putString(key, value);
            myPrefEditor.commit();
            Log.i("INFO", "saveStateData::success");
        }catch (Exception e){
            Log.i("ERROR", "saveStateData::" + e.getMessage());
        }
    }

    public static String getSavedStateData(
            Context context,
            String preferenceNameFile,
            String dataKey
    ) {
        Log.i("INFO", "getSavedStateData::is called");
        try{
            // (in case it exists) use saved data telling backg color
            SharedPreferences myPrefContainer = context.getSharedPreferences(
                    preferenceNameFile,
                    Activity.MODE_PRIVATE
            );

            if (( myPrefContainer != null ) && myPrefContainer.contains(dataKey)){
                String weatherInfoStr = myPrefContainer.getString(dataKey, null);

                return weatherInfoStr;
            }

            Log.i("INFO", "getSavedStateData::success");
            return null;
        }catch (Exception e){
            Log.e("ERROR", "getSavedStateData::" + e.getMessage());
            return null;
        }
    }

    public static Response<Integer> getHour() {
        Log.i("INFO", "getHour::is called");
        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Format.HOURS);
            String hoursString = simpleDateFormat.format(new Date());

            Integer hours = Integer.parseInt(hoursString);
            return new Response<Integer>(Status.SUCCESS, "", hours);
        }catch (Exception e){
            Log.e("ERROR", "getHour::" + e.getMessage());
            return new Response<Integer>(Status.INTERNAL_ERROR, "", -1);
        }
    }
}
