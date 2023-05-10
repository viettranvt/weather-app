package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather.constant.Format;
import com.example.weather.constant.Status;
import com.example.weather.model.DailyWeather;
import com.example.weather.model.Response;
import com.example.weather.model.Weather;
import com.example.weather.service.Services;
import com.google.gson.Gson;

public class DetailActivity extends AppCompatActivity {
    private TextView txtDate;
    private TextView txtMaxTempDetail;
    private TextView txtMinTempDetail;
    private TextView txtPressureDetail;
    private TextView txtSunriseDetail;
    private TextView txtSunsetDetail;
    private TextView txtMoonriseDetail;
    private TextView txtMoonsetDetail;
    private TextView txtWindSpeedDetail;
    private TextView txtHumidityDetail;
    private TextView txtCloudsDensityDetail;
    private TextView txtUvIndexDetail;
    private TextView txtWeatherInfoDetail;
    private TextView txtMorningTemp;
    private TextView txtAfternoonTemp;
    private TextView txtEveningTemp;
    private TextView txtNightTemp;
    private ImageView imgViewBack;
    private View view;
    private ImageView imgViewWeatherDetail;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        try{
            init();

            Intent intent = getIntent();
            String dailyWeatherStr = intent.getStringExtra("dailyWeather");
            Long dt = intent.getLongExtra("dt", 0);
            DailyWeather dailyWeather = gson.fromJson(dailyWeatherStr, DailyWeather.class);

            Response<Integer> hourResponse = Services.getHour();
            Response<String> dateResponse = Services.convertUnixTimestampToDateString(
                    dailyWeather.getDt(),
                    Format.FULL_DATE
            );

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

            Response<String> sunriseTime = Services.convertUnixTimestampToDateString(
                    dailyWeather.getSunrise(),
                    Format.TIME
            );
            Response<String> sunsetTime = Services.convertUnixTimestampToDateString(
                    dailyWeather.getSunset(),
                    Format.TIME
            );
            Response<String> moonriseTime = Services.convertUnixTimestampToDateString(
                    dailyWeather.getMoonrise(),
                    Format.TIME
            );
            Response<String> moonsetTime = Services.convertUnixTimestampToDateString(
                    dailyWeather.getMoonset(),
                    Format.TIME
            );
            Weather weather = new Weather(dailyWeather.getWeather().get(0));
            Response<Integer> iconResponse = Services.getWeatherIcon(weather.getIcon());
            String firstLetter  = weather.getDescription().substring(0, 1);
            String remainingLetters  = weather.getDescription().substring(1, weather.getDescription().length());

            if(dateResponse.getStatus().equals(Status.SUCCESS)){
                txtDate.setText(dateResponse.getData());
            }

            if(sunriseTime.getStatus().equals(Status.SUCCESS)){
                txtSunriseDetail.setText(sunriseTime.getData());
            }

            if(sunsetTime.getStatus().equals(Status.SUCCESS)){
                txtSunsetDetail.setText(sunsetTime.getData());
            }

            if(moonriseTime.getStatus().equals(Status.SUCCESS)){
                txtMoonriseDetail.setText(moonriseTime.getData());
            }

            if(moonsetTime.getStatus().equals(Status.SUCCESS)){
                txtMoonsetDetail.setText(moonsetTime.getData());
            }

            if(iconResponse.getStatus().equals(Status.SUCCESS)){
                imgViewWeatherDetail.setImageResource(iconResponse.getData());
            }

            txtMaxTempDetail.setText(String.format("%.0f", dailyWeather.getTemp().getMax()) + "°C");
            txtMinTempDetail.setText(String.format("%.0f", dailyWeather.getTemp().getMin()) + "°C");
            txtMorningTemp.setText(String.format("%.0f", dailyWeather.getTemp().getMorn()) + "°");
            txtAfternoonTemp.setText(String.format("%.0f", dailyWeather.getTemp().getDay()) + "°");
            txtEveningTemp.setText(String.format("%.0f", dailyWeather.getTemp().getEve()) + "°");
            txtNightTemp.setText(String.format("%.0f", dailyWeather.getTemp().getNight()) + "°");
            txtPressureDetail.setText(dailyWeather.getPressure() + "hPa");
            txtWindSpeedDetail.setText(dailyWeather.getWind_speed() + "m/s");
            txtHumidityDetail.setText(dailyWeather.getHumidity() + "%");
            txtCloudsDensityDetail.setText(dailyWeather.getClouds() + "%");
            txtUvIndexDetail.setText(dailyWeather.getUvi() + "");
            txtWeatherInfoDetail.setText(firstLetter.toUpperCase() + remainingLetters);
        }catch (Exception e){
            Log.e("ERROR::DetailActivity::onCreated", "" + e.getMessage());
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
        }
    }

    private void init() {
        txtDate = (TextView)findViewById(R.id.txtDate);
        txtMaxTempDetail = (TextView)findViewById(R.id.txtMaxTemp_detail);
        txtMinTempDetail = (TextView)findViewById(R.id.txtMinTemp_detail);
        txtPressureDetail = (TextView)findViewById(R.id.txtPressure_detail);
        txtSunriseDetail = (TextView)findViewById(R.id.txtSunrise_detail);
        txtSunsetDetail = (TextView)findViewById(R.id.txtSunset_detail);
        txtMoonriseDetail = (TextView)findViewById(R.id.txtMoonrise_detail);
        txtMoonsetDetail = (TextView)findViewById(R.id.txtMoonset_detail);
        txtWindSpeedDetail = (TextView)findViewById(R.id.txtWindSpeed_detail);
        txtHumidityDetail = (TextView)findViewById(R.id.txtHumidity_detail);
        txtCloudsDensityDetail = (TextView)findViewById(R.id.txtCloudDensity_detail);
        txtUvIndexDetail = (TextView)findViewById(R.id.txtUvIndex_detail);
        txtWeatherInfoDetail = (TextView)findViewById(R.id.txtWeatherInfo_detail);
        txtMorningTemp = (TextView)findViewById(R.id.txtMorningTemp);
        txtAfternoonTemp = (TextView)findViewById(R.id.txtAfternoonTemp);
        txtEveningTemp = (TextView)findViewById(R.id.txtEveningTemp);
        txtNightTemp = (TextView)findViewById(R.id.txtNightTemp);
        imgViewWeatherDetail = (ImageView)findViewById(R.id.imgViewWeather_detail);
        imgViewBack = (ImageView)findViewById(R.id.imgViewBack);
        view = findViewById(R.id.detailLayout);

        imgViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}