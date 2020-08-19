package com.example.mashu.walkinggroup.controller.account.monitoring.activities.common;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.checking.PendingPermissionChecker;
import com.example.mashu.walkinggroup.controller.login.activity.UpdateMonitoredByAndMonitorsUsers;
import com.example.mashu.walkinggroup.controller.toast.ToastDisplay;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.model.user.User;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import java.util.List;

import retrofit2.Call;

/**
 * The RemoveUserFromList class deals with removing
 * a user from a list by long clicking the user's information.
 */

public class RemoveUserFromList {

    private Activity activity;
    private int userToRemovePosition;
    private User userToRemove;
    private MonitorEnum monitorEnum;


    public RemoveUserFromList(Activity activity) {
        this.activity = activity;
    }

    public void setMonitorEnum(MonitorEnum monitorEnum) {
        this.monitorEnum = monitorEnum;
    }

    public void setUserToRemovePosition(int userToRemovePosition) {
        this.userToRemovePosition = userToRemovePosition;
    }

    public void onListItemLongClick(ListView listView, MonitorEnum monitorEnum){

        listView.setOnItemLongClickListener(
                (AdapterView<?> parent, View view, int position, long id) -> {

                    this.monitorEnum = monitorEnum;
                    this.userToRemovePosition = position;

                    PendingPermissionChecker checker = new PendingPermissionChecker(activity, monitorEnum, position, true);
                    checker.checkForPendingPermission();

                    return true;
                }
        );
    }

    public void removeUserAtPosition() {

        Long currentUserID = CurrentUserManager.getCurrentUser().getId();
        Call<List<User>> caller = null;

        if(monitorEnum == MonitorEnum.MONITORS_ACTIVITY) {
            caller = ProxyManager.getProxy(activity).getMonitorsUsers(currentUserID);
        } else if (monitorEnum == MonitorEnum.MONITORED_BY_ACTIVITY){
            caller = ProxyManager.getProxy(activity).getMonitoredByUsers(currentUserID);
        }

        if(caller != null) {
            ProxyBuilder.callProxy(activity, caller, this::onUsersReceived);
        }
    }

    private void onUsersReceived(List<User> receivedUsers) {

        Long currentUserID = CurrentUserManager.getCurrentUser().getId();
        userToRemove = receivedUsers.get(userToRemovePosition);
        Call<Void> caller = null;

        if(monitorEnum == MonitorEnum.MONITORS_ACTIVITY) {
            caller = ProxyManager.getProxy(activity).removeFromMonitorsUsers(currentUserID, userToRemove.getId());
        } else if (monitorEnum == MonitorEnum.MONITORED_BY_ACTIVITY){
            caller = ProxyManager.getProxy(activity).removeFromMonitoredByUsers(currentUserID, userToRemove.getId());
        }

        if(caller != null) {
            ProxyBuilder.callProxy(activity, caller, this::onUserRemoved);
        }
    }

    private void onUserRemoved(Void returnedNothing) {

        UpdateMonitoredByAndMonitorsUsers update = new UpdateMonitoredByAndMonitorsUsers(activity, null);
        update.updateCurrentUserMonitorsAndMonitoredBy();

        ToastDisplay display = new ToastDisplay(activity);
        display.displayRequestSentToHaveUserRemovedToast(userToRemove.getName());

        SharedActivityFunctions sharedFunctions = new SharedActivityFunctions(activity);
        sharedFunctions.populateListView(monitorEnum);
    }

}