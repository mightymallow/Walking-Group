package com.example.mashu.walkinggroup.controller.map.activity.walking.group.methods;

import android.app.Activity;

import com.example.mashu.walkinggroup.controller.map.activity.shared.methods.MoveCamera;
import com.example.mashu.walkinggroup.model.Group;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * The DisplayWalkingGroups class deals with displaying
 * the walking groups on the map.
 */
public class DisplayWalkingGroups {

    private static List<Marker> walkingGroupMarkers = new ArrayList<>();
    private static boolean isZoomApplicableToFitAllWalkingGroups = false;
    private static List<Group> theGroups;

    private GoogleMap googleMap;
    private Activity activity;


    public DisplayWalkingGroups(GoogleMap googleMap, Activity activity) {

        this.googleMap = googleMap;
        this.activity = activity;
    }

    public static void addToWalkingGroupMarkers(Marker marker) {
        walkingGroupMarkers.add(marker);
    }

    public static void setIsZoomApplicableToFitAllWalkingGroups(boolean isZoomApplicableToFitAllWalkingGroups) {
        DisplayWalkingGroups.isZoomApplicableToFitAllWalkingGroups = isZoomApplicableToFitAllWalkingGroups;
    }

    public void getWalkingGroupsToShow(){

        Call<List<Group>> caller = ProxyManager.getProxy(activity).getGroups();
        ProxyBuilder.callProxy(activity, caller, this::onGroupsReceived);
    }

    private void onGroupsReceived(List<Group> groupsReceived) {

        theGroups = groupsReceived;

        for(Group group: groupsReceived){

            PlaceDestinationMarker placeDestinationMarker = new PlaceDestinationMarker(activity, googleMap, group);
            placeDestinationMarker.placeClickableMarkerAtGroupDestination();
        }

        if(isZoomApplicableToFitAllWalkingGroups) {
            MoveCamera.moveCameraToIncludeAllMarkers(googleMap, walkingGroupMarkers);
        } else {
            isZoomApplicableToFitAllWalkingGroups = true;
        }
    }

    public static List<Group> getTheGroups(){
        return theGroups;
    }

    public static void setTheGroups(List<Group> theGroupToSet){
        theGroups = theGroupToSet;
    }

}