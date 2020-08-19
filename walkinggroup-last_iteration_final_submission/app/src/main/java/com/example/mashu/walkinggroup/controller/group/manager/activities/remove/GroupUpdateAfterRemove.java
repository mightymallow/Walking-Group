package com.example.mashu.walkinggroup.controller.group.manager.activities.remove;

import android.app.Activity;

import com.example.mashu.walkinggroup.controller.map.activity.walking.group.methods.DisplayWalkingGroups;
import com.example.mashu.walkinggroup.model.Group;
import com.example.mashu.walkinggroup.model.user.User;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import java.util.List;

import retrofit2.Call;

/**
 * The GroupUpdateAfterRemove class handles updating the
 * group once the user has been removed.
 */

public class GroupUpdateAfterRemove {

    private Activity activity;
    private List<User> validUsersToDisplay;
    private Group clickedGroup;
    private int position;


    public GroupUpdateAfterRemove(Activity activity, List<User> validUsersToDisplay, Group clickedGroup, int position) {

        this.activity = activity;
        this.validUsersToDisplay = validUsersToDisplay;
        this.clickedGroup = clickedGroup;
        this.position = position;
    }

    public void updateGroup(){

        RemoveCurrentUserIfClicked removeCurrentUserIfClicked = new RemoveCurrentUserIfClicked(validUsersToDisplay, clickedGroup, position);
        removeCurrentUserIfClicked.removeFromGroupIfNeeded();

        Call<Void> caller = ProxyManager.getProxy(activity)
                .removeGroupMember(clickedGroup.getId(), validUsersToDisplay.get(position).getId());

        ProxyBuilder.callProxy(activity, caller, this::onGroupMemberRemoved);
    }

    private void onGroupMemberRemoved(Void returnedNothing) {

        Call<List<Group>> caller = ProxyManager.getProxy(activity).getGroups();
        ProxyBuilder.callProxy(activity, caller, this::onGroupsReceived);
        activity.finish();}

    private void onGroupsReceived(List<Group> theGroups){
        DisplayWalkingGroups.setTheGroups(theGroups);
    }

}