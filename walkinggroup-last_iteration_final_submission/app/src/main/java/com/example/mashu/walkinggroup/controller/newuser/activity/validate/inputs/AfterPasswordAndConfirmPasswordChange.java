package com.example.mashu.walkinggroup.controller.newuser.activity.validate.inputs;

import android.app.Activity;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The AfterPasswordAndConfirmPasswordChange class handles
 * the functionality when both passwords and confirmed passwords change.
 */

public class AfterPasswordAndConfirmPasswordChange {

    private Activity activity;
    private ValidateUserInputs validator;


    public AfterPasswordAndConfirmPasswordChange(Activity activity) {

        this.activity = activity;
        this.validator = new ValidateUserInputs(activity);
    }

    public void afterPasswordTextChanged(Editable editable) {

        CurrentUserManager.getCurrentUser().setPassword(editable.toString());

        ImageView passwordErrorImageView = activity.findViewById(R.id.password_check);

        TextView inputCheckText = activity.findViewById(R.id.input_check_text);

        inputCheckText.setText(R.string.password_error_text);
        inputCheckText.setVisibility(View.VISIBLE);

        validator.checkLength(CurrentUserManager.getCurrentUser().getPassword(), passwordErrorImageView);

        Pattern p = Pattern.compile(activity.getString(R.string.password_regex));
        Matcher m = p.matcher(CurrentUserManager.getCurrentUser().getPassword());

        if (m.find()) {
            passwordErrorImageView.setImageResource(R.drawable.correct_icon);
            inputCheckText.setVisibility(View.INVISIBLE);

        } else {
            passwordErrorImageView.setImageResource(R.drawable.error_icon);
        }

        ImageView confirmedPasswordErrorImageView = activity.findViewById(R.id.confirm_password_check);
        confirmedPasswordErrorImageView.setVisibility(View.INVISIBLE);

        EditText confirmPasswordEditText = activity.findViewById(R.id.confirm_password);
        String confirmPassword = confirmPasswordEditText.getText().toString();

        validator.checkPasswordValidity(m, confirmPassword);
    }

    public void afterConfirmPasswordTextChanged(Editable editable) {

        ImageView confirmedPasswordErrorImageView = activity.findViewById(R.id.confirm_password_check);
        confirmedPasswordErrorImageView.setVisibility(View.VISIBLE);

        ImageView passwordErrorImageView = activity.findViewById(R.id.password_check);
        validator.checkLength(CurrentUserManager.getCurrentUser().getPassword(), passwordErrorImageView);

        Pattern p = Pattern.compile(activity.getString(R.string.password_regex));
        Matcher m = p.matcher(CurrentUserManager.getCurrentUser().getPassword());

        String confirmPassword = editable.toString();
        validator.checkPasswordValidity(m, confirmPassword);
    }

}