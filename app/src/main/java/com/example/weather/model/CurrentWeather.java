package com.example.weather.model;

import java.util.List;

public class CurrentWeather {
    protected Double feels_like;
    protected Double temp;
    protected Integer clouds;
    protected Double dew_point;
    protected Long dt;
    protected Integer humidity;
    protected Integer pressure;
    protected Long sunrise;
    protected Long sunset;
    protected Double uvi;
    protected Integer visibility;
    protected Integer wind_deg;
    protected Double wind_speed;
    protected List<Weather> weather;

    public CurrentWeather(Double feels_like, Double temp, Integer clouds, Double dew_point, Long dt, Integer humidity, Integer pressure, Long sunrise, Long sunset, Double uvi, Integer visibility, Integer wind_deg, Double wind_speed, List<Weather> weather) {
        this.feels_like = feels_like;
        this.temp = temp;
        this.clouds = clouds;
        this.dew_point = dew_point;
        this.dt = dt;
        this.humidity = humidity;
        this.pressure = pressure;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.uvi = uvi;
        this.visibility = visibility;
        this.wind_deg = wind_deg;
        this.wind_speed = wind_speed;
        this.weather = weather;
    }

    public Double getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(Double feels_like) {
        this.feels_like = feels_like;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Integer getClouds() {
        return clouds;
    }

    public void setClouds(Integer clouds) {
        this.clouds = clouds;
    }

    public Double getDew_point() {
        return dew_point;
    }

    public void setDew_point(Double dew_point) {
        this.dew_point = dew_point;
    }

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Long getSunrise() {
        return sunrise;
    }

    public void setSunrise(Long sunrise) {
        this.sunrise = sunrise;
    }

    public Long getSunset() {
        return sunset;
    }

    public void setSunset(Long sunset) {
        this.sunset = sunset;
    }

    public Double getUvi() {
        return uvi;
    }

    public void setUvi(Double uvi) {
        this.uvi = uvi;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Integer getWind_deg() {
        return wind_deg;
    }

    public void setWind_deg(Integer wind_deg) {
        this.wind_deg = wind_deg;
    }

    public Double getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(Double wind_speed) {
        this.wind_speed = wind_speed;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }
}
