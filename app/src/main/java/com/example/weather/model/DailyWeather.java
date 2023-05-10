package com.example.weather.model;

import java.util.List;

public class DailyWeather {
    protected FeelsLike feels_like;
    protected Temp temp;
    protected Integer clouds;
    protected Double dew_point;
    protected Long dt;
    protected Integer humidity;
    protected Double moon_phase;
    protected Long moonrise;
    protected Long moonset;
    protected Double pop;
    protected Integer pressure;
    protected Long sunrise;
    protected Long sunset;
    protected Double uvi;
    protected Double wind_gust;
    protected Integer wind_deg;
    protected Double wind_speed;
    protected List<Weather> weather;

    public DailyWeather(FeelsLike feels_like, Temp temp, Integer clouds, Double dew_point, Long dt, Integer humidity, Double moon_phase, Long moonrise, Long moonset, Double pop, Integer pressure, Long sunrise, Long sunset, Double uvi, Double wind_gust, Integer wind_deg, Double wind_speed, List<Weather> weather) {
        this.feels_like = feels_like;
        this.temp = temp;
        this.clouds = clouds;
        this.dew_point = dew_point;
        this.dt = dt;
        this.humidity = humidity;
        this.moon_phase = moon_phase;
        this.moonrise = moonrise;
        this.moonset = moonset;
        this.pop = pop;
        this.pressure = pressure;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.uvi = uvi;
        this.wind_gust = wind_gust;
        this.wind_deg = wind_deg;
        this.wind_speed = wind_speed;
        this.weather = weather;
    }

    public FeelsLike getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(FeelsLike feels_like) {
        this.feels_like = feels_like;
    }

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
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

    public Double getMoon_phase() {
        return moon_phase;
    }

    public void setMoon_phase(Double moon_phase) {
        this.moon_phase = moon_phase;
    }

    public Long getMoonrise() {
        return moonrise;
    }

    public void setMoonrise(Long moonrise) {
        this.moonrise = moonrise;
    }

    public Long getMoonset() {
        return moonset;
    }

    public void setMoonset(Long moonset) {
        this.moonset = moonset;
    }

    public Double getPop() {
        return pop;
    }

    public void setPop(Double pop) {
        this.pop = pop;
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

    public Double getWind_gust() {
        return wind_gust;
    }

    public void setWind_gust(Double wind_gust) {
        this.wind_gust = wind_gust;
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
