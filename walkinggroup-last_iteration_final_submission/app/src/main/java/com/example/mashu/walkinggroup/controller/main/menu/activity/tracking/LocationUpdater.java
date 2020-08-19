package com.example.mashu.walkinggroup.controller.main.menu.activity.tracking;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.toast.ToastDisplay;
import com.example.mashu.walkinggroup.model.GpsLocation;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import java.util.Date;

import retrofit2.Call;

/**
 * The LocationUpdater class handles updating the location of
 * the current user.
 */

public class LocationUpdater {

    private static LocationManager locationManager;
    private static LocationManagerSetup locationManagerSetup;
    private static LocationListener locationListener;
    private static boolean isUploadingLocation;

    private Activity activity;


    public LocationUpdater(Activity activity) {
        this.activity = activity;
    }

    public static void setUploadingLocation(boolean isUploadingLocation){
        LocationUpdater.isUploadingLocation = isUploadingLocation;
    }

    public void updateLastKnownLocation() {

        if(locationListener == null) {
            setupLocationListener();
        }

        locationManagerSetup = new LocationManagerSetup(activity, locationManager, locationListener);
        locationManager = locationManagerSetup.setup();

        isUploadingLocation = true;
    }

    private void setupLocationListener() {

        locationListener = new LocationListener() {

            public void onLocationChanged(Location location) {
                onTimeElapsedForLocationUpdate(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };
    }

    private void onTimeElapsedForLocationUpdate(Location location) {

        double lat = location.getLatitude();
        double lng = location.getLongitude();
        String timeStamp = Long.toString(new Date().getTime());

        Call<GpsLocation> caller =
                ProxyManager.getProxy(activity)
                            .setLastGpsLocation(CurrentUserManager.getCurrentUser().getId(),
                                                new GpsLocation(lat, lng, timeStamp));

        ProxyBuilder.callProxy(activity, caller, this::onGpsLocationUpdated);
    }

    private void onGpsLocationUpdated(GpsLocation gpsLocationUpdated) {

        CurrentUserManager.getCurrentUser().setLastGpsLocation(gpsLocationUpdated);

        ToastDisplay display = new ToastDisplay(activity);
        display.displayToast(R.string.gps_location_updated, Toast.LENGTH_SHORT);

        ConfirmUserLocation confirmUserLocation = new ConfirmUserLocation(activity);
        confirmUserLocation.checkIfUserAtDestination(gpsLocationUpdated);
    }

    public static boolean stopUploadingLocation() {
        return locationManagerSetup == null ||
               locationManagerSetup.stopUpload(locationManager, isUploadingLocation);

    }

}