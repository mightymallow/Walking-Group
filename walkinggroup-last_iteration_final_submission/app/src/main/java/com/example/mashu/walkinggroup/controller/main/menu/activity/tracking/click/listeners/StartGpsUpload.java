package com.example.mashu.walkinggroup.controller.main.menu.activity.tracking.click.listeners;

import android.app.Activity;
import android.widget.Toast;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.main.menu.activity.tracking.GPSTracking;
import com.example.mashu.walkinggroup.controller.toast.ToastDisplay;

/**
 * The StartGpsUpload class handles the functionality of the startGPSImageView
 * in the Main Menu Activity.
 */

public class StartGpsUpload {

    private static boolean isClicked;

    private Activity activity;


    public StartGpsUpload(Activity activity) {
        this.activity = activity;
    }

    public static void setIsClicked(boolean isClicked) {
        StartGpsUpload.isClicked = isClicked;
    }

    public void onStartGpsUpload(){

        ToastDisplay display = new ToastDisplay(activity);
        display.displayToast(R.string.starting_gps_upload, Toast.LENGTH_SHORT);

        GPSTracking gpsTracker = new GPSTracking(activity);

        if(!isClicked) {
            gpsTracker.checkForCurrentUserInvolvementWithGroup();
            display.displayToast(R.string.to_earn_points_do_this_stuff_over_here, Toast.LENGTH_LONG);
        } else {
            display.displayToast(R.string.already_uploading_gps, Toast.LENGTH_LONG);
        }
    }

}