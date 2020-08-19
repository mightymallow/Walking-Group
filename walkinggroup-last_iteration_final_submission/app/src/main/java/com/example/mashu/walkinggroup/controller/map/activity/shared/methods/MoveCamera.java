package com.example.mashu.walkinggroup.controller.map.activity.shared.methods;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

/**
 * The MoveCamera class handles moving the camera to
 * a new location on the map.
 */

public class MoveCamera {

    private static final float DEFAULT_ZOOM = 15f;
    private static final int OFFSET_FROM_MAP_EDGES = 250;


    public static void move(LatLng latLng, GoogleMap googleMap) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
    }

    public static void moveCameraToIncludeWalkingGroupPath(GoogleMap googleMap){

        Marker originMarker = UpdateOriginAndDestination.getOriginMarker();
        Marker destinationMarker = UpdateOriginAndDestination.getDestinationMarker();

        if(originMarker != null && destinationMarker != null) {

            LatLngBounds latLngBounds = getLatLngBoundsForMapWithWalkingPath(originMarker);
            zoomCameraToRequiredLevel(googleMap, latLngBounds);
        }
    }

    private static LatLngBounds getLatLngBoundsForMapWithWalkingPath(Marker originMarker) {

        LatLngBounds.Builder latLngBoundsBuilder = new LatLngBounds.Builder();
        latLngBoundsBuilder.include(originMarker.getPosition());
        latLngBoundsBuilder.include(UpdateOriginAndDestination.getDestinationMarker().getPosition());

        return latLngBoundsBuilder.build();
    }

    private static void zoomCameraToRequiredLevel(GoogleMap googleMap, LatLngBounds latLngBounds) {

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(latLngBounds, OFFSET_FROM_MAP_EDGES);
        googleMap.animateCamera(cameraUpdate);
    }

    public static void moveCameraToIncludeAllMarkers(GoogleMap googleMap, List<Marker> markers){

        if(!markers.isEmpty()) {
            LatLngBounds latLngBounds = getLatLngBoundsForMapWithMarkers(markers);
            zoomCameraToRequiredLevel(googleMap, latLngBounds);
        }
    }

    private static LatLngBounds getLatLngBoundsForMapWithMarkers(List<Marker> markers) {

        LatLngBounds.Builder latLngBoundsBuilder = new LatLngBounds.Builder();

        for(Marker marker: markers) {
            latLngBoundsBuilder.include(marker.getPosition());
        }

        return latLngBoundsBuilder.build();
    }

}