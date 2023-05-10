package com.example.weather.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather.R;
import com.example.weather.constant.Status;
import com.example.weather.model.DailyWeather;
import com.example.weather.constant.Format;
import com.example.weather.model.Response;
import com.example.weather.model.Weather;
import com.example.weather.service.Services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CustomDailyWeatherListView extends ArrayAdapter<DailyWeather> {
    Context context;
    List<DailyWeather> dailyWeathers;

    public CustomDailyWeatherListView(Context context, int layoutToBeInflated, List<DailyWeather> dailyWeathers) {
        super(context, R.layout.custom_daily_wether_lv, dailyWeathers);
        this.context = context;
        this.dailyWeathers = dailyWeathers;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(R.layout.custom_daily_wether_lv, null);

        TextView txtDailyWeatherDate = (TextView)row.findViewById(R.id.txtDailyWeatherDate);
        TextView txtDailyWeatherTemp = (TextView)row.findViewById(R.id.txtDailyWeatherTemp);
        ImageView imgDailyWeather = (ImageView)row.findViewById(R.id.imgDailyWeather);

        DailyWeather dailyWeather = dailyWeathers.get(pos);

        if(dailyWeather != null){
            Weather weather = new Weather(dailyWeather.getWeather().get(0));
            String dayInWeek = "";
            String date = "";
            String maxTemp = String.format("%.0f", dailyWeather.getTemp().getMax()) + "°";
            String minTemp = String.format("%.0f", dailyWeather.getTemp().getMin()) + "°";
            Response<String> dateResponse = Services.convertUnixTimestampToDateString(dailyWeather.getDt(), Format.DATE);
            Response<String> dayInWeekResponse = Services.getDayInWeek(dailyWeather.getDt());
            Response<Integer> imgResponse = Services.getWeatherIcon(weather.getIcon());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Format.DATE);
            String dateString = simpleDateFormat.format(new Date());

            txtDailyWeatherTemp.setText(maxTemp + "/" + minTemp);

            if(dateResponse.getStatus().equals(Status.SUCCESS)){
                date = dateResponse.getData();
            }

            if(dayInWeekResponse.getStatus().equals(Status.SUCCESS)){
                dayInWeek = dayInWeekResponse.getData();
            }

            if(imgResponse.getStatus().equals(Status.SUCCESS)){
                imgDailyWeather.setImageResource(imgResponse.getData());
            }

            if(date.equals(dateString)){
                txtDailyWeatherDate.setText("Hôm nay");
            }else {
                txtDailyWeatherDate.setText(dayInWeek + " " + date);
            }
        }

        return (row);
    }
}
