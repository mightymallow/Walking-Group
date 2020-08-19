package com.example.mashu.walkinggroup.controller.permissions.activity.update.list.view;

import android.app.Activity;

import com.example.mashu.walkinggroup.controller.permissions.activity.authorizor.data.AuthorizorsInfo;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.model.permission.requests.PermissionRequest;
import com.example.mashu.walkinggroup.model.permission.requests.PermissionStatus;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * The RetrievePermissionRequests class handles
 * retrieving all types of permission requests for
 * the current user.
 */

public class RetrievePermissionRequests {

    private Activity activity;
    private List<PermissionRequest> approvedPermissionRequests;
    private List<PermissionRequest> deniedPermissionRequests;
    private List<PermissionRequest> pendingPermissionRequests;

    public RetrievePermissionRequests(Activity activity) {

        this.activity = activity;
        this.approvedPermissionRequests = new ArrayList<>();
        this.deniedPermissionRequests = new ArrayList<>();
        this.pendingPermissionRequests = new ArrayList<>();
    }

    public void retrieve(){
        retrieveCurrentUserApprovedPermissions();
    }

    private void retrieveCurrentUserApprovedPermissions() {

        Call<List<PermissionRequest>> caller = ProxyManager
                .getProxy(activity)
                .getPermissionsForUser(CurrentUserManager.getCurrentUser().getId());

        ProxyBuilder.callProxy(activity, caller, this::onCurrentUserPermissionRequestsReceived);
    }

    private void onCurrentUserPermissionRequestsReceived(List<PermissionRequest> receivedPermissionRequests) {
        filterPermissionRequests(receivedPermissionRequests);
    }

    private void filterPermissionRequests(List<PermissionRequest> receivedPermissionRequests) {

        for(PermissionRequest permissionRequest: receivedPermissionRequests){

            if(permissionRequest.getStatus() == PermissionStatus.APPROVED){
                approvedPermissionRequests.add(permissionRequest);
            } else if(permissionRequest.getStatus() == PermissionStatus.DENIED){
                deniedPermissionRequests.add(permissionRequest);
            } else if(permissionRequest.getStatus() == PermissionStatus.PENDING){
                pendingPermissionRequests.add(permissionRequest);
            }
        }

        AuthorizorsInfo authorizorsInfo = new AuthorizorsInfo(activity, approvedPermissionRequests,
                                                              deniedPermissionRequests, pendingPermissionRequests);
        authorizorsInfo.getData();
    }

}