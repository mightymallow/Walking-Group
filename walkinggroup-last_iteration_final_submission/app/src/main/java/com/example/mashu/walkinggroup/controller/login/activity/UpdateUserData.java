package com.example.mashu.walkinggroup.controller.login.activity;

import android.app.Activity;

import com.example.mashu.walkinggroup.controller.login.activity.user.info.storage.UserInfoStorage;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.model.user.User;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import retrofit2.Call;

/**
 * The UpdateUserData class handles updating extra user data
 * from the server.
 */

public class UpdateUserData {

    private Activity activity;
    private UserInfoStorage userInfoStorage;


    public UpdateUserData(Activity activity, UserInfoStorage userInfoStorage) {

        this.activity = activity;
        this.userInfoStorage = userInfoStorage;
    }

    public void updateCurrentUserExtraUserData(){

        Call<User> caller = ProxyManager.getProxy(activity).getUserById(CurrentUserManager.getCurrentUser().getId());
        ProxyBuilder.callProxy(activity, caller, this::onCurrentUserExtraDataReceived);
    }

    private void onCurrentUserExtraDataReceived(User currentUserWithExtraData) {

        CurrentUserManager.getCurrentUser().setBirthYear(currentUserWithExtraData.getBirthYear());
        CurrentUserManager.getCurrentUser().setBirthMonth(currentUserWithExtraData.getBirthMonth());
        CurrentUserManager.getCurrentUser().setAddress(currentUserWithExtraData.getAddress());
        CurrentUserManager.getCurrentUser().setCellPhone(currentUserWithExtraData.getCellPhone());
        CurrentUserManager.getCurrentUser().setHomePhone(currentUserWithExtraData.getHomePhone());
        CurrentUserManager.getCurrentUser().setGrade(currentUserWithExtraData.getGrade());
        CurrentUserManager.getCurrentUser().setTeacherName(currentUserWithExtraData.getTeacherName());
        CurrentUserManager.getCurrentUser().setEmergencyContactInfo(currentUserWithExtraData.getEmergencyContactInfo());

        CurrentUserManager.getCurrentUser().setRewards(currentUserWithExtraData.getRewards());
        CurrentUserManager.getCurrentUser().setCurrentPoints(currentUserWithExtraData.getCurrentPoints());
        CurrentUserManager.getCurrentUser().setTotalPointsEarned(currentUserWithExtraData.getTotalPointsEarned());

        UpdateGroupData updateGroupData = new UpdateGroupData(activity, userInfoStorage);
        updateGroupData.updateCurrentUserGroupData();
    }

}