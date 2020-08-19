package com.example.mashu.walkinggroup.controller.login.activity.login.process;

import android.app.Activity;
import android.util.Log;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.login.activity.user.info.storage.UserInfoStorage;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.model.user.User;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import retrofit2.Call;

/**
 * The CheckUserLogIn class is part of the logging
 * in process and checks to make sure all
 * details are as should be when the user
 * is logging in.
 */

public class CheckUserLogIn {

    private final static String TAG = "LoginActivity";

    private Activity activity;
    private UserInfoStorage userInfoStorage;


    public CheckUserLogIn(Activity activity, UserInfoStorage userInfoStorage) {

        this.activity = activity;
        this.userInfoStorage = userInfoStorage;
    }

    public void checkLogin(){

        /*CurrentUserManager.getCurrentUser().setName("");
        CurrentUserManager.getCurrentUser().setMonitoredByUsers(null);
        CurrentUserManager.getCurrentUser().setMonitorsUsers(null);
        CurrentUserManager.getCurrentUser().setMemberOfGroups(null);
        CurrentUserManager.getCurrentUser().setLeadsGroups(null);
        CurrentUserManager.getCurrentUser().setLastGpsLocation(null);
        CurrentUserManager.getCurrentUser().setMessages(null);
        CurrentUserManager.getCurrentUser().setRewards(null);
        CurrentUserManager.getCurrentUser().setPendingPermissionRequests(null);*/

        ProxyBuilder.setOnTokenReceiveCallback(this::onTokenReceived);
        Call<Void> caller = ProxyManager.getProxy(activity).login(CurrentUserManager.getCurrentUser());
        ProxyBuilder.callProxy(activity, caller, this::onUserLoggedIn);
    }

    private void onTokenReceived(String token){

        Log.i(TAG, activity.getString(R.string.now_have_token) + token);
        ProxyManager.setToken(token);
    }

    private void onUserLoggedIn(Void returnedNothing) {

        Call<User> caller = ProxyManager.getProxy(activity).getUserByEmail(CurrentUserManager.getCurrentUser().getEmail());
        ProxyBuilder.callProxy(activity, caller, this::onCurrentUserReceived);
    }

    private void onCurrentUserReceived(User currentUser) {

        CurrentUserManager.getCurrentUser().setId(currentUser.getId());
        CurrentUserManager.getCurrentUser().setName(currentUser.getName());

        MainMenuStarter mainMenuStarter = new MainMenuStarter(activity, userInfoStorage);
        mainMenuStarter.goToMainMenu();
    }

}