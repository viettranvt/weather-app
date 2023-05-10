package com.example.weather.model;

import android.location.Location;

public class LocationInfo {
    protected Location location;
    protected Boolean isGpsProvider;
    protected Boolean isNetworkProvider;

    public LocationInfo(Location location, Boolean isGpsProvider, Boolean isNetworkProvider) {
        this.location = location;
        this.isGpsProvider = isGpsProvider;
        this.isNetworkProvider = isNetworkProvider;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Boolean getIsGpsProvider() {
        return isGpsProvider;
    }

    public void setIsGpsProvider(Boolean gpsProvider) {
        isGpsProvider = gpsProvider;
    }

    public Boolean getIsNetworkProvider() {
        return isNetworkProvider;
    }

    public void setIsNetworkProvider(Boolean networkProvider) {
        isNetworkProvider = networkProvider;
    }
}
