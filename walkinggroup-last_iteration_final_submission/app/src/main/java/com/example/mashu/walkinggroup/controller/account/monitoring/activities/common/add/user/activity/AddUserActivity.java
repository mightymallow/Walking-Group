package com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.add.user.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.checking.PendingPermissionChecker;
import com.example.mashu.walkinggroup.controller.shared.methods.CustomToolbar;
import com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.MonitorEnum;
import com.example.mashu.walkinggroup.controller.account.monitoring.activities.monitored.by.activity.MonitoredByActivity;
import com.example.mashu.walkinggroup.controller.account.monitoring.activities.monitors.activity.MonitorsActivity;

/**
 * The AddUserActivity deals with adding a user to
 * a list view as a new user to monitor or
 * a new user being monitored.
 */

public class AddUserActivity extends AppCompatActivity {

    private final static String KEY = "ORIGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        Intent intent = getIntent();
        MonitorEnum monitorEnum = MonitorEnum.valueOf(intent.getStringExtra(KEY));

        CustomToolbar.setupCustomToolbar(AddUserActivity.this, R.id.add_user_custom_toolbar,
                                         getString(R.string.add_user_subtitle, monitorEnum.getText()));

        PopulateUsersToAdd populate = new PopulateUsersToAdd(this);
        populate.populateAddListView(monitorEnum);

        ListView addUserListView = findViewById(R.id.add_user_listView);
        addUserListView.setOnItemClickListener(
            (AdapterView<?> parent, View view, int position, long id)  -> {

                PendingPermissionChecker checker = new PendingPermissionChecker(AddUserActivity.this, monitorEnum, position, false);
                checker.checkForPendingPermission();
            });


        Intent activityResultIntent = new Intent();

        if(monitorEnum == MonitorEnum.MONITORS_ACTIVITY){
            activityResultIntent = MonitorsActivity.makeActivityResultIntent(getApplicationContext(),
                                                                             getString(R.string.default_name));
        } else if (monitorEnum == MonitorEnum.MONITORED_BY_ACTIVITY){
            activityResultIntent = MonitoredByActivity.makeActivityResultIntent(getApplicationContext(),
                                                                                getString(R.string.default_name));
        }

        setResult(Activity.RESULT_OK, activityResultIntent);
    }

    public static Intent makeIntent(Context context, MonitorEnum monitorEnum){

        Intent intent = new Intent(context, AddUserActivity.class);
        intent.putExtra(KEY, monitorEnum.toString());
        return intent;
    }

}