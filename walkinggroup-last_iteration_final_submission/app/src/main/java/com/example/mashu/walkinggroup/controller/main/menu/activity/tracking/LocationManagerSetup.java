package com.example.mashu.walkinggroup.controller.main.menu.activity.tracking;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import com.example.mashu.walkinggroup.controller.main.menu.activity.tracking.click.listeners.StartGpsUpload;

/**
 * The LocationManagerSetup class handles setting up
 * the LocationManager for the LocationUpdater class.
 */

public class LocationManagerSetup {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final long MIN_TIME = 30000;
    private static final float MIN_DISTANCE = 0;

    private Activity activity;
    private LocationManager locationManager;
    private LocationListener locationListener;


    public LocationManagerSetup(Activity activity, LocationManager locationManager, LocationListener locationListener) {

        this.activity = activity;
        this.locationManager = locationManager;
        this.locationListener = locationListener;
    }

    public LocationManager setup() {

        if (locationManager == null) {
            locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        }

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, locationListener);

        return locationManager;
    }

    public boolean stopUpload(LocationManager locationManager, boolean isUploadingLocation){

        if(locationManager != null && isUploadingLocation) {

            locationManager.removeUpdates(locationListener);
            ConfirmUserLocation.cancelAndResetTimer();
            LocationUpdater.setUploadingLocation(false);
            StartGpsUpload.setIsClicked(false);

            return true;
        }

        return false;

    }

}