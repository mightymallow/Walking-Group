package com.example.mashu.walkinggroup.controller.account.monitoring.activities.monitors.activity;

import android.app.Activity;

import com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.MonitorEnum;
import com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.SharedActivityFunctions;
import com.example.mashu.walkinggroup.controller.toast.ToastDisplay;

/**
 * The PostActivityResult class handles the
 * functionality of the application postUserAdded
 * or postUserEdited in the MonitorsActivity.
 */

public class PostActivityResult {

    private Activity activity;
    private String name;


    public PostActivityResult(Activity activity, String name) {
        this.activity = activity;
        this.name = name;
    }

    public void postUserAdded() {

        ToastDisplay display = new ToastDisplay(activity);
        display.displayUserAddedToast(name);

        SharedActivityFunctions sharedFunctions = new SharedActivityFunctions(activity);
        sharedFunctions.populateListView(MonitorEnum.MONITORS_ACTIVITY);
    }

    public void postUserEdited() {

        ToastDisplay display = new ToastDisplay(activity);
        display.displayUserEditedToast(name);

        SharedActivityFunctions sharedFunctions = new SharedActivityFunctions(activity);
        sharedFunctions.populateListView(MonitorEnum.MONITORS_ACTIVITY);
    }

}