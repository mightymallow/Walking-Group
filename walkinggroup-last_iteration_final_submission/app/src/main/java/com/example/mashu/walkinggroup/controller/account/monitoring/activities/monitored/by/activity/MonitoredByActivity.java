package com.example.mashu.walkinggroup.controller.account.monitoring.activities.monitored.by.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.add.user.activity.AddUserActivity;
import com.example.mashu.walkinggroup.controller.shared.methods.CustomToolbar;
import com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.MonitorEnum;
import com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.RemoveUserFromList;
import com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.SharedActivityFunctions;
import com.example.mashu.walkinggroup.controller.toast.ToastDisplay;

/**
 * The MonitoredByActivity deals with displaying and manipulating
 * the list of users who the current user is monitored by.
 */

public class MonitoredByActivity extends AppCompatActivity {

    private static final String NAME = "User name";
    private static final int REQUEST_CODE = 413;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitored_by);

        CustomToolbar.setupCustomToolbar(MonitoredByActivity.this, R.id.monitored_by_custom_toolbar,
                                         getString(R.string.monitored_by_subtitle));

        SharedActivityFunctions sharedFunctions = new SharedActivityFunctions(this);
        sharedFunctions.populateListView(MonitorEnum.MONITORED_BY_ACTIVITY);

        ListView monitoredByListView = findViewById(R.id.monitored_by_listView);
        RemoveUserFromList remove = new RemoveUserFromList(this);
        remove.onListItemLongClick(monitoredByListView, MonitorEnum.MONITORED_BY_ACTIVITY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.monitored_by_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.add_user_to_monitor_you){

            Intent intent = AddUserActivity.makeIntent(getApplicationContext(), MonitorEnum.MONITORED_BY_ACTIVITY);
            startActivityForResult(intent, REQUEST_CODE);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            String name = data.getStringExtra(NAME);
            ToastDisplay display = new ToastDisplay(MonitoredByActivity.this);
            display.displayUserAddedToast(name);

            SharedActivityFunctions sharedFunctions = new SharedActivityFunctions(this);
            sharedFunctions.populateListView(MonitorEnum.MONITORED_BY_ACTIVITY);
        }
    }

    public static Intent makeIntent(Context context){
        return new Intent(context, MonitoredByActivity.class);
    }

    public static Intent makeActivityResultIntent(Context context, String name){

        Intent intent = new Intent(context, MonitoredByActivity.class);
        intent.putExtra(NAME, name);

        return intent;
    }

}