package com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.add.user.activity;

import android.app.Activity;
import android.content.Intent;

import com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.MonitorEnum;
import com.example.mashu.walkinggroup.controller.account.monitoring.activities.monitored.by.activity.MonitoredByActivity;
import com.example.mashu.walkinggroup.controller.account.monitoring.activities.monitors.activity.MonitorsActivity;
import com.example.mashu.walkinggroup.controller.login.activity.UpdateMonitoredByAndMonitorsUsers;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.model.user.User;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import java.util.List;

import retrofit2.Call;

/**
 * The AddUserFunctionality class deals with adding users
 * to the list views in the monitor activities.
 */

public class AddUserFunctionality {

    private static List<User> usersDisplayed = null;

    private Activity activity;
    private MonitorEnum monitorEnum;
    private int position;
    private Intent activityResultIntent;


    public AddUserFunctionality(Activity activity, MonitorEnum monitorEnum, int position) {

        this.activity = activity;
        this.monitorEnum = monitorEnum;
        this.position = position;
    }

    public static void setUsersDisplayed(List<User> usersDisplayed) {
        AddUserFunctionality.usersDisplayed = usersDisplayed;
    }

    public void addUserFromUsersDisplayed(){

        if(usersDisplayed != null) {
            addNewUser();
        }
    }

    private void addNewUser() {

        Call<List<User>> caller = null;
        User currentUser = CurrentUserManager.getCurrentUser();
        User userToAdd = usersDisplayed.get(position);

        if (monitorEnum == MonitorEnum.MONITORS_ACTIVITY) {
            caller = ProxyManager.getProxy(activity).addToMonitorsUsers(currentUser.getId(), userToAdd);
        } else if (monitorEnum == MonitorEnum.MONITORED_BY_ACTIVITY) {
            caller = ProxyManager.getProxy(activity).addToMonitoredByUsers(currentUser.getId(), userToAdd);
        }

        if(caller != null) {

            if(monitorEnum == MonitorEnum.MONITORS_ACTIVITY) {
                activityResultIntent = MonitorsActivity.makeActivityResultIntent(activity, userToAdd.getName());
            } else if (monitorEnum == MonitorEnum.MONITORED_BY_ACTIVITY){
                activityResultIntent = MonitoredByActivity.makeActivityResultIntent(activity, userToAdd.getName());
            }

            ProxyBuilder.callProxy(activity, caller, this::onUserAdded);
        }
    }

    private void onUserAdded(List<User> returnedUsers) {

        UpdateMonitoredByAndMonitorsUsers update = new UpdateMonitoredByAndMonitorsUsers(activity, null);
        update.updateCurrentUserMonitorsAndMonitoredBy();

        activity.setResult(Activity.RESULT_OK, activityResultIntent);
        activity.finish();
    }

}