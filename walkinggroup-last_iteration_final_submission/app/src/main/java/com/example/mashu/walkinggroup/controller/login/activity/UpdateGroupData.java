package com.example.mashu.walkinggroup.controller.login.activity;

import android.app.Activity;

import com.example.mashu.walkinggroup.controller.login.activity.user.info.storage.UserInfoStorage;
import com.example.mashu.walkinggroup.controller.main.menu.activity.MainMenuActivity;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.model.Group;
import com.example.mashu.walkinggroup.model.user.User;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import java.util.List;

import retrofit2.Call;

/**
 * The UpdateGroupData class handles updating the groups that the
 * user is a part of as well as the groups the user leads.
 */

public class UpdateGroupData {

    private final static int REMOVE_USER_INFO_ON_LOG_OUT = 1;

    private Activity activity;
    private UserInfoStorage userInfoStorage;


    public UpdateGroupData(Activity activity, UserInfoStorage userInfoStorage) {

        this.activity = activity;
        this.userInfoStorage = userInfoStorage;
    }

    public void updateCurrentUserGroupData(){

        Call<User> caller = ProxyManager.getProxy(activity).getUserById(CurrentUserManager.getCurrentUser().getId());
        ProxyBuilder.callProxy(activity, caller, this::onCurrentUserWithGroupDataReceived);
    }

    private void onCurrentUserWithGroupDataReceived(User currentUserWithGroupData) {

        CurrentUserManager.getCurrentUser().setMemberOfGroups(currentUserWithGroupData.getMemberOfGroups());
        CurrentUserManager.getCurrentUser().setLeadsGroups(currentUserWithGroupData.getLeadsGroups());

        updateCurrentUserGroupsWithRouteData();
    }

    private void updateCurrentUserGroupsWithRouteData() {

        Call<List<Group>> caller = ProxyManager.getProxy(activity).getGroups();
        ProxyBuilder.callProxy(activity, caller, this::onGroupsReceived);
    }

    private void onGroupsReceived(List<Group> allGroupsReceived) {

        if(CurrentUserManager.getCurrentUser().getMemberOfGroups() != null) {

            for (Group group : CurrentUserManager.getCurrentUser().getMemberOfGroups()) {
                updateGroupWithDestinationCoordinates(group, allGroupsReceived);
            }
        }

        if(CurrentUserManager.getCurrentUser().getLeadsGroups() != null) {

            for (Group group : CurrentUserManager.getCurrentUser().getLeadsGroups()) {
                updateGroupWithDestinationCoordinates(group, allGroupsReceived);
            }
        }

        userInfoStorage.saveUserInfo();
        activity.startActivityForResult(MainMenuActivity.makeIntent(activity), REMOVE_USER_INFO_ON_LOG_OUT);
    }

    private void updateGroupWithDestinationCoordinates(Group currentGroup, List<Group> allGroupsReceived) {

        for(Group groupFromServer: allGroupsReceived){

            if(groupFromServer.getId().equals(currentGroup.getId())){

                currentGroup.setRouteLatArray(groupFromServer.getRouteLatArray());
                currentGroup.setRouteLngArray(groupFromServer.getRouteLngArray());
                break;
            }
        }
    }

}