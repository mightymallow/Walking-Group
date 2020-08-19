package com.example.mashu.walkinggroup.controller.login.activity.login.process;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.login.activity.user.info.storage.UserInfoStorage;
import com.example.mashu.walkinggroup.controller.newuser.activity.NewUserActivity;
import com.example.mashu.walkinggroup.controller.toast.ToastDisplay;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;

/**
 * The SetupLoginOrCreateNewUserOptions class handles
 * building the functionality that allows the user
 * to either create a new user or login to the app.
 */

public class SetupLoginOrCreateNewUserOptions {

    private final static String TAG = "LoginActivity";
    private final static int CREATE_USER = 0;
    private final static int DISABLE_TIME_IN_MS = 3000;

    private Activity activity;
    private UserInfoStorage userInfoStorage;


    public SetupLoginOrCreateNewUserOptions(Activity activity, UserInfoStorage userInfoStorage) {

        this.activity = activity;
        this.userInfoStorage = userInfoStorage;
    }

    public void setupOptions(){

        setupLoginButton();
        createNewUser();
    }

    private void setupLoginButton(){

        Button loginBtn = activity.findViewById(R.id.login_button);

        loginBtn.setOnClickListener((View view) -> {

            Log.i(TAG, activity.getString(R.string.login_btn_clicked));

            EditText emailEditText = activity.findViewById(R.id.email);
            String email = emailEditText.getText().toString();
            CurrentUserManager.getCurrentUser().setEmail(email);

            EditText passwordEditText = activity.findViewById(R.id.password);
            String password = passwordEditText.getText().toString();
            CurrentUserManager.getCurrentUser().setPassword(password);

            if(email.isEmpty() || password.isEmpty()) {

                ToastDisplay display = new ToastDisplay(activity);
                display.displayToast(R.string.empty_error_toast, Toast.LENGTH_SHORT);

            } else {

                disableButton();

                CheckUserLogIn checkUserLogIn = new CheckUserLogIn(activity, userInfoStorage);
                checkUserLogIn.checkLogin();

            }
        });
    }

    private void disableButton(){

        Button loginBtn = activity.findViewById(R.id.login_button);

        loginBtn.setAlpha(.5f);
        loginBtn.setEnabled(false);

        final Handler handler = new Handler();
        handler.postDelayed(() -> {

            loginBtn.setAlpha(1f);
            loginBtn.setEnabled(true);

        }, DISABLE_TIME_IN_MS);
    }

    private void createNewUser() {

        TextView createUserTextView = activity.findViewById(R.id.create_user_text);
        createUserTextView.setOnClickListener((View view) -> {

            Intent intent = NewUserActivity.makeIntent(activity);
            activity.startActivityForResult(intent, CREATE_USER);
        });
    }

}