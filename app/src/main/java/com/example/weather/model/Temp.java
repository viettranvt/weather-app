package com.example.weather.model;

public class Temp {
    protected Double day;
    protected Double eve;
    protected Double max;
    protected Double min;
    protected Double morn;
    protected Double night;

    public Temp(Double day, Double eve, Double max, Double min, Double morn, Double night) {
        this.day = day;
        this.eve = eve;
        this.max = max;
        this.min = min;
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

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
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
