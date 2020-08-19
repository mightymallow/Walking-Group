package com.example.mashu.walkinggroup.controller.login.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.login.activity.login.process.MainMenuStarter;
import com.example.mashu.walkinggroup.controller.login.activity.login.process.SetupLoginOrCreateNewUserOptions;
import com.example.mashu.walkinggroup.controller.login.activity.user.info.storage.UserInfoStorage;
import com.example.mashu.walkinggroup.controller.shared.methods.CustomToolbar;
import com.example.mashu.walkinggroup.controller.toast.ToastDisplay;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

public class LoginActivity extends AppCompatActivity {

    private final static String TAG = "LoginActivity";
    private final static int CREATE_USER = 0;
    private final static int REMOVE_USER_INFO_ON_LOG_OUT = 1;

    private UserInfoStorage userInfoStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        CustomToolbar.setupCustomToolbar(this, R.id.login_custom_toolbar, getString(R.string.login_subtitle));

        userInfoStorage = new UserInfoStorage(this);
        userInfoStorage.getSavedInfo();

        MainMenuStarter mainMenuStarter = new MainMenuStarter(LoginActivity.this, userInfoStorage);

        if(wasUserLoggedIn()){
            mainMenuStarter.goToMainMenu();
        }

        SetupLoginOrCreateNewUserOptions setupLoginOrCreateNewUserOptions =
                new SetupLoginOrCreateNewUserOptions(LoginActivity.this, userInfoStorage);

        setupLoginOrCreateNewUserOptions.setupOptions();
    }

    private boolean wasUserLoggedIn() {
        return ProxyManager.getToken() != null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){

            case CREATE_USER:

                if(resultCode == Activity.RESULT_OK){

                    updateInputs(getString(R.string.result_ok), CurrentUserManager.getCurrentUser().getEmail(), false);

                    ToastDisplay display = new ToastDisplay(LoginActivity.this);
                    display.displayToast(R.string.account_created, Toast.LENGTH_LONG);

                } else {

                    updateInputs(getString(R.string.cancelled), getString(R.string.empty), true);

                }

            case REMOVE_USER_INFO_ON_LOG_OUT:

                if(resultCode == Activity.RESULT_OK){

                    ProxyManager.setToken(null);
                    userInfoStorage.removeUserInfo();

                    updateInputs(getString(R.string.result_ok), getString(R.string.empty), true);
                }
        }
    }

    private void updateInputs(String message, String email, boolean isPasswordEmpty) {

        Log.i(TAG, message);

        EditText emailEditText = findViewById(R.id.email);
        emailEditText.setText(email);

        if(isPasswordEmpty){
            EditText passwordEditText = findViewById(R.id.password);
            passwordEditText.setText(getString(R.string.empty));
        }
    }

}