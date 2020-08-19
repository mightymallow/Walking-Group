package com.example.mashu.walkinggroup.controller.dashboard.map.activity.placer.placing.process;

import android.app.Activity;

import com.example.mashu.walkinggroup.model.user.User;
import com.google.android.gms.maps.GoogleMap;

import java.util.List;

/**
 * The PlaceMarkers class handles placing markers
 * on the screen.
 */

public class PlaceMarkers {

    private Activity activity;
    private GoogleMap googleMap;
    private List<User> usersToMark;


    public PlaceMarkers(Activity activity, GoogleMap googleMap, List<User> usersToMark) {

        this.activity = activity;
        this.googleMap = googleMap;
        this.usersToMark = usersToMark;
    }

    public void placeMarkersOnMap(){

        CarryOutPlacement carryOutPlacement = new CarryOutPlacement(activity, googleMap, usersToMark);
        carryOutPlacement.placement();
    }

}