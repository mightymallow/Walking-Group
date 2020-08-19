package com.example.mashu.walkinggroup.controller.shared.methods.dashboard.marker.dialog.display;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.example.mashu.walkinggroup.R;

/**
 * The DashboardMarkerDisplay is used to display the
 * timestamp information from the onClick event of
 * the markers on the Dashboard Activity map.
 */

public class MarkerDisplay {

    private Activity activity;
    private String message;


    public MarkerDisplay(Activity activity, String message) {

        this.activity = activity;
        this.message = message;
    }


    public void displayDashboardMarkerDialog(){

        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setMessage(message);

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, activity.getString(R.string.help_positive_response),
            (DialogInterface dialog, int which) -> dialog.dismiss());

        alertDialog.show();
    }

}