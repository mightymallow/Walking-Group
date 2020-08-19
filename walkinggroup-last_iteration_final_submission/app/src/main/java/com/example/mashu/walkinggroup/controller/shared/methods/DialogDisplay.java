package com.example.mashu.walkinggroup.controller.shared.methods;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.DialogInterface;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.group.manager.activities.add.AddToGroupActivity;
import com.example.mashu.walkinggroup.controller.group.manager.activities.remove.RemoveFromGroupActivity;
import com.example.mashu.walkinggroup.controller.map.activity.walking.group.methods.DisplayWalkingGroups;
import com.example.mashu.walkinggroup.controller.view.member.info.activity.ViewMemberInfoActivity;
import com.google.android.gms.maps.model.Marker;

/**
 * The DialogDisplay class displays custom dialogs.
 */
public class DialogDisplay {

    private static final String GROUP_MANAGER_ACTIVITIES_LAT_KEY = "com.example.mashu.walkinggroup.controller." +
            "group.manager.activities - Group Manager Activities" +
            "Latitude";

    private static final String GROUP_MANAGER_ACTIVITIES_LNG_KEY = "com.example.mashu.walkinggroup.controller." +
            "group.manager.activities - Group Manager Activities" +
            "Longitude";

    private static boolean accessGranted;

    private Activity activity;
    private BuildAndSetDialog buildAndSetDialog;


    public DialogDisplay(Activity activity) {

        this.activity = activity;
        this.buildAndSetDialog = new BuildAndSetDialog(activity);
    }

    public static boolean isAccessGranted(){
        return  accessGranted;
    }

    public static void setAccessGranted(boolean accessGranted) {
        DialogDisplay.accessGranted = accessGranted;
    }

    public AlertDialog buildGroupMarkerDialog(Marker marker) {

        AlertDialog alertDialog = buildAndSetDialog.buildAndSetMarkerDialog(marker);

        CheckAccessGrantedToViewGroupMembers checker = new CheckAccessGrantedToViewGroupMembers();
        checker.checkAccessGranted(DisplayWalkingGroups.getTheGroups(), marker);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, activity.getString(R.string.add_user_to_group),
            (DialogInterface dialog, int which) -> {

                Intent intent = AddToGroupActivity.makeIntent(activity.getApplicationContext());
                addCoordinatesToIntent(marker, intent);

                activity.startActivity(intent);
            });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, activity.getString(R.string.remove_user_from_group),
            (DialogInterface dialog, int which) -> {

                Intent intent = RemoveFromGroupActivity.makeIntent(activity.getApplicationContext());
                addCoordinatesToIntent(marker, intent);

                activity.startActivity(intent);
            });

        if (accessGranted) {

            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, activity.getString(R.string.view_group_user_info),
                (DialogInterface dialog, int which) -> {

                    Intent intent = ViewMemberInfoActivity.makeIntent(activity.getApplicationContext());
                    addCoordinatesToIntent(marker, intent);

                    activity.startActivity(intent);
                });

        }

        return alertDialog;
    }

    private void addCoordinatesToIntent(Marker marker, Intent intent) {

        double destinationLatitude = marker.getPosition().latitude;
        double destinationLongitude = marker.getPosition().longitude;

        intent.putExtra(GROUP_MANAGER_ACTIVITIES_LAT_KEY, destinationLatitude);
        intent.putExtra(GROUP_MANAGER_ACTIVITIES_LNG_KEY, destinationLongitude);
    }

}