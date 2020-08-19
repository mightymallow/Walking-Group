package com.example.mashu.walkinggroup.controller.map.activity.location.methods.location.search.bar;

import android.app.Activity;

import com.example.mashu.walkinggroup.controller.map.activity.invisible.icon.setup.ChangeViewVisibility;
import com.example.mashu.walkinggroup.controller.map.activity.shared.methods.MoveCamera;
import com.example.mashu.walkinggroup.controller.map.activity.shared.methods.UpdateOriginAndDestination;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * The LocationSearchBar class handles updating destination and origin
 * based on user inputs in the displayed edit texts.
 */

public class LocationSearchBar {

    private static boolean isOriginUpdated = false;
    private static boolean isDestinationUpdated = false;

    private Activity activity;
    private GoogleMap googleMap;


    public LocationSearchBar(Activity activity, GoogleMap googleMap) {

        this.activity = activity;
        this.googleMap = googleMap;
    }

    public void updateOrigin(LatLng newOrigin) {

        if (newOrigin != null) {

            UpdateOriginAndDestination.updateOrigin(activity, newOrigin, googleMap);
            MoveCamera.move(UpdateOriginAndDestination.getOrigin(), googleMap);
        }

        MoveCamera.moveCameraToIncludeWalkingGroupPath(googleMap);

        isOriginUpdated = true;
    }

    public void updateDestination(LatLng newDestination) {

        if (newDestination != null) {

            UpdateOriginAndDestination.updateDestination(activity, newDestination, googleMap);
            MoveCamera.move(UpdateOriginAndDestination.getDestination(), googleMap);
        }

        MoveCamera.moveCameraToIncludeWalkingGroupPath(googleMap);

        isDestinationUpdated = true;
    }

    public void onOriginDestinationUpdated(){

        if(isOriginUpdated && isDestinationUpdated){

            ChangeViewVisibility change = new ChangeViewVisibility(activity);
            change.setupTransitionsForViewWalkingGroupScreen();

            isOriginUpdated = false;
            isDestinationUpdated = false;
        }
    }

}