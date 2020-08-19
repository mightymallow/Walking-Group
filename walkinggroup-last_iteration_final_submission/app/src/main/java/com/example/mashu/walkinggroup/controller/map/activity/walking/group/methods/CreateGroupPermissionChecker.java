package com.example.mashu.walkinggroup.controller.map.activity.walking.group.methods;

import android.app.Activity;

import com.example.mashu.walkinggroup.controller.toast.ToastDisplay;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.model.permission.requests.PermissionRequest;
import com.example.mashu.walkinggroup.model.permission.requests.PermissionStatus;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import java.util.List;

import retrofit2.Call;

/**
 * The CreateGroupPermissionChecker class handles
 * checking if there are any pending requests
 * to create a group with the current user as
 * the leader of the group.
 */

public class CreateGroupPermissionChecker {

    private Activity activity;


    public CreateGroupPermissionChecker(Activity activity) {
        this.activity = activity;
    }

    public void checkForPermissions(){

        Call<List<PermissionRequest>> caller = ProxyManager.getProxy(activity).getPermissionsWithGivenStatus(PermissionStatus.PENDING);
        ProxyBuilder.callProxy(activity, caller, this::onPendingPermissionsReceived);
    }

    private void onPendingPermissionsReceived(List<PermissionRequest> pendingPermissionRequests) {

        for(PermissionRequest permissionRequest: pendingPermissionRequests){

            if(currentUserHasAPendingRequestToLeadGroup(permissionRequest)){
                ToastDisplay display = new ToastDisplay(activity);
                display.displayWaitingForResponseToLeadGroupToast(permissionRequest.getGroupG().getId());
            }
        }
    }

    private boolean currentUserHasAPendingRequestToLeadGroup(PermissionRequest permissionRequest) {
        return permissionRequest.getAction().equals("A_LEAD_GROUP") &&
               permissionRequest.getUserA().getId().equals(CurrentUserManager.getCurrentUser().getId());
    }

}