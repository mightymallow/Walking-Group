package com.example.mashu.walkinggroup.controller.view.member.info.activity.display.data;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.AccountMonitorListAdapter;
import com.example.mashu.walkinggroup.model.Group;
import com.example.mashu.walkinggroup.model.user.User;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * The DisplayGroupMembers class is responsible for setting
 * up the views for the group members of the clicked group
 * in the ViewMemberInfoActivity.
 */

public class DisplayGroupMembers {

    private Activity activity;
    private double destinationLatitude;
    private double destinationLongitude;
    private List<User> validUsersToDisplay;
    private Group clickedGroup;


    public DisplayGroupMembers(Activity activity, double destinationLatitude, double destinationLongitude) {

        this.activity = activity;
        this.destinationLatitude = destinationLatitude;
        this.destinationLongitude = destinationLongitude;
        this.validUsersToDisplay = new ArrayList<>();
    }

    public void retrieveAllGroupsFromServer() {

        Call<List<Group>> caller = ProxyManager.getProxy(activity).getGroups();
        ProxyBuilder.callProxy(activity, caller, this::onAllGroupsReceivedFromServer);
    }

    private void onAllGroupsReceivedFromServer(List<Group> allGroupsReceivedFromServer) {

        validUsersToDisplay = new ArrayList<>();

        for (Group group: allGroupsReceivedFromServer){

            if(group.accessDestinationLatitude() == destinationLatitude &&
               group.accessDestinationLongitude() == destinationLongitude){

                clickedGroup = group;
                retrieveAllMembersOfClickedGroupFromServer();

                break;

            }
        }
    }

    private void retrieveAllMembersOfClickedGroupFromServer() {

        Call<List<User>> caller = ProxyManager.getProxy(activity).getGroupMembers(clickedGroup.getId());
        ProxyBuilder.callProxy(activity, caller, this::onGroupMembersReceived);
    }

    private void onGroupMembersReceived(List<User> groupMembersReceived) {

        validUsersToDisplay.addAll(groupMembersReceived);

        if(clickedGroup.getLeader() != null){

            Call<User> caller = ProxyManager.getProxy(activity).getUserById(clickedGroup.getLeader().getId());
            ProxyBuilder.callProxy(activity, caller, this::onGroupLeaderWithFullDataReceived);

        } else {

            setupListViewForClickedGroupMembersAndLeader();

        }
    }

    private void onGroupLeaderWithFullDataReceived(User groupLeaderWithFullDataReceived) {

        validUsersToDisplay.add(groupLeaderWithFullDataReceived);

        setupListViewForClickedGroupMembersAndLeader();
    }

    private void setupListViewForClickedGroupMembersAndLeader() {

        ListView viewUserInfoListView = activity.findViewById(R.id.view_user_info_listView);

        ArrayAdapter<User> adapter = new AccountMonitorListAdapter(activity, validUsersToDisplay);
        AccountMonitorListAdapter.populateListView(viewUserInfoListView, adapter);

        DisplayGroupMemberMonitors displayMonitors = new DisplayGroupMemberMonitors(activity, validUsersToDisplay);
        displayMonitors.setupListViewForClickedGroupMemberAndLeaderMonitors();
    }

}