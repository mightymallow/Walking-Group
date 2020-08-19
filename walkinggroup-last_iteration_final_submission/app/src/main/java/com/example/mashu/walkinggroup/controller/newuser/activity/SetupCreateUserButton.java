package com.example.mashu.walkinggroup.controller.newuser.activity;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.newuser.activity.validate.inputs.ValidateEmailWithRegex;
import com.example.mashu.walkinggroup.controller.newuser.activity.validate.inputs.ValidateUserInputs;
import com.example.mashu.walkinggroup.controller.toast.ToastDisplay;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.model.user.User;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import retrofit2.Call;

/**
 * The SetupCreateUserButton class is used to
 * setup the create user button in the NewUserActivity.
 */

public class SetupCreateUserButton {

    private Activity activity;


    public SetupCreateUserButton(Activity activity) {
        this.activity = activity;
    }

    public void setCreateUserBtn(){

        Button createUserBtn = activity.findViewById(R.id.create_user_button);

        createUserBtn.setOnClickListener((View view) -> {

            EditText nameEditText = activity.findViewById(R.id.user_name);
            String name = nameEditText.getText().toString();
            CurrentUserManager.getCurrentUser().setName(name);

            EditText emailEditText = activity.findViewById(R.id.email);
            String email = emailEditText.getText().toString();
            CurrentUserManager.getCurrentUser().setEmail(email);

            EditText passwordEditText = activity.findViewById(R.id.password);
            String password = passwordEditText.getText().toString();
            CurrentUserManager.getCurrentUser().setPassword(password);

            if(ValidateEmailWithRegex.isInputCorrect() && ValidateUserInputs.isPasswordCorrect()){

                executeUserCreate();

            } else {

                ToastDisplay display = new ToastDisplay(activity);
                display.displayToast(R.string.check_inputs, Toast.LENGTH_SHORT);

            }
        });
    }

    private void executeUserCreate() {

        CurrentUserManager.getCurrentUser().setEmail(ValidateEmailWithRegex.getMostRecentCorrectInput());
        Call<User> caller = ProxyManager.getProxy(activity).createUser(CurrentUserManager.getCurrentUser());
        ProxyBuilder.callProxy(activity, caller, this::onUserCreated);
    }

    private void onUserCreated(User userCreated) {

        CurrentUserManager.getCurrentUser().setId(userCreated.getId());

        activity.setResult(Activity.RESULT_OK);
        activity.finish();
    }

}