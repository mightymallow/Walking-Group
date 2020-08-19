package com.example.mashu.walkinggroup.controller.dashboard.map.activity.placer.placing.process;

import android.app.Activity;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.dashboard.map.activity.placer.TimeCalculator;
import com.example.mashu.walkinggroup.model.user.User;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;
import java.util.List;

/**
 * The CycleUsersToMark class handles cycling through
 * all the usersToMark from the CarryOutPlacement class.
 */

public class CycleUsersToMark {

    private static final Integer MARKER_TAG = 116;

    private Activity activity;
    private GoogleMap googleMap;
    private List<User> usersToMark;


    public CycleUsersToMark(Activity activity, GoogleMap googleMap, List<User> usersToMark) {

        this.activity = activity;
        this.googleMap = googleMap;
        this.usersToMark = usersToMark;
    }

    public void cycleThroughUsersToMark() {

        for(User user: usersToMark){

            if(isUserGPSLocationNotFullyUpdated(user)){
                continue;
            }

            LatLng userPosition = getLatLngPositionForUser(user);
            String timeSinceLastUpdate = getTimeSinceLastUpdate(user);
            Marker marker;

            if(userIsAGroupLeader(user)){

                marker = googleMap.addMarker(
                        new MarkerOptions().position(userPosition)
                                .title(activity.getString(R.string.group_leader_updated, timeSinceLastUpdate))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.group_leader)));

            } else {

                marker = googleMap.addMarker(
                        new MarkerOptions().position(userPosition)
                                .title(activity.getString(R.string.group_member_updated, timeSinceLastUpdate))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.group_member)));
            }

            marker.setTag(MARKER_TAG);
            CarryOutPlacement.addToCurrentDisplayedUserMarkers(marker);
        }
    }

    private boolean isUserGPSLocationNotFullyUpdated(User user) {

        return user.getLastGpsLocation().getLat() == null || user.getLastGpsLocation().getLng() == null ||
                user.getLastGpsLocation().getTimestamp() == null || user.getLastGpsLocation().getTimestamp().isEmpty();
    }

    private boolean userIsAGroupLeader(User user) {
        return !user.getLeadsGroups().isEmpty();
    }

    private LatLng getLatLngPositionForUser(User user) {
        return new LatLng(user.getLastGpsLocation().getLat(), user.getLastGpsLocation().getLng());
    }

    private String getTimeSinceLastUpdate(User user) {

        long lastGPSLocationTime = Long.parseLong(user.getLastGpsLocation().getTimestamp());
        long currentTime = new Date().getTime();

        return buildTimeSinceLastUpdate(Math.abs(currentTime - lastGPSLocationTime));
    }

    private String buildTimeSinceLastUpdate(long timeDifference) {

        TimeCalculator timeCalculator = new TimeCalculator(timeDifference);
        return timeCalculator.getTimeDescription();
    }

}