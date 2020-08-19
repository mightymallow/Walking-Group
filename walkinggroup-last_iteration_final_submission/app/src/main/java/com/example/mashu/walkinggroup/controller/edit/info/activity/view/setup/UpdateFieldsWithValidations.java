package com.example.mashu.walkinggroup.controller.edit.info.activity.view.setup;

import android.app.Activity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.edit.info.activity.validate.birth.data.ValidateBirthMonthWithRegex;
import com.example.mashu.walkinggroup.controller.edit.info.activity.validate.birth.data.ValidateBirthYearWithRegex;
import com.example.mashu.walkinggroup.controller.newuser.activity.validate.inputs.ValidateEmailWithRegex;
import com.example.mashu.walkinggroup.model.user.User;

/**
 * The UpdateFieldsWithValidation class handles updating
 * all the fields which require validation.
 */

public class UpdateFieldsWithValidations {

    private Activity activity;
    private User userOfInterest;


    public UpdateFieldsWithValidations(Activity activity, User userOfInterest) {
        this.activity = activity;
        this.userOfInterest = userOfInterest;
    }

    public void updateFields(){

        updateNameInfo();
        updateEmailInfo();
        updateBirthYearInfo();
        updateBirthMonthInfo();
    }

    private void updateNameInfo() {

        EditText usernameEditText = activity.findViewById(R.id.current_user_name_editText);
        usernameEditText.setText(userOfInterest.getName());
    }

    private void updateEmailInfo() {

        EditText emailEditText = activity.findViewById(R.id.current_email_editText);
        emailEditText.setText(userOfInterest.getEmail());

        TextView checkTextView = activity.findViewById(R.id.current_check_textView);
        ImageView emailErrorImageView = activity.findViewById(R.id.current_email_check_imageView);

        addEmailValidation(emailEditText, checkTextView, emailErrorImageView);
    }

    private void addEmailValidation(EditText emailEditText, TextView checkTextView, ImageView emailErrorImageView) {

        ValidateEmailWithRegex emailValidator = new ValidateEmailWithRegex(activity);
        emailValidator.addInputValidation(emailEditText, checkTextView, emailErrorImageView,
                                          R.string.email_error_text, R.string.email_regex);
    }

    private void updateBirthYearInfo() {

        EditText birthYearEditText = activity.findViewById(R.id.current_birth_year_editText);

        if(userOfInterest.getBirthYear() != null) {
            birthYearEditText.setText(activity.getString(R.string.integer, userOfInterest.getBirthYear()));
        }

        TextView checkTextView = activity.findViewById(R.id.current_check_textView);
        ImageView birthYearErrorImageView = activity.findViewById(R.id.current_birth_year_check_imageView);

        addBirthYearValidation(checkTextView, birthYearEditText, birthYearErrorImageView);
    }

    private void addBirthYearValidation(TextView checkTextView, EditText birthYearEditText, ImageView birthYearErrorImageView) {

        ValidateBirthYearWithRegex birthYearValidator = new ValidateBirthYearWithRegex(activity);
        birthYearValidator.addInputValidation(birthYearEditText, checkTextView, birthYearErrorImageView,
                                              R.string.birth_year_error_text, R.string.birth_year_regex);
    }

    private void updateBirthMonthInfo() {

        EditText birthMonthEditText = activity.findViewById(R.id.current_birth_month_editText);

        if(userOfInterest.getBirthMonth() != null) {
            birthMonthEditText.setText(activity.getString(R.string.integer, userOfInterest.getBirthMonth()));
        }

        TextView checkTextView = activity.findViewById(R.id.current_check_textView);
        ImageView birthMonthErrorImageView = activity.findViewById(R.id.current_birth_month_check_imageView);

        addBirthMonthValidation(checkTextView, birthMonthEditText, birthMonthErrorImageView);
    }

    private void addBirthMonthValidation(TextView checkTextView, EditText birthMonthEditText,
                                         ImageView birthMonthErrorImageView) {

        ValidateBirthMonthWithRegex birthMonthValidator = new ValidateBirthMonthWithRegex(activity);
        birthMonthValidator.addInputValidation(birthMonthEditText, checkTextView, birthMonthErrorImageView,
                                               R.string.birth_month_error_text, R.string.birth_month_regex);
    }

}