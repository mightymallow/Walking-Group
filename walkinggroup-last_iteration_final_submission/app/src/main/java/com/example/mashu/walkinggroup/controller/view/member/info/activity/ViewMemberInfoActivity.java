package com.example.mashu.walkinggroup.controller.view.member.info.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.shared.methods.CustomToolbar;
import com.example.mashu.walkinggroup.controller.view.member.info.activity.display.data.DisplayGroupMembers;

/**
 * The ViewMemberInfoActivity is responsible for
 * displaying the member info for a group along
 * with info on the monitors of the members of
 * that same group.
 */

public class ViewMemberInfoActivity extends AppCompatActivity {

    private static final String GROUP_MANAGER_ACTIVITIES_LAT_KEY = "com.example.mashu.walkinggroup.controller." +
                                                                   "group.manager.activities - Group Manager Activities" +
                                                                   "Latitude";

    private static final String GROUP_MANAGER_ACTIVITIES_LNG_KEY = "com.example.mashu.walkinggroup.controller." +
                                                                   "group.manager.activities - Group Manager Activities" +
                                                                   "Longitude";
    private final static int DEFAULT_VALUE = 0;

    private double destinationLatitude;
    private double destinationLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_member_info);

        CustomToolbar.setupCustomToolbar(ViewMemberInfoActivity.this, R.id.member_info_custom_toolbar,
                getString(R.string.view_group_member_info_subtitle));

        destinationLatitude = getIntent().getDoubleExtra(GROUP_MANAGER_ACTIVITIES_LAT_KEY, DEFAULT_VALUE);
        destinationLongitude = getIntent().getDoubleExtra(GROUP_MANAGER_ACTIVITIES_LNG_KEY, DEFAULT_VALUE);

        if(areDestinationCoordinatesValid()) {

            DisplayGroupMembers displayGroupMembers =
                    new DisplayGroupMembers(ViewMemberInfoActivity.this, destinationLatitude, destinationLongitude);

            displayGroupMembers.retrieveAllGroupsFromServer();

        }
    }

    private boolean areDestinationCoordinatesValid() {
        return destinationLatitude != DEFAULT_VALUE && destinationLongitude != DEFAULT_VALUE;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public static Intent makeIntent(Context context){
        return new Intent(context, ViewMemberInfoActivity.class);
    }

}