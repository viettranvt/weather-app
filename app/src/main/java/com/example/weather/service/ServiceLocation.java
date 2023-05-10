package com.example.weather.service;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

import com.example.weather.model.AddressInfo;
import com.example.weather.constant.Status;
import com.example.weather.model.LocationInfo;
import com.example.weather.model.Response;

import java.util.List;
import java.util.Locale;

public class ServiceLocation {
    public static Response<AddressInfo> getCityName(
            Context context,
            LocationInfo locationInfo
    ) {
        Log.i("INFO", "getCityName::is called");
        try {
            Location location = locationInfo.getLocation();
            AddressInfo addressInfo = new AddressInfo(
                    null,
                    null,
                    null
            );
            Geocoder gcd = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = gcd.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    1
            );

            if(!addresses.isEmpty() && addresses.size() > 0){
                addressInfo.setProvince(addresses.get(0).getAdminArea());
                String addressLine = addresses.get(0).getAddressLine(0);
                Log.i("INFO", addressLine);

                if(addressLine != null && locationInfo.getIsGpsProvider()){
                    String[] separated = addressLine.split(", ");
                    addressInfo.setAddress(addressLine);

                    if(separated.length > 2){
                        addressInfo.setDistrict(separated[separated.length - 3]);
                    }
                }
            }

            Log.i("INFO", "getCityName::Success");
            return new Response<AddressInfo>(
                    Status.SUCCESS,
                    "getCityName success",
                    addressInfo
            );
        }catch (Exception e){
            Log.e("ERROR::getCityName", "" + e.getMessage());
            return new Response<AddressInfo>(
                    Status.INTERNAL_ERROR,
                    "" + e.getMessage(),
                    null
            );
        }
    };

    public static Response<LocationInfo> getCurrentLocation(
            LocationManager locationManager,
            Context context,
            LocationListener listener
    ) {
        Log.i("INFO", "getCurrentLocation::is called");
        try {
            boolean isGpsLocation = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkProvider = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (isGpsLocation) {
                if(!Services.checkPermission(context)){
                    Log.i("INFO", "getCurrentLocation::Permissions denied");
                    return new Response<LocationInfo>(
                            Status.UNAUTHORIZED,
                            "Permissions denied",
                            null
                    );
                }

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1000, listener);

                if(locationManager!=null && locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) != null){
                    Log.i("INFO", "getCurrentLocation::GPS_PROVIDER");
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    LocationInfo locationInfo = new LocationInfo(
                            location,
                            true,
                            false
                    );

                    Log.i("INFO", "getCurrentLocation::Get location success");
                    return new Response<LocationInfo>(
                            Status.SUCCESS,
                            "Get location success",
                            locationInfo
                    );
                }
            }

            if(isNetworkProvider){
                if(!Services.checkPermission(context)){
                    Log.i("INFO", "getCurrentLocation::Permissions denied");
                    return new Response<LocationInfo>(
                            Status.UNAUTHORIZED,
                            "Permissions denied",
                            null
                    );
                }

                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 1000, listener);

                if(locationManager!=null && locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) != null){
                    Log.i("INFO", "getCurrentLocation::NETWORK_PROVIDER");
                    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    LocationInfo locationInfo = new LocationInfo(
                            location,
                            false,
                            true);

                    Log.i("INFO", "getCurrentLocation::Get location success");
                    return new Response<LocationInfo>(
                            Status.SUCCESS,
                            "Get location success",
                            locationInfo
                    );
                }
            }

            Log.i("INFO", "getCurrentLocation::Can't get location");
            return new Response<LocationInfo>(
                    Status.BAD_REQUEST,
                    "Can't get location",
                    null
            );
            //return location;
        }catch (Exception e){
            Log.e("ERROR::getCurrentLocation", "" + e.getMessage());
            return new Response<LocationInfo>(
                    Status.INTERNAL_ERROR,
                    "" + e.getMessage(),
                    null
            );
        }
    }
}
