package com.example.mashu.walkinggroup.controller.login.activity;

import android.app.Activity;
import android.widget.Toast;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.login.activity.user.info.storage.UserInfoStorage;
import com.example.mashu.walkinggroup.controller.shared.methods.DetermineUserType;
import com.example.mashu.walkinggroup.controller.toast.ToastDisplay;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.model.user.User;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import java.util.List;

import retrofit2.Call;

/**
 * The UpdateMonitoredByAndMonitorsUsers class handles updating the userOfInterest
 * with all the users that monitor him/her as well as all
 * the users he/she monitors.
 */

public class UpdateMonitoredByAndMonitorsUsers {

    private Activity activity;
    private User userOfInterest;
    private UserInfoStorage userInfoStorage;


    public UpdateMonitoredByAndMonitorsUsers(Activity activity, UserInfoStorage userInfoStorage) {

        this.activity = activity;
        this.userInfoStorage = userInfoStorage;
    }

    public void updateCurrentUserMonitorsAndMonitoredBy(){

        Call<User> caller = ProxyManager.getProxy(activity).getUserById(CurrentUserManager.getCurrentUser().getId());
        ProxyBuilder.callProxy(activity, caller, this::onUserOfInterestReceived);
    }

    private void onUserOfInterestReceived(User userOfInterest) {

        this.userOfInterest = userOfInterest;

        if(userOfInterest != null) {

            Call<List<User>> caller = ProxyManager.getProxy(activity).getMonitoredByUsers(userOfInterest.getId());
            ProxyBuilder.callProxy(activity, caller, this::onMonitoredByUsersReceived);

        } else {

            ToastDisplay display = new ToastDisplay(activity);
            display.displayToast(R.string.non_existent_user, Toast.LENGTH_SHORT);

        }
    }

    private void onMonitoredByUsersReceived(List<User> monitoredByUsersReceived) {

        userOfInterest.setMonitoredByUsers(monitoredByUsersReceived);
        updateMonitors();
    }

    private void updateMonitors() {

        Call<List<User>> caller = ProxyManager.getProxy(activity).getMonitorsUsers(userOfInterest.getId());
        ProxyBuilder.callProxy(activity, caller, this::onMonitorsUsersReceived);
    }

    private void onMonitorsUsersReceived(List<User> monitorsUsersReceived) {

        userOfInterest.setMonitorsUsers(monitorsUsersReceived);

        DetermineUserType determineUserType = new DetermineUserType(userOfInterest);
        determineUserType.updateCurrentUserType();

        UpdateUserData updateExtraData = new UpdateUserData(activity, userInfoStorage);
        updateExtraData.updateCurrentUserExtraUserData();
    }

}