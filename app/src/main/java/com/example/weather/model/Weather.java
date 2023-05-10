package com.example.weather.model;

public class Weather {
    protected String description;
    protected String icon;
    protected Integer id;
    protected String main;

    public Weather(String description, String icon, Integer id, String main) {
        this.description = description;
        this.icon = icon;
        this.id = id;
        this.main = main;
    }

    public Weather(Weather weather) {
        this.description = weather.description;
        this.icon = weather.icon;
        this.id = weather.id;
        this.main = weather.main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }
}
