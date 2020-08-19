package com.example.mashu.walkinggroup.controller.group.manager.activities;

import android.app.Activity;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.shared.methods.BuildAddAndRemoveUserFromGroupDialog;
import com.example.mashu.walkinggroup.controller.shared.methods.BuildAndSetDialog;
import com.example.mashu.walkinggroup.controller.toast.ToastDisplay;
import com.example.mashu.walkinggroup.model.Group;
import com.example.mashu.walkinggroup.model.user.User;
import com.example.mashu.walkinggroup.model.permission.requests.PermissionRequest;
import com.example.mashu.walkinggroup.model.permission.requests.PermissionStatus;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import java.util.List;

import retrofit2.Call;

/**
 * The PendingGroupMemberChecker class handles checking
 * the list of valid users if there is any user
 * present that is currently pending being added/removed so
 * as to avoid sending another request for them.
 */

public class PendingGroupMemberChecker {

    private Activity activity;
    private User clickedUserInListView;
    private Group clickedGroupOnMap;
    private int position;
    private List<User> membersUsers;
    private List<User> validUsersToDisplay;
    private boolean isMemberPending;
    private boolean isForRemove;


    public PendingGroupMemberChecker(Activity activity, Group clickedGroupOnMap, int position,
                                     List<User> membersUsers, List<User> validUsersToDisplay, boolean isForRemove) {

        this.activity = activity;
        this.clickedUserInListView = validUsersToDisplay.get(position);
        this.clickedGroupOnMap = clickedGroupOnMap;
        this.position = position;
        this.membersUsers = membersUsers;
        this.validUsersToDisplay = validUsersToDisplay;
        this.isMemberPending = false;
        this.isForRemove = isForRemove;
    }

    public void checker(){

        Call<List<PermissionRequest>> caller = ProxyManager.getProxy(activity)
                                                           .getPermissionsWithGivenStatus(PermissionStatus.PENDING);

        ProxyBuilder.callProxy(activity, caller, this::onPendingPermissionsReceived);
    }

    private void onPendingPermissionsReceived(List<PermissionRequest> pendingPermissionRequestsReceived) {

        for(PermissionRequest permissionRequest: pendingPermissionRequestsReceived){

            ToastDisplay display = new ToastDisplay(activity);

            if(clickedUserInListViewIsPendingAdditionOrRemovalFromClickedGroupOnMap(permissionRequest)){

                display.displayPendingAddingOrRemovingUserInGroup(permissionRequest.getUserA().getId(),
                                                                  permissionRequest.getGroupG().getId(),
                                                                  isForRemove);
                isMemberPending = true;
                break;

            }
        }

        BuildAddAndRemoveUserFromGroupDialog display =
                new BuildAddAndRemoveUserFromGroupDialog(activity, new BuildAndSetDialog(activity));

        if(!isMemberPending && !isForRemove){

            display.buildAddUserToGroupDialog(position, membersUsers, clickedGroupOnMap, validUsersToDisplay)
                   .show();

        } else if(!isMemberPending && isForRemove){

            display.buildRemoveUserFromGroupDialog(position, clickedGroupOnMap, validUsersToDisplay)
                   .show();

        }
    }

    private boolean clickedUserInListViewIsPendingAdditionOrRemovalFromClickedGroupOnMap(PermissionRequest permissionRequest) {

        return pendingPermissionIsToJoinOrLeaveGroup(permissionRequest)                   &&
               permissionRequest.getUserA().getId().equals(clickedUserInListView.getId()) &&
               permissionRequest.getGroupG().getId().equals(clickedGroupOnMap.getId());
    }

    private boolean pendingPermissionIsToJoinOrLeaveGroup(PermissionRequest permissionRequest){

        return permissionRequest.getAction().equals(activity.getString(R.string.join_group)) ||
               permissionRequest.getAction().equals(activity.getString(R.string.leave_group));
    }

}