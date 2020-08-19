package com.example.mashu.walkinggroup.controller.group.manager.activities.remove;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.shared.methods.CustomToolbar;
import com.example.mashu.walkinggroup.model.Group;
import com.example.mashu.walkinggroup.model.user.User;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * The RemoveFromGroupActivity deals with removing users
 * from walking groups.
 */

public class RemoveFromGroupActivity extends AppCompatActivity {

    private static final String GROUP_MANAGER_ACTIVITIES_LAT_KEY = "com.example.mashu.walkinggroup.controller." +
                                                                   "group.manager.activities - Group Manager Activities" +
                                                                   "Latitude";

    private static final String GROUP_MANAGER_ACTIVITIES_LNG_KEY = "com.example.mashu.walkinggroup.controller." +
                                                                   "group.manager.activities - Group Manager Activities" +
                                                                   "Longitude";


    private final static int DEFAULT_VALUE = 0;

    private double destinationLatitude;
    private double destinationLongitude;
    private Group clickedGroup;
    private List<User> memberUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_from_group);

        CustomToolbar.setupCustomToolbar(this, R.id.remove_from_group_custom_toolbar,
                                         getString(R.string.remove_from_group_subtitle));

        extractUsersToRemove();
    }

    private void extractUsersToRemove() {

        this.destinationLatitude = getIntent().getDoubleExtra(GROUP_MANAGER_ACTIVITIES_LAT_KEY, DEFAULT_VALUE);
        this.destinationLongitude = getIntent().getDoubleExtra(GROUP_MANAGER_ACTIVITIES_LNG_KEY, DEFAULT_VALUE);

        if(destinationLatitude != DEFAULT_VALUE && destinationLongitude != DEFAULT_VALUE){

            Call<List<Group>> caller = ProxyManager.getProxy(this).getGroups();
            ProxyBuilder.callProxy(this, caller, this::onGroupsReceived);
        }
    }

    private void onGroupsReceived(List<Group> groupsReceived) {

        for (Group group: groupsReceived){

            if(group.accessDestinationLatitude() == destinationLatitude &&
                    group.accessDestinationLongitude() == destinationLongitude){

                clickedGroup = group;
                memberUsers = clickedGroup.getMemberUsers();
                getValidUsersToDisplay();
                break;
            }
        }
    }

    private void getValidUsersToDisplay() {

        ListPossibleUsersToRemove listUsers = new ListPossibleUsersToRemove(RemoveFromGroupActivity.this,
                                                                            memberUsers, clickedGroup,
                                                                            new ArrayList<>());
        listUsers.determineValidUsers();
    }

    public static Intent makeIntent(Context context){
        return new Intent(context, RemoveFromGroupActivity.class);
    }

}