package com.example.mashu.walkinggroup.controller.newuser.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.shared.methods.CustomToolbar;
import com.example.mashu.walkinggroup.controller.newuser.activity.validate.inputs.ValidateEmailWithRegex;
import com.example.mashu.walkinggroup.controller.newuser.activity.validate.inputs.ValidatePasswordWithRegex;

public class NewUserActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        CustomToolbar.setupCustomToolbar(this, R.id.new_user_custom_toolbar, getString(R.string.new_user_subtitle));

        validateUserInputs();

        SetupCreateUserButton setupCreateUserButton = new SetupCreateUserButton(NewUserActivity.this);
        setupCreateUserButton.setCreateUserBtn();
    }

    private void validateUserInputs() {

        EditText emailEditText = findViewById(R.id.email);
        TextView inputCheckText = findViewById(R.id.input_check_text);
        ImageView emailErrorImageView = findViewById(R.id.email_check);

        ValidateEmailWithRegex emailValidator = new ValidateEmailWithRegex(NewUserActivity.this);
        emailValidator.addInputValidation(emailEditText, inputCheckText, emailErrorImageView,
                                          R.string.email_error_text, R.string.email_regex);

        ValidatePasswordWithRegex passwordValidator =  new ValidatePasswordWithRegex(NewUserActivity.this);
        passwordValidator.addPasswordValidation();
        passwordValidator.addPasswordConfirmationValidation();
    }

    public static Intent makeIntent(Context context){
        return new Intent(context, NewUserActivity.class);
    }

}