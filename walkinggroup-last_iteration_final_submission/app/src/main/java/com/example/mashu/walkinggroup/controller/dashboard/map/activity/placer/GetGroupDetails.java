package com.example.mashu.walkinggroup.controller.dashboard.map.activity.placer;

import android.app.Activity;

import com.example.mashu.walkinggroup.controller.dashboard.map.activity.placer.placing.process.PlaceMarkers;
import com.example.mashu.walkinggroup.model.Group;
import com.example.mashu.walkinggroup.model.user.User;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;
import com.google.android.gms.maps.GoogleMap;

import java.util.List;

import retrofit2.Call;

/**
 * The GetGroupDetails class handles getting all the
 * group details from the UserFinder class.
 */

public class GetGroupDetails {

    private Activity activity;
    private GoogleMap googleMap;
    private List<User> usersToMark;
    private List<Long> userMemberGroupsIDs;
    private List<Long> groupLeaderIDs;
    private List<Group> allGroupsReceived;


    public GetGroupDetails(Activity activity, GoogleMap googleMap, List<User> usersToMark,
                           List<Long> userMemberGroupsIDs, List<Long> groupLeaderIDs, List<Group> allGroupsReceived) {

        this.activity = activity;
        this.googleMap = googleMap;
        this.usersToMark = usersToMark;
        this.userMemberGroupsIDs = userMemberGroupsIDs;
        this.groupLeaderIDs = groupLeaderIDs;
        this.allGroupsReceived = allGroupsReceived;
    }

    public void getGroupDetailsOnceAllGroupsAreReceived() {

        for(Group group: allGroupsReceived){

            checkIfMonitoredUserIsAGroupMember(group);

            if(groupLeaderIDs.size() == userMemberGroupsIDs.size()){
                break;
            }
        }

        getGroupLeaderDetails();
    }

    private void checkIfMonitoredUserIsAGroupMember(Group group) {

        if(userMemberGroupsIDs.contains(group.getId())){
            groupLeaderIDs.add(group.getLeader().getId());
        }
    }

    private void getGroupLeaderDetails() {

        Call<List<User>> caller = ProxyManager.getProxy(activity).getUsers();
        ProxyBuilder.callProxy(activity, caller, this::onAllUsersReceived);
    }

    private void onAllUsersReceived(List<User> allUsersReceived) {

        for(User user: allUsersReceived){

            checkIfUserIsAGroupLeader(user);

            if(groupLeaderIDs.isEmpty()){
                break;
            }
        }

        PlaceMarkers placeMarkers = new PlaceMarkers(activity, googleMap, usersToMark);
        placeMarkers.placeMarkersOnMap();

        usersToMark.clear();
    }

    private void checkIfUserIsAGroupLeader(User user) {

        if(groupLeaderIDs.contains(user.getId())){

            usersToMark.add(user);
            groupLeaderIDs.remove(user.getId());
        }
    }

}