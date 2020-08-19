package com.example.mashu.walkinggroup.controller.account.monitoring.activities.common;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.model.user.User;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * The SharedActivityFunctions class describes all the methods
 * shared between the two monitoring activities.
 */

public class SharedActivityFunctions {

    private Activity activity;
    private MonitorEnum monitorEnum;

    private static List<User> usersToDisplay;


    public SharedActivityFunctions(Activity activity) {

        this.activity = activity;
        SharedActivityFunctions.usersToDisplay = new ArrayList<>();
    }

    public static List<User> getUsersToDisplay() {
        return usersToDisplay;
    }

    public void populateListView(MonitorEnum monitorEnum) {

        this.monitorEnum = monitorEnum;
        setupListView();
    }

    private void setupListView() {

        Call<List<User>> caller = null;
        Long userID = CurrentUserManager.getCurrentUser().getId();

        if(monitorEnum == MonitorEnum.MONITORS_ACTIVITY) {
            caller = ProxyManager.getProxy(activity).getMonitorsUsers(userID);
        } else if(monitorEnum == MonitorEnum.MONITORED_BY_ACTIVITY){
            caller = ProxyManager.getProxy(activity).getMonitoredByUsers(userID);
        }

        if(caller != null) {
            ProxyBuilder.callProxy(activity, caller, this::onMonitorsResponse);
        }
    }

    private void onMonitorsResponse(List<User> returnedUsers) {

        usersToDisplay = returnedUsers;
        ListView listView = null;

        if(monitorEnum == MonitorEnum.MONITORS_ACTIVITY) {
            listView = activity.findViewById(R.id.monitors_listView);
        } else if(monitorEnum == MonitorEnum.MONITORED_BY_ACTIVITY){
            listView = activity.findViewById(R.id.monitored_by_listView);
        }

        if(listView != null) {

            ArrayAdapter<User> adapter = new AccountMonitorListAdapter(activity, returnedUsers);
            AccountMonitorListAdapter.populateListView(listView, adapter);
        }
    }

}