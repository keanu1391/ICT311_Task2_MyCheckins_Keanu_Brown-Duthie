package com.keanu1094859.mycheckins;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CheckinMapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    private double mLatitude;
    private double mLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_checkin_maps);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.checkin_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        updateUI();
    }

    private void updateUI() {
        mLatitude = getIntent().getDoubleExtra("Latitude", 0.0);
        mLongitude = getIntent().getDoubleExtra("Longitude", 0.0);
        LatLng checkinPosition = new LatLng(mLatitude, mLongitude);

        mMap.addMarker(
                new MarkerOptions().position(checkinPosition).title("Checkin Record Location")
        );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(checkinPosition, 16));
    }
}

