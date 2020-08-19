package com.example.mashu.walkinggroup.controller.login.activity.user.info.storage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

/**
 * The UserInfoStorage class handles storing and retrieving user info.
 */

public class UserInfoStorage {

    private final static String savedInfo = "SaveInfo";

    private Activity activity;


    public UserInfoStorage(Activity activity) {
        this.activity = activity;
    }

    public void getSavedInfo(){

        SharedPreferences preferences = activity.getSharedPreferences(savedInfo, Context.MODE_PRIVATE);

        Long id = preferences.getLong(activity.getString(R.string.save_id), Long.valueOf(activity.getString(R.string.random_long)));
        String userName = preferences.getString(activity.getString(R.string.save_username), null);
        String email = preferences.getString(activity.getString(R.string.save_user_email), null);
        String password = preferences.getString(activity.getString(R.string.save_user_password), null);

        CurrentUserManager.getCurrentUser().setId(id);
        CurrentUserManager.getCurrentUser().setName(userName);
        CurrentUserManager.getCurrentUser().setEmail(email);
        CurrentUserManager.getCurrentUser().setPassword(password);

        ProxyManager.setToken(preferences.getString(activity.getString(R.string.save_token), null));
    }

    public void saveUserInfo(){

        SharedPreferences sharedPreferences = activity.getSharedPreferences(savedInfo, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putLong(activity.getString(R.string.save_id), CurrentUserManager.getCurrentUser().getId());
        editor.putString(activity.getString(R.string.save_username), CurrentUserManager.getCurrentUser().getName());
        editor.putString(activity.getString(R.string.save_user_email), CurrentUserManager.getCurrentUser().getEmail());
        editor.putString(activity.getString(R.string.save_user_password), CurrentUserManager.getCurrentUser().getPassword());
        editor.putString(activity.getString(R.string.save_token), ProxyManager.getToken());

        editor.apply();
    }

    public void removeUserInfo(){

        SharedPreferences sharedPreferences = activity.getSharedPreferences(savedInfo, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();

        CurrentUserManager.setCurrentUser(null);
    }

}