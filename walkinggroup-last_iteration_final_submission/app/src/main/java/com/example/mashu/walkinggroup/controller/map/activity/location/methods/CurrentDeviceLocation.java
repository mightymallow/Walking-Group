package com.example.mashu.walkinggroup.controller.map.activity.location.methods;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.util.Log;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.map.activity.shared.methods.MoveCamera;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

/**
 * The CurrentDeviceLocation class handles obtaining the
 * current location of the device, which
 * may be null in rare cases.
 */

public class CurrentDeviceLocation {

    private static final int DIMENSION_SIZE = 200;

    private static Marker myCurrentLocationMarker;

    private Activity activity;
    private GoogleMap googleMap;


    public CurrentDeviceLocation(Activity activity, GoogleMap googleMap) {

        this.activity = activity;
        this.googleMap = googleMap;
    }

    public void getLocation() {

        try {
            if (LocationPermission.isLocationPermissionGranted()) {

                Task<Location> locationResult = LocationServices.getFusedLocationProviderClient(activity)
                                                                .getLastLocation();

                locationResult.addOnSuccessListener(activity, this::setCameraPositionToCurrentDeviceLocation);
            }
        } catch(SecurityException securityException)  {

            Log.e(activity.getString(R.string.exception), securityException.getMessage());

        }
    }

    private void setCameraPositionToCurrentDeviceLocation(Location lastLocation) {

        if(lastLocation != null) {

            addCustomMarkerBasedOnCurrentUserRewards(lastLocation);

            MoveCamera.move(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()),
                            googleMap);
        }
    }

    private void addCustomMarkerBasedOnCurrentUserRewards(Location currentDeviceLocation) {

        if(CurrentUserManager.getCurrentUser().getRewards() != null &&
           CurrentUserManager.getCurrentUser().getRewards().getCurrentAvatar() != null){

            LatLng currentPosition = new LatLng(currentDeviceLocation.getLatitude(), currentDeviceLocation.getLongitude());
            int iconResourceID = CurrentUserManager.getCurrentUser().getRewards().getCurrentAvatar().getIconID();

            if(myCurrentLocationMarker != null){
                myCurrentLocationMarker.remove();
            }

            myCurrentLocationMarker = googleMap.addMarker(
                    new MarkerOptions().position(currentPosition)
                                       .icon(BitmapDescriptorFactory.fromBitmap(createScaledIcon(iconResourceID))));
        }

        CurrentLocationSettings locationSettings = new CurrentLocationSettings(activity, googleMap);
        locationSettings.updateLocationUI();
    }

    private Bitmap createScaledIcon(int iconResourceID) {

        BitmapDrawable bitmapDrawable = (BitmapDrawable) activity.getResources().getDrawable(iconResourceID);
        Bitmap bitmap = bitmapDrawable.getBitmap();

        return Bitmap.createScaledBitmap(bitmap, DIMENSION_SIZE, DIMENSION_SIZE, false);
    }

}