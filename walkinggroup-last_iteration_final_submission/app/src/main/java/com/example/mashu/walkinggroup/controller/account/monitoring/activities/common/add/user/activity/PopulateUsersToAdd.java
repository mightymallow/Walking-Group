package com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.add.user.activity;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.AccountMonitorListAdapter;
import com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.MonitorEnum;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.model.user.User;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * The PopulateUsersToAdd class deals with populating
 * the list views of the two monitoring activities.
 */

public class PopulateUsersToAdd {

    private Activity activity;
    private MonitorEnum monitorEnum;
    private List<User> usersToExclude = new ArrayList<>();

    private static List<User> usersToDisplay;


    public PopulateUsersToAdd(Activity activity) {
        this.activity = activity;
    }

    public static List<User> getUsersToDisplay() {
        return usersToDisplay;
    }

    public void populateAddListView(MonitorEnum monitorEnum) {

        this.monitorEnum = monitorEnum;
        setupAddListView();
    }

    private void setupAddListView() {

        Call<List<User>> caller = null;

        if(monitorEnum == MonitorEnum.MONITORS_ACTIVITY) {
            caller = ProxyManager.getProxy(activity).getMonitorsUsers(CurrentUserManager.getCurrentUser().getId());
        } else if(monitorEnum == MonitorEnum.MONITORED_BY_ACTIVITY){
            caller = ProxyManager.getProxy(activity).getMonitoredByUsers(CurrentUserManager.getCurrentUser().getId());
        }

        if(caller != null) {
            ProxyBuilder.callProxy(activity, caller, this::onUsersReturned);
        }
    }


    private void onUsersReturned(List<User> returnedUsers) {

        usersToExclude.addAll(returnedUsers);
        usersToExclude.add(CurrentUserManager.getCurrentUser());

        Call<List<User>> caller = ProxyManager.getProxy(activity).getUsers();
        ProxyBuilder.callProxy(activity, caller, this::onAllUsersReturned);
    }

    private void onAllUsersReturned(List<User> allUsersReturned) {

        usersToDisplay = new ArrayList<>();

        for(User user: allUsersReturned){

            if(userToAddToDisplay(usersToExclude, user)){
                usersToDisplay.add(user);
            }
        }

        AddUserFunctionality.setUsersDisplayed(usersToDisplay);

        ListView listView = activity.findViewById(R.id.add_user_listView);
        ArrayAdapter<User> adapter = new AccountMonitorListAdapter(activity, usersToDisplay);
        AccountMonitorListAdapter.populateListView(listView, adapter);
    }

    private boolean userToAddToDisplay(List<User> users, User userToSearchFor){

        for(User user: users){

            if(user.getId().equals(userToSearchFor.getId())){
                return false;
            }
        }

        return true;
    }

}