package com.example.mashu.walkinggroup.controller.login.activity.login.process;

import android.app.Activity;

import com.example.mashu.walkinggroup.controller.login.activity.UpdateMonitoredByAndMonitorsUsers;
import com.example.mashu.walkinggroup.controller.login.activity.user.info.storage.UserInfoStorage;

/**
 * The MainMenuStarter class handles starting
 * the MainMenuActivity once the user has logged in.
 */

public class MainMenuStarter {

    private Activity activity;
    private UserInfoStorage userInfoStorage;


    public MainMenuStarter(Activity activity, UserInfoStorage userInfoStorage) {
        this.activity = activity;
        this.userInfoStorage = userInfoStorage;
    }

    public void goToMainMenu(){

        updateCurrentUserData();
    }

    private void updateCurrentUserData() {

        UpdateMonitoredByAndMonitorsUsers update = new UpdateMonitoredByAndMonitorsUsers(activity, userInfoStorage);
        update.updateCurrentUserMonitorsAndMonitoredBy();
    }

}
