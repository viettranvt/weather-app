package com.example.weather.model;

public class FeelsLike {
    protected Double day;
    protected Double eve;
    protected Double morn;
    protected Double night;

    public FeelsLike(Double day, Double eve, Double morn, Double night) {
        this.day = day;
        this.eve = eve;
        this.morn = morn;
        this.night = night;
    }

    public Double getDay() {
        return day;
    }

    public void setDay(Double day) {
        this.day = day;
    }

    public Double getEve() {
        return eve;
    }

    public void setEve(Double eve) {
        this.eve = eve;
    }

    public Double getMorn() {
        return morn;
    }

    public void setMorn(Double morn) {
        this.morn = morn;
    }

    public Double getNight() {
        return night;
    }

    public void setNight(Double night) {
        this.night = night;
    }
}
