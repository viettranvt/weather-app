package com.example.weather;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather.api.Api;
import com.example.weather.constant.Config;
import com.example.weather.model.LocationInfo;
import com.example.weather.model.Response;
import com.example.weather.service.ServiceLocation;
import com.example.weather.service.Services;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView txtProvince;
    private TextView txtTemp;
    private TextView txtUpdatedAt;
    private TextView txtSunrise;
    private TextView txtSunset;
    private TextView txtWindSpeed;
    private TextView txtHumidity;
    private TextView txtCloudDensity;
    private TextView txtUvIndex;
    private TextView txtWeatherInfo;
    private TextView txtMaxTemp;
    private TextView txtMinTemp;
    private ImageView imgViewIconWeather;
    private ListView lvDailyWeather;
    private RecyclerView rcvHoursWeather;
    private SwipeRefreshLayout refreshLayout;
    private EditText edtLocation;
    private View view;
    private Context context = this;
    private Boolean isPlacesLocation = false;

    LocationManager locationManager;
    String[] permissions_all={Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            init();

            if(Services.checkPermission(MainActivity.this)){
                Response<LocationInfo> response = ServiceLocation.getCurrentLocation(
                        locationManager,
                        MainActivity.this,
                        locationListener
                );
                LocationInfo locationInfo = response.getData();

                if(locationInfo != null){
                    location = locationInfo.getLocation();
                }

                Api.getWeatherInfo(
                        locationInfo,
                        context,
                        txtProvince,
                        txtTemp,
                        txtUpdatedAt,
                        txtSunrise,
                        txtSunset,
                        txtWindSpeed,
                        txtHumidity,
                        txtCloudDensity,
                        txtUvIndex,
                        txtWeatherInfo,
                        imgViewIconWeather,
                        lvDailyWeather,
                        rcvHoursWeather,
                        refreshLayout,
                        txtMaxTemp,
                        txtMinTemp,
                        view
                );
            }else{
                ActivityCompat.requestPermissions(MainActivity.this, permissions_all, Config.PERMISSION_CODE);
            }

            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if(Services.checkPermission(MainActivity.this)){
                        edtLocation.getText().clear();
                        isPlacesLocation = false;

                        if(location == null){
                            Response<LocationInfo> response = ServiceLocation.getCurrentLocation(
                                    locationManager,
                                    MainActivity.this,
                                    locationListener
                            );
                            LocationInfo locationInfo = response.getData();

                            if(locationInfo != null){
                                location = locationInfo.getLocation();
                            }
                        }

                        LocationInfo locationInfo = new LocationInfo(
                                location,
                                false,
                                false
                        );

                        Api.getWeatherInfo(
                                locationInfo,
                                context,
                                txtProvince,
                                txtTemp,
                                txtUpdatedAt,
                                txtSunrise,
                                txtSunset,
                                txtWindSpeed,
                                txtHumidity,
                                txtCloudDensity,
                                txtUvIndex,
                                txtWeatherInfo,
                                imgViewIconWeather,
                                lvDailyWeather,
                                rcvHoursWeather,
                                refreshLayout,
                                txtMaxTemp,
                                txtMinTemp,
                                view
                        );
                    }else{
                        ActivityCompat.requestPermissions(MainActivity.this, permissions_all, Config.PERMISSION_CODE);
                    }
                }
            });
        } catch (Exception e) {
            Log.e("ERROR::onCreate", e.toString());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i("INFO::onRequestPermissionsResult", "id called");
        try{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            if(requestCode == Config.PERMISSION_CODE){
                if(Services.checkPermission(MainActivity.this)){
                    Response<LocationInfo> response = ServiceLocation.getCurrentLocation(locationManager, getApplication(), locationListener);
                    LocationInfo locationInfo = response.getData();

                    if(locationInfo != null){
                        location = locationInfo.getLocation();
                    }

                    Api.getWeatherInfo(
                            locationInfo,
                            context,
                            txtProvince,
                            txtTemp,
                            txtUpdatedAt,
                            txtSunrise,
                            txtSunset,
                            txtWindSpeed,
                            txtHumidity,
                            txtCloudDensity,
                            txtUvIndex,
                            txtWeatherInfo,
                            imgViewIconWeather,
                            lvDailyWeather,
                            rcvHoursWeather,
                            refreshLayout,
                            txtMaxTemp,
                            txtMinTemp,
                            view
                    );
                }
            }else{
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Log.e("ERROR::onRequestPermissionsResult", "" + e.getMessage());
        }
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location currentLocation) {
            Log.i("INFO::onLocationChanged", "id called");
            try{
                if(isPlacesLocation) {
                    Log.i("INFO::onLocationChanged", "isPlacesLocation");
                    return;
                }

                if(location == null || (location.getLongitude() != currentLocation.getLongitude() && location.getLatitude() != currentLocation.getLatitude())){
                    edtLocation.getText().clear();
                    location = currentLocation;
                    LocationInfo locationInfo = new LocationInfo(location, false, false);

                    Api.getWeatherInfo(
                            locationInfo,
                            context,
                            txtProvince,
                            txtTemp,
                            txtUpdatedAt,
                            txtSunrise,
                            txtSunset,
                            txtWindSpeed,
                            txtHumidity,
                            txtCloudDensity,
                            txtUvIndex,
                            txtWeatherInfo,
                            imgViewIconWeather,
                            lvDailyWeather,
                            rcvHoursWeather,
                            refreshLayout,
                            txtMaxTemp,
                            txtMinTemp,
                            view
                    );
                }
            }catch (Exception e){
                Log.e("ERROR::onLocationChanged", "" + e.getMessage());
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

        @Override
        public void onProviderDisabled(@NonNull String provider) {}

        @Override
        public void onProviderEnabled(@NonNull String provider) {}
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try{
            super.onActivityResult(requestCode, resultCode, data);

            if(requestCode == Config.PLACE_CODE && resultCode == RESULT_OK){
                Log.i("INFO::onActivityResult", "location of google places");
                Place place = Autocomplete.getPlaceFromIntent(data);
                edtLocation.setText(place.getAddress());
                location.setLatitude(place.getLatLng().latitude);
                location.setLongitude(place.getLatLng().longitude);
                isPlacesLocation = true;

                LocationInfo locationInfo = new LocationInfo(location, false, false);

                Api.getWeatherInfo(
                        locationInfo,
                        context,
                        txtProvince,
                        txtTemp,
                        txtUpdatedAt,
                        txtSunrise,
                        txtSunset,
                        txtWindSpeed,
                        txtHumidity,
                        txtCloudDensity,
                        txtUvIndex,
                        txtWeatherInfo,
                        imgViewIconWeather,
                        lvDailyWeather,
                        rcvHoursWeather,
                        refreshLayout,
                        txtMaxTemp,
                        txtMinTemp,
                        view
                );

                return ;
            }

            if(requestCode == AutocompleteActivity.RESULT_ERROR){
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.e("ERROR::onActivityResult", "" + status.getStatusMessage());
            }
            /*else{
                if(requestCode == AutocompleteActivity.RESULT_ERROR){
                    Status status = Autocomplete.getStatusFromIntent(data);
                    Log.e("ERROR::onActivityResult", "" + status.getStatusMessage());
                }

                Log.i("INFO::onActivityResult", "location of device");
                isPlacesLocation = false;
                edtLocation.getText().clear();
                Response<LocationInfo> response = ServiceLocation.getCurrentLocation(
                        locationManager,
                        MainActivity.this,
                        locationListener
                );
                LocationInfo info = response.getData();

                if(info != null){
                    location = info.getLocation();
                }
            }

            LocationInfo locationInfo = new LocationInfo(location, false, false);*/
        }catch (Exception e){
            Log.e("ERROR::onActivityResult", "" + e.getMessage());
        }
    }

    private void init() {
        txtProvince = (TextView) findViewById(R.id.txtProvince);
        txtTemp = (TextView)findViewById(R.id.txtTemp);
        txtUpdatedAt = (TextView)findViewById(R.id.txtUpdatedAt);
        txtSunrise = (TextView)findViewById(R.id.txtSunrise);
        txtSunset = (TextView)findViewById(R.id.txtSunset);
        txtHumidity = (TextView)findViewById(R.id.txtHumidity);
        txtCloudDensity = (TextView)findViewById(R.id.txtCloudDensity);
        txtWindSpeed = (TextView)findViewById(R.id.txtWindSpeed);
        txtUvIndex = (TextView)findViewById(R.id.txtUvIndex);
        txtWeatherInfo = (TextView)findViewById(R.id.txtWeatherInfo);
        txtMaxTemp = (TextView)findViewById(R.id.txtMaxTemp);
        txtMinTemp = (TextView)findViewById(R.id.txtMinTemp);
        imgViewIconWeather = (ImageView) findViewById(R.id.imgViewIconWeather);
        lvDailyWeather = (ListView)findViewById(R.id.lvDailyWeather);
        rcvHoursWeather = (RecyclerView)findViewById(R.id.rcvHoursWeather);
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        edtLocation = (EditText)findViewById(R.id.edtLocation);
        view = findViewById(R.id.mainLayout);
        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);

        //init places
        Places.initialize(getApplicationContext(), Config.API_KEY);

        edtLocation.setFocusable(false);
        edtLocation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try{
                    List<Place.Field> fieldList = Arrays.asList(
                            Place.Field.ADDRESS,
                            Place.Field.LAT_LNG,
                            Place.Field.NAME
                    );

                    Intent intent = new Autocomplete.IntentBuilder(
                            AutocompleteActivityMode.OVERLAY,
                            fieldList
                    ).build(MainActivity.this);

                    startActivityForResult(intent, Config.PLACE_CODE);
                }catch (Exception e){
                    Log.e("ERROR", "edit::setOnClick" + e.getMessage());
                }
            }
        });
    }
}