package com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.checking;

import android.app.Activity;

import com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.MonitorEnum;
import com.example.mashu.walkinggroup.controller.shared.methods.account.monitoring.dialog.display.AccountMonitoringDialog;
import com.example.mashu.walkinggroup.controller.toast.ToastDisplay;

/**
 * The PostPermissionRequestSearch class handles
 * the functionality of the CheckThroughPermissions class
 * post the appropriate permission request being found.
 */

public class PostPermissionRequestSearch {

    private Activity activity;
    private MonitorEnum monitorEnum;
    private boolean isForRemove;
    private int position;


    public PostPermissionRequestSearch(Activity activity, MonitorEnum monitorEnum, boolean isForRemove, int position) {

        this.activity = activity;
        this.monitorEnum = monitorEnum;
        this.isForRemove = isForRemove;
        this.position = position;
    }

    public void postPermissionRequestSearch(boolean isPendingRequest, Long userID) {

        if(isPendingRequest && userID != null){
            displayToast(userID);
        } else {
            showDialog();
        }
    }

    private void displayToast(Long userID) {

        ToastDisplay display = new ToastDisplay(activity);

        if(isForRemove){
            display.displayRequestAlreadySentToAddOrRemoveUser(userID, true, monitorEnum);
        } else {
            display.displayRequestAlreadySentToAddOrRemoveUser(userID, false, monitorEnum);
        }
    }

    private void showDialog() {

        AccountMonitoringDialog dialog = new AccountMonitoringDialog(activity, monitorEnum, position);

        if(isForRemove){
            dialog.buildRemoveListItemDialog().show();
        } else {
            dialog.buildAddListItemDialog().show();
        }
    }

}