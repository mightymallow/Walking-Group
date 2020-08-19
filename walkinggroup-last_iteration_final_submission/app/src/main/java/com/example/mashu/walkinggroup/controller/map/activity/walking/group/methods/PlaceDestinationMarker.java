package com.example.mashu.walkinggroup.controller.map.activity.walking.group.methods;

import android.app.Activity;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.map.activity.shared.methods.UpdateOriginAndDestination;
import com.example.mashu.walkinggroup.controller.shared.methods.DialogDisplay;
import com.example.mashu.walkinggroup.model.Group;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * The PlaceDestinationMarker class handles placing
 * a clickable marker at the destination when
 * displaying walking groups.
 */

public class PlaceDestinationMarker {

    private final static int DESTINATION_VALUE_POS = 1;

    private Activity activity;
    private GoogleMap googleMap;
    private Group groupOfInterest;


    public PlaceDestinationMarker(Activity activity, GoogleMap googleMap, Group groupOfInterest) {

        this.activity = activity;
        this.googleMap = googleMap;
        this.groupOfInterest = groupOfInterest;
    }

    public void placeClickableMarkerAtGroupDestination() {

        double destinationLatitude = groupOfInterest.getRouteLatArray()[DESTINATION_VALUE_POS];
        double destinationLongitude = groupOfInterest.getRouteLngArray()[DESTINATION_VALUE_POS];

        LatLng destinationPosition = new LatLng(destinationLatitude, destinationLongitude);

        Marker currentMarker = googleMap.addMarker(new MarkerOptions()
                .position(destinationPosition)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .title(activity.getString(R.string.group_title, groupOfInterest.getGroupDescription())));

        googleMap.setOnMarkerClickListener((Marker marker) -> {

            if(!marker.equals(UpdateOriginAndDestination.getDestinationMarker()) &&
               !marker.equals(UpdateOriginAndDestination.getOriginMarker())){

                DialogDisplay display = new DialogDisplay(activity);
                display.buildGroupMarkerDialog(marker).show();

            } else {

                return false;

            }

            return true;
        });

        DisplayWalkingGroups.addToWalkingGroupMarkers(currentMarker);
    }

}