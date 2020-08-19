package com.example.mashu.walkinggroup.controller.dashboard.map.activity.placer.placing.process;

import android.app.Activity;
import android.widget.Toast;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.map.activity.shared.methods.MoveCamera;
import com.example.mashu.walkinggroup.controller.toast.ToastDisplay;
import com.example.mashu.walkinggroup.model.user.User;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

/**
 * The CarryOutPlacement class handles the
 * placement of the markers on the dashboard map
 * in the DashboardMapActivity.
 */

public class CarryOutPlacement {

    private static List<Marker> currentlyDisplayedUserMarkers = new ArrayList<>();
    private static boolean allMarkersUpdated = true;

    private Activity activity;
    private GoogleMap googleMap;
    private List<User> usersToMark;


    public CarryOutPlacement(Activity activity, GoogleMap googleMap, List<User> usersToMark) {

        this.activity = activity;
        this.googleMap = googleMap;
        this.usersToMark = usersToMark;
    }

    public static boolean areAllMarkersUpdated() {
        return allMarkersUpdated;
    }

    public static void setAllMarkersUpdated(boolean allMarkersUpdated) {
        CarryOutPlacement.allMarkersUpdated = allMarkersUpdated;
    }

    public static void addToCurrentDisplayedUserMarkers(Marker marker){
        currentlyDisplayedUserMarkers.add(marker);
    }

    public void placement(){

        if(!currentlyDisplayedUserMarkers.isEmpty()) {
            clearAllPresentUserMarkers();
        }

        CycleUsersToMark cycle = new CycleUsersToMark(activity, googleMap, usersToMark);
        cycle.cycleThroughUsersToMark();

        allMarkersUpdated = true;
        MoveCamera.moveCameraToIncludeAllMarkers(googleMap, currentlyDisplayedUserMarkers);

        ToastDisplay display = new ToastDisplay(activity);
        display.displayToast(R.string.markers_updated, Toast.LENGTH_SHORT);
    }

    private void clearAllPresentUserMarkers() {

        for(Marker marker: currentlyDisplayedUserMarkers){
            marker.remove();
        }

        currentlyDisplayedUserMarkers.clear();
    }

}