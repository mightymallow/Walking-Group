package com.example.mashu.walkinggroup.controller.newuser.activity.validate.inputs;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.mashu.walkinggroup.R;

/**
 * The ValidatePasswordWithRegex class handles validating passwords
 * input by the user using regex.
 */

public class ValidatePasswordWithRegex {

    private Activity activity;
    private AfterPasswordAndConfirmPasswordChange afterChange;


    public ValidatePasswordWithRegex(Activity activity) {

        this.activity = activity;
        this.afterChange = new AfterPasswordAndConfirmPasswordChange(activity);
    }

    public void addPasswordValidation(){

        EditText passwordEditText = activity.findViewById(R.id.password);

        passwordEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {  }

            @Override
            public void afterTextChanged(Editable editable) {
                afterChange.afterPasswordTextChanged(editable);
            }
        });
    }

    public void addPasswordConfirmationValidation(){

        EditText confirmPasswordEditText = activity.findViewById(R.id.confirm_password);

        confirmPasswordEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                afterChange.afterConfirmPasswordTextChanged(editable);
            }
        });
    }

}