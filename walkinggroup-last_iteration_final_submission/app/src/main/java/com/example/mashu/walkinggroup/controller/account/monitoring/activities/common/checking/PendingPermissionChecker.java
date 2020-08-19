package com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.checking;

import android.app.Activity;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.MonitorEnum;
import com.example.mashu.walkinggroup.model.permission.requests.PermissionRequest;
import com.example.mashu.walkinggroup.model.permission.requests.PermissionStatus;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import java.util.List;

import retrofit2.Call;

/**
 * The PendingPermissionChecker class handles checking if
 * either the click on a listview to start monitoring a user
 * or the click on a listview to stop monitoring a user
 * is already a pending request.
 */

public class PendingPermissionChecker {

    private Activity activity;
    private MonitorEnum monitorEnum;
    private int position;
    private boolean forRemove;

    public PendingPermissionChecker(Activity activity, MonitorEnum monitorEnum, int position, boolean forRemove) {

        this.activity = activity;
        this.monitorEnum = monitorEnum;
        this.position = position;
        this.forRemove = forRemove;
    }

    public void checkForPendingPermission(){

        Call<List<PermissionRequest>> caller = ProxyManager.getProxy(activity).getPermissionsWithGivenStatus(PermissionStatus.PENDING);
        ProxyBuilder.callProxy(activity, caller, this::onPendingPermissionRequestsReceived);
    }

    private void onPendingPermissionRequestsReceived(List<PermissionRequest> pendingPermissionRequests) {

        if(forRemove){

            CheckThroughPermissions checkThroughPermissions =
                    new CheckThroughPermissions(activity, monitorEnum, position, pendingPermissionRequests,
                                                activity.getString(R.string.stop_monitor), forRemove);
            checkThroughPermissions.cycleThroughPendingPermissions();

        } else {

            CheckThroughPermissions checkThroughPermissions =
                    new CheckThroughPermissions(activity, monitorEnum, position, pendingPermissionRequests,
                                       activity.getString(R.string.start_monitor), forRemove);
            checkThroughPermissions.cycleThroughPendingPermissions();

        }
    }

}