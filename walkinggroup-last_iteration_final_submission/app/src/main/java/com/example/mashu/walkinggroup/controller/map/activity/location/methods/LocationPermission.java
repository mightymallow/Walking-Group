package com.example.mashu.walkinggroup.controller.map.activity.location.methods;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * The LocationPermission class determines if location permission
 * has previously been granted.
 */

public class LocationPermission {

    private final static int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private static boolean locationPermissionGranted = false;


    public static boolean isLocationPermissionGranted(){
        return locationPermissionGranted;
    }

    public static void setLocationPermissionGranted(boolean locationPermissionGranted) {
        LocationPermission.locationPermissionGranted = locationPermissionGranted;
    }

    public static void isLocationPermissionGranted(Activity activity) {

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            locationPermissionGranted = true;

        } else {

            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                              PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            locationPermissionGranted = false;

        }
    }

}