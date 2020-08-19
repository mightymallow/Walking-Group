package com.example.mashu.walkinggroup.controller.group.manager.activities.add;

import android.app.Activity;
import com.example.mashu.walkinggroup.controller.group.manager.activities.SharedManagerFunctions;
import com.example.mashu.walkinggroup.controller.map.activity.walking.group.methods.DisplayWalkingGroups;
import com.example.mashu.walkinggroup.model.Group;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.model.user.User;
import com.example.mashu.walkinggroup.model.permission.requests.PermissionRequest;
import com.example.mashu.walkinggroup.model.permission.requests.PermissionStatus;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import java.util.List;

import retrofit2.Call;

/**
 * The ListPossibleUsersToAdd class deals with populating the list of
 * possible users to add in AddGroupActivity.
 */

public class ListPossibleUsersToAdd {

    private Activity activity;
    private List<User> membersUsers;
    private Group clickedGroup;
    private List<User> validUsersToDisplay;

    private static boolean isCurrentUserPendingToLeadClickedGroup;


    public ListPossibleUsersToAdd(Activity activity, List<User> membersUsers, Group clickedGroup,
                                  List<User> validUsersToDisplay) {

        this.activity = activity;
        this.membersUsers = membersUsers;
        this.clickedGroup = clickedGroup;
        this.validUsersToDisplay = validUsersToDisplay;
    }

    public void determineValidUsers(){

        Call<List<PermissionRequest>> caller =
                ProxyManager.getProxy(activity)
                            .getPermissionsForGroupWithGivenStatus(PermissionStatus.PENDING, clickedGroup.getId());

        ProxyBuilder.callProxy(activity, caller, this::onPendingPermissionsReceived);
    }

    private void onPendingPermissionsReceived(List<PermissionRequest> pendingPermissionRequests) {

        for(PermissionRequest permissionRequest: pendingPermissionRequests){

            if(isCurrentUserPendingToLeadTheClickedGroup(permissionRequest)){
                isCurrentUserPendingToLeadClickedGroup = true;
                break;
            }
        }

        if(isCurrentUserNotMemberAndNotGroupLeader() && !isCurrentUserPendingToLeadClickedGroup){
            validUsersToDisplay.add(CurrentUserManager.getCurrentUser());
        }

        ComposeListOfUsersToAdd composeListOfUsersToAdd =
                new ComposeListOfUsersToAdd(activity, membersUsers, clickedGroup, isCurrentUserPendingToLeadClickedGroup,
                                            validUsersToDisplay, pendingPermissionRequests);
        composeListOfUsersToAdd.compose();
    }

    private boolean isCurrentUserPendingToLeadTheClickedGroup(PermissionRequest permissionRequest) {

        return permissionRequest.getAction().equals("A_LEAD_GROUP") &&
               permissionRequest.getUserA().getId().equals(CurrentUserManager.getCurrentUser().getId());
    }

    private boolean isCurrentUserNotMemberAndNotGroupLeader() {

        return !SharedManagerFunctions.userIsFoundInList(CurrentUserManager.getCurrentUser(), membersUsers) &&
               !SharedManagerFunctions.currentUserIsGroupLeader(clickedGroup);
    }

    public void addMemberToGroupIfNotAlreadyPresent(int position){

        AddCurrentUserOnClick addCurrentUserOnClick = new AddCurrentUserOnClick(validUsersToDisplay, clickedGroup, position);
        addCurrentUserOnClick.add();

        Call<List<User>> caller = ProxyManager.getProxy(activity)
                                              .addGroupMember(clickedGroup.getId(),
                                                              validUsersToDisplay.get(position));

        ProxyBuilder.callProxy(activity, caller, this::onGroupMemberAdded);
    }

    private void onGroupMemberAdded(List<User> groupUpdated) {

        Call<List<Group>> caller = ProxyManager.getProxy(activity).getGroups();
        ProxyBuilder.callProxy(activity, caller, this::onGroupsReceived);
        activity.finish();
    }

    private void onGroupsReceived(List<Group> theGroups) {
        DisplayWalkingGroups.setTheGroups(theGroups);
    }
}