package com.example.mashu.walkinggroup.controller.dashboard.map.activity.placer;

import android.app.Activity;

import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.model.Group;
import com.example.mashu.walkinggroup.model.user.User;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;
import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * The UserFinder class handles finding
 * all the users the current user monitors
 * as well as all the group leaders of
 * the groups they are in.
 */

public class UserFinder {

    private Activity activity;
    private GoogleMap googleMap;
    private List<User> usersToMark;
    private List<Long> userMemberGroupsIDs;
    private List<Long> groupLeaderIDs;

    public UserFinder(Activity activity, GoogleMap googleMap) {

        this.activity = activity;
        this.googleMap = googleMap;
        this.usersToMark = new ArrayList<>();
        this.userMemberGroupsIDs = new ArrayList<>();
        this.groupLeaderIDs = new ArrayList<>();
    }

    public void retrieveAllUsersToMarkFromServer(){

        Call<List<User>> caller = ProxyManager.getProxy(activity).getMonitorsUsers(CurrentUserManager.getCurrentUser().getId());
        ProxyBuilder.callProxy(activity, caller, this::onMonitorsUsersReceived);
    }

    private void onMonitorsUsersReceived(List<User> monitorsUsersReceived) {

        usersToMark.addAll(monitorsUsersReceived);

        for(User user: monitorsUsersReceived){
            retrieveGroupIDs(user);
        }

        retrieveAllGroups();
    }

    private void retrieveGroupIDs(User user) {

        List<Group> groupsUserMember = user.getMemberOfGroups();

        for(Group group: groupsUserMember){
            userMemberGroupsIDs.add(group.getId());
        }
    }

    private void retrieveAllGroups() {

        Call<List<Group>> caller = ProxyManager.getProxy(activity).getGroups();
        ProxyBuilder.callProxy(activity, caller, this::onAllGroupsReceived);
    }

    private void onAllGroupsReceived(List<Group> allGroupsReceived) {

        GetGroupDetails getGroupDetails = new GetGroupDetails(activity, googleMap, usersToMark,
                                                              userMemberGroupsIDs, groupLeaderIDs, allGroupsReceived);
        getGroupDetails.getGroupDetailsOnceAllGroupsAreReceived();
    }

}