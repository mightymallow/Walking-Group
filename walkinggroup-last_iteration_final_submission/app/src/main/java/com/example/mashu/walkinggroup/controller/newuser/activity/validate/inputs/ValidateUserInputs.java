package com.example.mashu.walkinggroup.controller.newuser.activity.validate.inputs;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;

import java.util.regex.Matcher;

/**
 * The ValidateUserInputs class handles validating user inputs during
 * login and sign up.
 */

public class ValidateUserInputs {

    private Activity activity;
    private static boolean correctPassword;


    public ValidateUserInputs(Activity activity) {
        this.activity = activity;
    }

    public static boolean isPasswordCorrect(){
        return correctPassword;
    }

    public void checkLength(String text, ImageView imageView){

        if (text != null && text.length() == 0) {
            imageView.setVisibility(View.INVISIBLE);
        } else {
            imageView.setVisibility(View.VISIBLE);
        }
    }

    public void checkPasswordValidity(Matcher m, String confirmPassword){

        ImageView confirmedPasswordErrorImageView = activity.findViewById(R.id.confirm_password_check);

        if (confirmPassword.equals(CurrentUserManager.getCurrentUser().getPassword()) && m.find()) {

            confirmedPasswordErrorImageView.setImageResource(R.drawable.correct_icon);
            correctPassword = true;

        } else {

            confirmedPasswordErrorImageView.setImageResource(R.drawable.error_icon);
            correctPassword = false;
        }

    }

}