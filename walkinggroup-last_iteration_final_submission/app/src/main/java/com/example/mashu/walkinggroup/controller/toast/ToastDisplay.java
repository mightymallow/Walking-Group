package com.example.mashu.walkinggroup.controller.toast;

import android.app.Activity;
import android.view.Gravity;
import android.widget.Toast;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.MonitorEnum;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.model.Group;
import com.example.mashu.walkinggroup.model.user.User;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import retrofit2.Call;

/**
 * The ToastDisplay class handles displaying toasts in the application.
 */

public class ToastDisplay {

    private static final int X_OFFSET = 0;
    private static final int Y_OFFSET = 250;

    private static boolean isForRemove;
    private static MonitorEnum monitorEnum;
    private static Long groupID;
    private static String userName;

    private Activity activity;


    public ToastDisplay(Activity activity) {
        this.activity = activity;
    }

    public void displayToast(int messageResource, int duration){
        Toast.makeText(activity, activity.getString(messageResource), duration).show();
    }

    public void displayWelcomeToast(){

        Toast toast = Toast.makeText(activity, activity.getString(R.string.welcome_message, CurrentUserManager.getCurrentUser().getName()),
                                   Toast.LENGTH_SHORT);

        toast.setGravity(Gravity.CENTER_VERTICAL, X_OFFSET, Y_OFFSET);
        toast.show();
    }

    public void displayUserAddedToast(String name){

        if(!name.equals(activity.getString(R.string.default_name))) {

            Toast.makeText(activity.getApplicationContext(),
                    activity.getString(R.string.user_requested_to_be_added, name),
                    Toast.LENGTH_SHORT)
                    .show();
        }

    }

    public void displayUserEditedToast(String name){

        Toast.makeText(activity.getApplicationContext(),
                       activity.getString(R.string.user_edited, name),
                       Toast.LENGTH_SHORT)
             .show();

    }

    public void displayWaitingForResponseToLeadGroupToast(Long groupID){

        Call<Group> caller = ProxyManager.getProxy(activity).getGroupById(groupID);
        ProxyBuilder.callProxy(activity, caller, this::onGroupReceived);
    }

    private void onGroupReceived(Group groupReceived) {

        Toast.makeText(activity, activity.getString(R.string.waiting_for_response_to_lead_group, groupReceived.getGroupDescription()),
                       Toast.LENGTH_LONG)
             .show();
    }

    public void displayRequestAlreadySentToAddOrRemoveUser(Long userID, boolean isForRemove, MonitorEnum monitorEnum){

        ToastDisplay.isForRemove = isForRemove;
        ToastDisplay.monitorEnum = monitorEnum;

        Call<User> caller = ProxyManager.getProxy(activity).getUserById(userID);
        ProxyBuilder.callProxy(activity, caller, this::onUserAlreadyRequestedToBeAddedOrRemovedReceived);
    }

    private void onUserAlreadyRequestedToBeAddedOrRemovedReceived(User userRequestedToBeAddedOrRemoved) {

        if(monitorEnum == MonitorEnum.MONITORS_ACTIVITY) {
            displayToastsForMonitorsActivity(userRequestedToBeAddedOrRemoved);
        } else if (monitorEnum == MonitorEnum.MONITORED_BY_ACTIVITY){
            displayToastsForMonitoredByActivity(userRequestedToBeAddedOrRemoved);
        }
    }

    private void displayToastsForMonitorsActivity(User userRequestedToBeAddedOrRemoved) {

        if (isForRemove) {

            Toast.makeText(activity,
                           activity.getString(R.string.waiting_response_from_user_to_stop_monitoring_them,
                                              userRequestedToBeAddedOrRemoved.getName()),
                           Toast.LENGTH_LONG)
                 .show();

        } else {

            Toast.makeText(activity,
                           activity.getString(R.string.waiting_for_response_from_user_to_start_monitoring_them,
                                              userRequestedToBeAddedOrRemoved.getName()),
                           Toast.LENGTH_LONG)
                 .show();

        }
    }

    private void displayToastsForMonitoredByActivity(User userRequestedToBeAddedOrRemoved) {

        if (isForRemove) {

            Toast.makeText(activity,
                           activity.getString(R.string.waiting_response_from_user_to_stop_monitoring_you,
                                              userRequestedToBeAddedOrRemoved.getName()),
                           Toast.LENGTH_LONG)
                 .show();

        } else {

            Toast.makeText(activity,
                           activity.getString(R.string.waiting_for_response_from_user_to_start_monitoring_you,
                                              userRequestedToBeAddedOrRemoved.getName()),
                           Toast.LENGTH_LONG)
                 .show();

        }
    }

    public void displayRequestSentToHaveUserRemovedToast(String name){

        Toast.makeText(activity,
                       activity.getString(R.string.user_removed_message, name),
                       Toast.LENGTH_SHORT)
             .show();
    }

    public void displayGroupRelatedToast(String message, boolean isGroupCreated){

        if(isGroupCreated) {

            Toast.makeText(activity.getApplicationContext(),
                           activity.getString(R.string.request_made_to_lead_group, message),
                           Toast.LENGTH_SHORT)
                 .show();

        } else {

            Toast.makeText(activity.getApplicationContext(),
                    activity.getString(R.string.group_updated, message),
                    Toast.LENGTH_SHORT)
                    .show();

        }
    }

    public void displayPendingAddingOrRemovingUserInGroup(Long userID, Long groupID, boolean isForRemove){

        ToastDisplay.groupID = groupID;
        ToastDisplay.isForRemove = isForRemove;

        Call<User> caller = ProxyManager.getProxy(activity).getUserById(userID);
        ProxyBuilder.callProxy(activity, caller, this::onUserReceived);
    }

    private void onUserReceived(User userReceived) {

        ToastDisplay.userName = userReceived.getName();

        Call<Group> caller = ProxyManager.getProxy(activity).getGroupById(groupID);
        ProxyBuilder.callProxy(activity, caller, this::onGroupByIDReceived);
    }

    private void onGroupByIDReceived(Group groupByIDReceived) {

        if(!isForRemove) {

            Toast.makeText(activity,
                           activity.getString(R.string.waiting_for_response_for_user_to_join_group, userName,
                                              groupByIDReceived.getGroupDescription()),
                           Toast.LENGTH_LONG)
                 .show();

        } else {

            Toast.makeText(activity,
                           activity.getString(R.string.waiting_for_response_for_user_to_leave_group, userName,
                                              groupByIDReceived.getGroupDescription()),
                           Toast.LENGTH_LONG)
                 .show();

        }
    }

    public void displayRequestSentToMakeGroup(String groupName){

        Toast.makeText(activity,
                       activity.getString(R.string.request_made_to_lead_group, groupName),
                       Toast.LENGTH_LONG)
             .show();
    }

}