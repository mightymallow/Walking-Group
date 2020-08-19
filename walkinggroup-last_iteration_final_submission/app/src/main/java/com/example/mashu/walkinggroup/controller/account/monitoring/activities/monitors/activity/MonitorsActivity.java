package com.example.mashu.walkinggroup.controller.account.monitoring.activities.monitors.activity;

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
import com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.MonitorEnum;
import com.example.mashu.walkinggroup.controller.shared.methods.CustomToolbar;
import com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.RemoveUserFromList;
import com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.SharedActivityFunctions;
import com.example.mashu.walkinggroup.model.user.User;

/**
 * The MonitorsActivity deals with displaying and manipulating
 * the list of users the current user monitors.
 */

public class MonitorsActivity extends AppCompatActivity {

    private static final String NAME = "User name";
    private static final int ADD_USER_REQUEST_CODE = 116;
    private static final int EDIT_USER_REQUEST_CODE = 413;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitors);

        CustomToolbar.setupCustomToolbar(MonitorsActivity.this, R.id.monitors_custom_toolbar,
                                         getString(R.string.monitors_subtitle));

        SharedActivityFunctions sharedFunctions = new SharedActivityFunctions(this);
        sharedFunctions.populateListView(MonitorEnum.MONITORS_ACTIVITY);

        ListView monitorsListView = findViewById(R.id.monitors_listView);

        RemoveUserFromList remove = new RemoveUserFromList(this);
        remove.onListItemLongClick(monitorsListView, MonitorEnum.MONITORS_ACTIVITY);

        EditUser edit = new EditUser(this);
        edit.onListItemClick(monitorsListView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.monitors_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.add_user_to_monitor){

            Intent intent = AddUserActivity.makeIntent(getApplicationContext(), MonitorEnum.MONITORS_ACTIVITY);
            startActivityForResult(intent, ADD_USER_REQUEST_CODE);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        PostActivityResult post = new PostActivityResult(MonitorsActivity.this, data.getStringExtra(NAME));

        if(requestCode == ADD_USER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            post.postUserAdded();
        } else if(requestCode == EDIT_USER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            post.postUserEdited();
        }
    }

    public static Intent makeIntent(Context context){
        return new Intent(context, MonitorsActivity.class);
    }

    public static Intent makeActivityResultIntent(Context context, String name){

        Intent intent = new Intent(context, MonitorsActivity.class);
        intent.putExtra(NAME, name);

        return intent;
    }

    public static void startActivityForResult(Activity activity, Intent intent){
        activity.startActivityForResult(intent, EDIT_USER_REQUEST_CODE);
    }

    public static Intent addNameExtraToIntent(User userOfInterest){

        Intent intent = new Intent();

        intent.putExtra(NAME, userOfInterest.getName());
        return intent;
    }

}