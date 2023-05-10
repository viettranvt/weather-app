package com.example.weather.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.R;
import com.example.weather.constant.Format;
import com.example.weather.constant.Status;
import com.example.weather.model.CurrentWeather;
import com.example.weather.model.Response;
import com.example.weather.model.Weather;
import com.example.weather.service.Services;

import java.util.List;

public class HoursWeatherAdapter extends RecyclerView.Adapter<HoursWeatherAdapter.HoursWeatherViewHolder> {
    private List<CurrentWeather> currentWeathers;

    public void setDate(List<CurrentWeather> currentWeathers){
        this.currentWeathers = currentWeathers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HoursWeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        try {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_hours_weather_rcv, parent, false);
            return new HoursWeatherViewHolder(view);
        }catch (Exception e){
            Log.e("ERROR::onCreateViewHolder", ""+ e.getMessage());
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull HoursWeatherViewHolder holder, int position) {
        try{
            CurrentWeather hourWeather = currentWeathers.get(position);

            if(hourWeather == null){
                return;
            }

            Weather weather = new Weather(hourWeather.getWeather().get(0));
            Response<String> timeResponse = Services.convertUnixTimestampToDateString(hourWeather.getDt(), Format.TIME);
            Response<Integer> imgResponse = Services.getWeatherIcon(weather.getIcon());

            holder.txtHourWeatherTemp.setText(String.format("%.0f", hourWeather.getTemp()) + "°");

            if(timeResponse.getStatus().equals(Status.SUCCESS)){
                holder.txtHourWeatherDate.setText(timeResponse.getData());

                if(timeResponse.getData().equals("00:00")){
                    Response<String> dateResponse = Services.convertUnixTimestampToDateString(hourWeather.getDt(), Format.DATE);

                    if(dateResponse.getStatus().equals(Status.SUCCESS)){
                        holder.txtHourWeatherDate.setText(dateResponse.getData());
                    }
                }
            }

            if(imgResponse.getStatus().equals(Status.SUCCESS)){
                holder.imgHourWeather.setImageResource(imgResponse.getData());
            }
        }catch (Exception e){
            Log.e("ERROR::onBindViewHolder", ""+ e.getMessage());
            return;
        }
    }

    @Override
    public int getItemCount() {
        if(currentWeathers != null){
            return currentWeathers.size();
        }

        return 0;
    }

    public class HoursWeatherViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgHourWeather;
        private TextView txtHourWeatherDate;
        private TextView txtHourWeatherTemp;

        public HoursWeatherViewHolder(@NonNull View itemView){
            super(itemView);

            imgHourWeather = itemView.findViewById(R.id.imgHoursWeather);
            txtHourWeatherDate = itemView.findViewById(R.id.txtHourWeatherDate);
            txtHourWeatherTemp = itemView.findViewById(R.id.txtHourWeatherTemp);
        }
    }
}
