package com.example.weather.api;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.weather.DetailActivity;
import com.example.weather.R;
import com.example.weather.adapter.HoursWeatherAdapter;
import com.example.weather.constant.Config;
import com.example.weather.model.AddressInfo;
import com.example.weather.constant.Appid;
import com.example.weather.constant.Lang;
import com.example.weather.constant.Status;
import com.example.weather.constant.Units;
import com.example.weather.constant.Format;
import com.example.weather.model.DailyWeather;
import com.example.weather.model.LocationInfo;
import com.example.weather.model.Temp;
import com.example.weather.model.Weather;
import com.example.weather.model.WeatherInfo;
import com.example.weather.service.ServiceGenerator;
import com.example.weather.service.ServiceLocation;
import com.example.weather.service.Services;
import com.example.weather.adapter.CustomDailyWeatherListView;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Api{
    public static void getWeatherInfo(
            LocationInfo locationInfo,
            Context context,
            TextView txtProvince,
            TextView txtTemp,
            TextView txtUpdatedAt,
            TextView txtSunrise,
            TextView txtSunset,
            TextView txtWindSpeed,
            TextView txtHumidity,
            TextView txtCloudDensity,
            TextView txtUvIndex,
            TextView txtWeatherInfo,
            ImageView imgViewIconWeather,
            ListView lvDailyWeather,
            RecyclerView rcvHoursWeather,
            SwipeRefreshLayout swipeRefreshLayout,
            TextView txtMaxTemp,
            TextView txtMinTemp,
            View view
    ) {
        Log.i("INFO", "getWeatherInfo::is called");
        try {
            //call api
            swipeRefreshLayout.setRefreshing(true);
            Gson gson = new Gson();
            ApiService service = ServiceGenerator.createService(ApiService.class);
            Boolean isDone = false;

            setOldDataIntoUi(
                    context,
                    txtProvince,
                    txtTemp,
                    txtUpdatedAt,
                    txtSunrise,
                    txtSunset,
                    txtWindSpeed,
                    txtHumidity,
                    txtCloudDensity,
                    txtUvIndex,
                    txtWeatherInfo,
                    imgViewIconWeather,
                    lvDailyWeather,
                    rcvHoursWeather,
                    txtMaxTemp,
                    txtMinTemp,
                    view
            );

            if(locationInfo != null && locationInfo.getLocation() != null){
                Location location = locationInfo.getLocation();

                service.getWeatherInfo(
                        location.getLatitude(),
                        location.getLongitude(),
                        Units.METRIC,
                        Appid.APPID,
                        Lang.VIETNAMESE).enqueue(new Callback<WeatherInfo>() {

                    @Override
                    public void onResponse(Call<WeatherInfo> call, Response<WeatherInfo> response) {
                        WeatherInfo info = response.body();
                        com.example.weather.model.Response<AddressInfo> addressResponse = ServiceLocation.getCityName(
                                context,
                                locationInfo
                        );

                        if(addressResponse.getStatus().equals(Status.SUCCESS)){
                            AddressInfo addressInfo = addressResponse.getData();
                            String addressInfoStr = gson.toJson(addressInfo);
                            Services.saveStateData(
                                    context,
                                    addressInfoStr,
                                    Config.PREFERENCE_ADDRESS_INFO_FILE,
                                    Config.ADDRESS_INFO_KEY
                            );
                            txtProvince.setText(addressInfo.getProvince());
                        }

                        if (info != null) {
                            long unixTime = System.currentTimeMillis() / 1000L;
                            info.getCurrent().setDt(unixTime);
                            String strJson = gson.toJson(info);
                            Services.saveStateData(
                                    context,
                                    strJson,
                                    Config.PREFERENCE_WEATHER_INFO_FILE,
                                    Config.WEATHER_INFO_KEY
                            );

                            Log.i("INFO::getWeatherInfo::onResponse", "get weather success");
                            setDataIntoUi(
                                    info,
                                    context,
                                    txtTemp,
                                    txtUpdatedAt,
                                    txtSunrise,
                                    txtSunset,
                                    txtWindSpeed,
                                    txtHumidity,
                                    txtCloudDensity,
                                    txtUvIndex,
                                    txtWeatherInfo,
                                    imgViewIconWeather,
                                    lvDailyWeather,
                                    rcvHoursWeather,
                                    txtMaxTemp,
                                    txtMinTemp,
                                    view
                            );

                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherInfo> call, Throwable t) {
                        Log.e("INFO::getWeatherInfo", "get weather info failed");
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }else{
                swipeRefreshLayout.setRefreshing(false);
            }
        } catch (Exception e) {
            Log.e("ERROR::getWeatherInfo", "" + e.toString());
            txtProvince.setText("Không thể lấy thông tin thời tiết.");
            throw new Error("" + e.getMessage());
        }
    }

    public static void setDataIntoUi(
            WeatherInfo info,
            Context context,
            TextView txtTemp,
            TextView txtUpdatedAt,
            TextView txtSunrise,
            TextView txtSunset,
            TextView txtWindSpeed,
            TextView txtHumidity,
            TextView txtCloudDensity,
            TextView txtUvIndex,
            TextView txtWeatherInfo,
            ImageView imgViewIconWeather,
            ListView lvDailyWeather,
            RecyclerView rcvHoursWeather,
            TextView txtMaxTemp,
            TextView txtMinTemp,
            View view
    ){
        Log.i("INFO::setDataIntoUi", "is called");
        try{
            Weather weather = new Weather(info.getCurrent().getWeather().get(0));
            com.example.weather.model.Response<Integer> iconResponse = Services.getWeatherIcon(weather.getIcon());
            String firstLetter  = weather.getDescription().substring(0, 1);
            String remainingLetters  = weather.getDescription().substring(1, weather.getDescription().length());
            List<DailyWeather> dailyWeathers = info.getDaily();

            //Set recycle view for hours weather info
            HoursWeatherAdapter hoursWeatherAdapter = new HoursWeatherAdapter();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
            rcvHoursWeather.setLayoutManager(linearLayoutManager);
            hoursWeatherAdapter.setDate(info.getHourly());
            rcvHoursWeather.setAdapter(hoursWeatherAdapter);


            //Set list view for daily weather info
            CustomDailyWeatherListView adapter = new CustomDailyWeatherListView(
                    context,
                    R.layout.custom_daily_wether_lv,
                    info.getDaily()
            );
            lvDailyWeather.setAdapter(adapter);
            lvDailyWeather.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try{
                        if(dailyWeathers.size() >= position){
                            Log.i("INFO::setDataIntoUi", "Clicked");
                            Intent intent = new Intent(context, DetailActivity.class);
                            Gson gson = new Gson();
                            String dailyWeatherJson = gson.toJson(dailyWeathers.get(position));

                            intent.putExtra("dailyWeather", dailyWeatherJson);
                            intent.putExtra("dt", info.getCurrent().getDt());
                            context.startActivity(intent);
                        }
                    }catch (Exception e){
                        Log.e("ERROR::setDataIntoUi", "" + e.getMessage());
                    }
                }
            });

            txtTemp.setText(String.format("%.0f", info.getCurrent().getTemp()) + "°");
            txtCloudDensity.setText(info.getCurrent().getClouds() + "%");
            txtWindSpeed.setText(info.getCurrent().getWind_speed() + "m/s");
            txtHumidity.setText(info.getCurrent().getHumidity() + "%");
            txtUvIndex.setText(info.getCurrent().getUvi() + "");
            txtWeatherInfo.setText(firstLetter.toUpperCase() + remainingLetters);

            com.example.weather.model.Response<String> dateResponse = Services.convertUnixTimestampToDateString(
                    info.getCurrent().getDt(),
                    Format.DATE_TIME);
            com.example.weather.model.Response<String> timeSunrise = Services.convertUnixTimestampToDateString(
                    info.getCurrent().getSunrise(),
                    Format.TIME);
            com.example.weather.model.Response<String> timeSunset = Services.convertUnixTimestampToDateString(
                    info.getCurrent().getSunset(),
                    Format.TIME);
            com.example.weather.model.Response<Integer> hourResponse = Services.getHour();

            if(dateResponse.getStatus().equals(Status.SUCCESS)){
                txtUpdatedAt.setText("Cập nhật vào lúc: " + dateResponse.getData());
            }

            if(hourResponse.getStatus().equals(Status.SUCCESS)){
                GradientDrawable gradientDrawable = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        Format.GRADIENT_DAY_V2
                );

                if(hourResponse.getData() < 6 || hourResponse.getData() > 17){
                    gradientDrawable.setColors(Format.GRADIENT_NIGHT);
                }

                view.setBackground(gradientDrawable);
            }

            if(timeSunrise.getStatus().equals(Status.SUCCESS)){
                txtSunrise.setText(timeSunrise.getData());
            }

            if(timeSunset.getStatus().equals(Status.SUCCESS)){
                txtSunset.setText(timeSunset.getData());
            }

            if(iconResponse.getStatus().equals(Status.SUCCESS)){
                imgViewIconWeather.setImageResource(iconResponse.getData());
            }

            if(dailyWeathers.size() > 0) {
                DailyWeather dailyWeather = dailyWeathers.get(0);
                Temp temp = dailyWeather.getTemp();

                txtMaxTemp.setText(String.format("%.0f", temp.getMax()) + "°C");
                txtMinTemp.setText(String.format("%.0f", temp.getMin()) + "°C");
            }

            Log.i("INFO::setDataIntoUi", "success");
            return;
        }catch (Exception e){
            Log.e("ERROR::setDataIntoUi", "" + e.getMessage());
            return;
        }
    }

    public static void setOldDataIntoUi(
            Context context,
            TextView txtProvince,
            TextView txtTemp,
            TextView txtUpdatedAt,
            TextView txtSunrise,
            TextView txtSunset,
            TextView txtWindSpeed,
            TextView txtHumidity,
            TextView txtCloudDensity,
            TextView txtUvIndex,
            TextView txtWeatherInfo,
            ImageView imgViewIconWeather,
            ListView lvDailyWeather,
            RecyclerView rcvHoursWeather,
            TextView txtMaxTemp,
            TextView txtMinTemp,
            View view
    ){
        try {
            Gson gson = new Gson();
            //get weather old data
            String weatherInfoResponse = Services.getSavedStateData(
                    context,
                    Config.PREFERENCE_WEATHER_INFO_FILE,
                    Config.WEATHER_INFO_KEY
            );
            //get address old data
            String addressResponse =  Services.getSavedStateData(
                    context,
                    Config.PREFERENCE_ADDRESS_INFO_FILE,
                    Config.ADDRESS_INFO_KEY
            );

            if(addressResponse != null){
                AddressInfo addressInfo = gson.fromJson(
                        addressResponse,
                        AddressInfo.class
                );

                txtProvince.setText(addressInfo.getProvince());
            }

            if(weatherInfoResponse != null){
                Log.i("INFO", "setOldDataIntoUi::get data success");
                WeatherInfo weatherInfo = gson.fromJson(
                        weatherInfoResponse,
                        WeatherInfo.class
                );

                setDataIntoUi(
                        weatherInfo,
                        context,
                        txtTemp,
                        txtUpdatedAt,
                        txtSunrise,
                        txtSunset,
                        txtWindSpeed,
                        txtHumidity,
                        txtCloudDensity,
                        txtUvIndex,
                        txtWeatherInfo,
                        imgViewIconWeather,
                        lvDailyWeather,
                        rcvHoursWeather,
                        txtMaxTemp,
                        txtMinTemp,
                        view
                );

                return;
            }

            Log.i("INFO", "setOldDataIntoUi::get data failed");
            txtProvince.setText("Không thể lấy thông tin thời tiết.");

            return;
        }catch (Exception e){
            Log.e("ERROR", "setOldDataIntoUi::" + e.getMessage());
        }
    }
}