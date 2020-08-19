package com.example.mashu.walkinggroup.controller.map.activity.shared.methods;

import android.app.Activity;

import com.example.mashu.walkinggroup.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * The UpdateOriginAndDestination class handles updating
 * the origin and originMarker of the map as well as
 * updating the destination and destinationMarker of the map.
 */

public class UpdateOriginAndDestination {

    private static LatLng origin;
    private static Marker originMarker;
    private static LatLng destination;
    private static Marker destinationMarker;


    public static LatLng getOrigin() {
        return origin;
    }

    public static Marker getOriginMarker() {
        return originMarker;
    }

    public static LatLng getDestination() {
        return destination;
    }

    public static Marker getDestinationMarker() {
        return destinationMarker;
    }

    public static void updateOrigin(Activity activity, LatLng newOrigin, GoogleMap googleMap) {

        if (originMarker != null) {
            originMarker.remove();
        }

        origin = newOrigin;

        originMarker = googleMap.addMarker(new MarkerOptions()
                                .position(origin)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin))
                                .title(activity.getString(R.string.meeting_point_title)));
    }

    public static void clearOriginMarker(){
        originMarker.remove();
    }

    public static void updateDestination(Activity activity, LatLng newDestination, GoogleMap googleMap) {

        if (destinationMarker != null) {
            destinationMarker.remove();
        }

        destination = newDestination;

        destinationMarker = googleMap.addMarker(new MarkerOptions()
                                     .position(destination)
                                     .icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_pin))
                                     .title(activity.getString(R.string.destination_title)));
    }

    public static void clearDestinationMarker(){
        destinationMarker.remove();
    }

}