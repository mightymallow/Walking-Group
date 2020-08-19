package com.example.mashu.walkinggroup.controller.permissions.activity.authorizor.data;

import android.app.Activity;

import com.example.mashu.walkinggroup.model.permission.requests.PermissionRequest;
import com.example.mashu.walkinggroup.model.permission.requests.PermissionStatus;

import java.util.List;

/**
 * The HandlePermissionRequests class handles
 * building a list of concerned authorizors
 * when it comes to permission requests.
 */

public class HandlePermissionRequests {

    private static final int START_POSITION = 0;

    private List<PermissionRequest> permissionRequests;
    private PermissionStatus permissionStatus;
    private StringBuilder authorizorContent;
    private Activity activity;


    public HandlePermissionRequests(List<PermissionRequest> permissionRequests, PermissionStatus permissionStatus,
                                    Activity activity) {

        this.permissionRequests = permissionRequests;
        this.permissionStatus = permissionStatus;
        this.authorizorContent = new StringBuilder();
        this.activity = activity;
    }

    public void goThroughPermissionRequests(){

        for(PermissionRequest permissionRequest: permissionRequests){
            goThroughGroupsOfAuthorizorsForPermissionRequest(permissionRequest);
        }
    }

    private void goThroughGroupsOfAuthorizorsForPermissionRequest(PermissionRequest permissionRequest) {

        if(permissionStatus == PermissionStatus.PENDING){

            AppendTextForPendingPermission appender = new AppendTextForPendingPermission(activity);
            authorizorContent = appender.append(permissionRequest);

        } else {

            AppendTextForApprovedOrDeniedPermission appender = new AppendTextForApprovedOrDeniedPermission(activity, permissionStatus);
            authorizorContent = appender.append(permissionRequest);

        }

        permissionRequest.updateAuthorizorContent(authorizorContent.toString());
        authorizorContent.delete(START_POSITION, authorizorContent.length());
    }

}