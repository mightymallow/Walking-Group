package com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.checking;

import android.app.Activity;

import com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.MonitorEnum;
import com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.SharedActivityFunctions;
import com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.add.user.activity.PopulateUsersToAdd;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.model.user.User;
import com.example.mashu.walkinggroup.model.permission.requests.PermissionRequest;

import java.util.List;

/**
 *The CheckThroughPermissions class handles the functionality of the
 * PendingPermissionCheck class with regards to either
 * adding or removing a user to monitor the current user / already monitors the current user or
 * adding or removing a user which the current user will monitor / does monitor.
 */

public class CheckThroughPermissions {

    private Activity activity;
    private MonitorEnum monitorEnum;
    private int position;
    private List<PermissionRequest> pendingPermissionRequests;
    private String action;
    private boolean isForRemove;

    public CheckThroughPermissions(Activity activity, MonitorEnum monitorEnum, int position,
                                   List<PermissionRequest> pendingPermissionRequests,
                                   String action, boolean isForRemove) {

        this.activity = activity;
        this.monitorEnum = monitorEnum;
        this.position = position;
        this.pendingPermissionRequests = pendingPermissionRequests;
        this.action = action;
        this.isForRemove = isForRemove;
    }

    public void cycleThroughPendingPermissions(){

        boolean isPendingRequest = false;
        Long userID = null;

        for(PermissionRequest permissionRequest: pendingPermissionRequests) {

            if(isAppropriatePendingRequestBetweenCurrentAndClickedUsers(permissionRequest)) {

                userID = configureUserID(permissionRequest);
                isPendingRequest = true;
                break;

            }
        }

        PostPermissionRequestSearch post = new PostPermissionRequestSearch(activity, monitorEnum, isForRemove, position);
        post.postPermissionRequestSearch(isPendingRequest, userID);
    }

    private boolean isAppropriatePendingRequestBetweenCurrentAndClickedUsers(PermissionRequest permissionRequest) {

        User clickedUser;

        if(isForRemove){
            clickedUser = SharedActivityFunctions.getUsersToDisplay().get(position);
        } else {
            clickedUser = PopulateUsersToAdd.getUsersToDisplay().get(position);
        }

        if(monitorEnum == MonitorEnum.MONITORS_ACTIVITY && clickedUser != null) {
            return permissionRequest.getAction().equals(action) && pendingCurrentUserToMonitorClickedUser(permissionRequest, clickedUser);
        } else if (monitorEnum == MonitorEnum.MONITORED_BY_ACTIVITY && clickedUser != null){
            return permissionRequest.getAction().equals(action) && pendingClickedUserToMonitorCurrentUser(permissionRequest, clickedUser);
        }

        return false;
    }

    private boolean pendingCurrentUserToMonitorClickedUser(PermissionRequest permissionRequest, User clickedUser) {
        return permissionRequest.getUserA().getId().equals(CurrentUserManager.getCurrentUser().getId()) &&
               permissionRequest.getUserB().getId().equals(clickedUser.getId());
    }

    private boolean pendingClickedUserToMonitorCurrentUser(PermissionRequest permissionRequest, User clickedUser) {
        return permissionRequest.getUserA().getId().equals(clickedUser.getId()) &&
               permissionRequest.getUserB().getId().equals(CurrentUserManager.getCurrentUser().getId());
    }

    private Long configureUserID(PermissionRequest permissionRequest) {

        if(monitorEnum == MonitorEnum.MONITORS_ACTIVITY) {
            return permissionRequest.getUserB().getId();
        } else if(monitorEnum == MonitorEnum.MONITORED_BY_ACTIVITY){
            return permissionRequest.getUserA().getId();
        }

        return null;
    }

}