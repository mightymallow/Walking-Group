package com.example.mashu.walkinggroup.controller.main.menu.activity.tracking.click.listeners;

import android.app.Activity;
import android.widget.Toast;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.main.menu.activity.tracking.LocationUpdater;
import com.example.mashu.walkinggroup.controller.toast.ToastDisplay;

/**
 * The StopGpsUpload class handles the functionality of the stopGPSImageView
 * in the Main Menu Activity.
 */

public class StopGpsUpload {

    private Activity activity;


    public StopGpsUpload(Activity activity) {
        this.activity = activity;
    }

    public void onStopGpsUpload(){

        if(LocationUpdater.stopUploadingLocation()) {

            ToastDisplay display = new ToastDisplay(activity);
            display.displayToast(R.string.stopping_gps_upload_now, Toast.LENGTH_LONG);

        } else {

            ToastDisplay display = new ToastDisplay(activity);
            display.displayToast(R.string.gps_upload_not_started, Toast.LENGTH_SHORT);

        }
    }

}