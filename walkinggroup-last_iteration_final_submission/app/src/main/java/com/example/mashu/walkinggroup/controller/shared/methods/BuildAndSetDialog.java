package com.example.mashu.walkinggroup.controller.shared.methods;

import android.app.Activity;
import android.app.AlertDialog;

import com.example.mashu.walkinggroup.R;
import com.google.android.gms.maps.model.Marker;

/**
 * The BuildAndSetDialog class is used to both build
 * and return a dialog to the client.
 */

public class BuildAndSetDialog {

    private Activity activity;


    public BuildAndSetDialog(Activity activity) {
        this.activity = activity;
    }

    public AlertDialog buildAndSetAlertDialogMessage(int questionResourceID) {

        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setMessage(activity.getString(questionResourceID));
        return alertDialog;
    }

    public AlertDialog buildAndSetMarkerDialog(Marker marker) {

        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setMessage(activity.getString(R.string.user_question, marker.getTitle()));
        return alertDialog;
    }

}