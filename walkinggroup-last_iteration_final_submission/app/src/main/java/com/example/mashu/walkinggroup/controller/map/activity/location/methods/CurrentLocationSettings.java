package com.example.mashu.walkinggroup.controller.map.activity.location.methods;

import android.app.Activity;
import android.util.Log;

import com.example.mashu.walkinggroup.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * The CurrentLocationSettings class handles the map
 * settings to allow the current location of
 * the device to be tracked.
 */

public class CurrentLocationSettings {

    private static final String MY_LOCATION_MARKER = "com.example.mashu.walkinggroup.controller." +
                                                     "map.activity.location.methods - My Location Marker";
    private Activity activity;
    private GoogleMap googleMap;


    public CurrentLocationSettings(Activity activity, GoogleMap googleMap) {

        this.activity = activity;
        this.googleMap = googleMap;
    }


    public void updateLocationUI() {

        if (googleMap == null) {
            return;
        }

        updateMapSettings();
    }

    private void updateMapSettings() {

        try {

            if (LocationPermission.isLocationPermissionGranted()){

                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);

                googleMap.setOnMyLocationButtonClickListener(() -> {

                    CurrentDeviceLocation currentDeviceLocation = new CurrentDeviceLocation(activity, googleMap);
                    currentDeviceLocation.getLocation();

                    return true;
                });

            } else {

                googleMap.setMyLocationEnabled(false);
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);

                LocationPermission.isLocationPermissionGranted(activity);

            }

        } catch (SecurityException e)  {

            Log.e(activity.getString(R.string.exception), e.getMessage());

        }
    }

}