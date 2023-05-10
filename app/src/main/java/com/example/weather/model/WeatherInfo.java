package com.example.weather.model;

import java.util.List;

public class WeatherInfo {
    protected CurrentWeather current;
    protected List<DailyWeather> daily;
    protected List<CurrentWeather> hourly;

    public WeatherInfo(CurrentWeather current, List<DailyWeather> daily, List<CurrentWeather> hourly) {
        this.current = current;
        this.daily = daily;
        this.hourly = hourly;
    }

    public WeatherInfo(WeatherInfo weatherInfo) {
        this.current = weatherInfo.current;
        this.daily = weatherInfo.daily;
        this.hourly = weatherInfo.hourly;
    }

    public CurrentWeather getCurrent() {
        return current;
    }

    public void setCurrent(CurrentWeather current) {
        this.current = current;
    }

    public List<DailyWeather> getDaily() {
        return daily;
    }

    public void setDaily(List<DailyWeather> daily) {
        this.daily = daily;
    }

    public List<CurrentWeather> getHourly() {
        return hourly;
    }

    public void setHourly(List<CurrentWeather> hourly) {
        this.hourly = hourly;
    }
}
