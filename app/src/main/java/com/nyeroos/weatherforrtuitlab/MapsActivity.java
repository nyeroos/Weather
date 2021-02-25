package com.nyeroos.weatherforrtuitlab;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nyeroos.weatherforrtuitlab.data.Data;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private API mApi = API.Instance.getApi();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button cityButton = findViewById(R.id.city_button);
        cityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receiveWeather(((Button)v).getText().toString());
            }
        });
    }

    private void receiveWeather(final String city) {
        mApi.getDataByCity(city, "ba3bef5e5bccf745a023bc18db977237", "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Data>() {
                    @Override
                    public void accept(Data data) throws Exception {
                        //Toast.makeText(MapsActivity.this, data.getName()+ " " +data.getMain().getTemp(), Toast.LENGTH_LONG).show();
                        showWeatherMarker(data);
                    }
                });
    }

    private void showWeatherMarker(Data data) {
        LatLng latLng = new LatLng(data.getCoord().getLat(), data.getCoord().getLon());
        mMap.addMarker(new MarkerOptions().position(latLng).title(data.getName() + ": " + data.getMain().getTemp())).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);



                /*.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Data>() {
                    @Override
                    public void accept(final Data data) throws Exception {
                        Toast.makeText(MapsActivity.this, data.getName()+ " " + data.getMain().getTemp(), Toast.LENGTH_LONG).show();
                    }
                });*/
    }
}
