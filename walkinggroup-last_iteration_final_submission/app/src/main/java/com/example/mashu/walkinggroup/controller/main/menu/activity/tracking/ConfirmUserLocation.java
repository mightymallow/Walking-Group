package com.example.mashu.walkinggroup.controller.main.menu.activity.tracking;

import android.app.Activity;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.shared.methods.PointsManager;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.model.GpsLocation;
import com.example.mashu.walkinggroup.model.Group;
import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Timer;

/**
 * The ConfirmUserLocation class is used to confirm
 * where the current user is at a given point in time
 */

public class ConfirmUserLocation {

    private static final int ORIGIN_COORDINATE = 0;
    private static final int DESTINATION_COORDINATE = 1;

    private static LatLng currentUserStartingLocationOnStartGPS;
    private static boolean atDestination;
    private static long timePassed = 0;
    private static Timer timer;

    private Activity activity;
    private String currentLatitude;
    private String currentLongitude;
    private List<Group> groupsToSearchThrough;


    public ConfirmUserLocation(Activity activity) {
        this.activity = activity;
    }

    public static boolean isAtDestination() {
        return atDestination;
    }

    public static void cancelAndResetTimer(){

        if(timer != null){

            timer.cancel();
            timer = null;
        }

        timePassed = 0;
    }

    public void checkIfUserAtDestination(GpsLocation currentUserLocation) {

        List<Group> currentUserGroupsMember = CurrentUserManager.getCurrentUser().getMemberOfGroups();
        List<Group> currentUserGroupsLed = CurrentUserManager.getCurrentUser().getLeadsGroups();

        FilterGroups filterGroups = new FilterGroups(currentUserGroupsMember, currentUserGroupsLed);
        groupsToSearchThrough = filterGroups.getGroupsToSearchThrough();

        determineIfCurrentUserAtDestination(currentUserLocation);
    }

    private void determineIfCurrentUserAtDestination(GpsLocation currentUserGPSLocation) {

        DecimalFormat twoDecimalPlaces = new DecimalFormat(activity.getString(R.string.two_decimal_places_format));

        currentLatitude = twoDecimalPlaces.format(currentUserGPSLocation.getLat());
        currentLongitude = twoDecimalPlaces.format(currentUserGPSLocation.getLng());

        String currentUserStartingLocationLatitude = twoDecimalPlaces.format(currentUserStartingLocationOnStartGPS.latitude);
        String currentUserStartingLocationLongitude = twoDecimalPlaces.format(currentUserStartingLocationOnStartGPS.longitude);

        for(Group group: groupsToSearchThrough){

            String destinationLatitude = twoDecimalPlaces.format(group.getRouteLatArray()[DESTINATION_COORDINATE]);
            String destinationLongitude = twoDecimalPlaces.format(group.getRouteLngArray()[DESTINATION_COORDINATE]);

            String originLatitude = twoDecimalPlaces.format(group.getRouteLatArray()[ORIGIN_COORDINATE]);
            String originLongitude = twoDecimalPlaces.format(group.getRouteLngArray()[ORIGIN_COORDINATE]);

            if(isCurrentUserAtGroupDestination(destinationLatitude, destinationLongitude)){

                atDestination = true;

                if (isCurrentUserStartingPointSameAsGroupMeetingPoint(currentUserStartingLocationLatitude, currentUserStartingLocationLongitude,
                                                                      originLatitude, originLongitude)) {

                    PointsManager pointsManager = new PointsManager(activity, group);
                    pointsManager.addToCurrentUserEarnedPointsAndUpdateServer();
                }

                if(timer == null) {

                    TimerSetup timerSetup = new TimerSetup(activity, timePassed, group);
                    timer = timerSetup.setTimerAfterWhichToStopLocationUpdates();
                }

                break;

            } else {

                atDestination = false;

            }
        }

        if(!atDestination){
            cancelAndResetTimer();
        }
    }

    private boolean isCurrentUserAtGroupDestination(String destinationLatitude, String destinationLongitude) {
        return currentLatitude.equals(destinationLatitude) && currentLongitude.equals(destinationLongitude);
    }

    private boolean isCurrentUserStartingPointSameAsGroupMeetingPoint(String currentUserStartingLocationLatitude,
                                                                      String currentUserStartingLocationLongitude,
                                                                      String groupOriginLatitude, String groupOriginLongitude) {

        return groupOriginLatitude.equals(currentUserStartingLocationLatitude) &&
               groupOriginLongitude.equals(currentUserStartingLocationLongitude);
    }

    public static void setUserStartingPointOnStartGPS(LatLng originOnStartGPS) {
        ConfirmUserLocation.currentUserStartingLocationOnStartGPS = originOnStartGPS;
    }

}