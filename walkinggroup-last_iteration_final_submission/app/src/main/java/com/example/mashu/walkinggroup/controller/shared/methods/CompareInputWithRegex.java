package com.example.mashu.walkinggroup.controller.shared.methods;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.edit.info.activity.validate.birth.data.ValidateBirthMonthWithRegex;
import com.example.mashu.walkinggroup.controller.edit.info.activity.validate.birth.data.ValidateBirthYearWithRegex;
import com.example.mashu.walkinggroup.controller.newuser.activity.validate.inputs.ValidateEmailWithRegex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The CompareInputWithRegex class handles comparing
 * inputs with regex for validation.
 */

public class CompareInputWithRegex {

    private Activity activity;
    private String currentEditTextInput;
    private TextView instructionsTextView;
    private int regexResourceID;
    private ImageView errorIconImageView;
    private RegexType regexType;


    public CompareInputWithRegex(Activity activity, String currentEditTextInput, TextView instructionsTextView,
                                 int regexResourceID, ImageView errorIconImageView, RegexType regexType) {

        this.activity = activity;
        this.currentEditTextInput = currentEditTextInput;
        this.instructionsTextView = instructionsTextView;
        this.regexResourceID = regexResourceID;
        this.errorIconImageView = errorIconImageView;
        this.regexType = regexType;
    }

    public void compareInputWithRegex() {

        Pattern p = Pattern.compile(activity.getString(regexResourceID));
        Matcher m = p.matcher(currentEditTextInput);

        if (m.find()) {
            onRegexMatch(currentEditTextInput, instructionsTextView, errorIconImageView);
        } else {
            onRegexUnmatched(errorIconImageView);
        }
    }

    private void onRegexMatch(String currentEditTextInput, TextView instructionsTextView, ImageView errorIconImageView) {

        instructionsTextView.setVisibility(View.INVISIBLE);

        errorIconImageView.setVisibility(View.VISIBLE);
        errorIconImageView.setImageResource(R.drawable.correct_icon);

        if(regexType == RegexType.BIRTH_MONTH){

            ValidateBirthMonthWithRegex.setCorrectInput(true);

        } else if (regexType == RegexType.BIRTH_YEAR) {

            ValidateBirthYearWithRegex.setCorrectInput(true);

        } else if(regexType == RegexType.EMAIL){

            ValidateEmailWithRegex.setCorrectInput(true);
            ValidateEmailWithRegex.setMostRecentCorrectInput(currentEditTextInput);
            return;

        }

        if(!currentEditTextInput.isEmpty() && regexType == RegexType.BIRTH_MONTH) {
            ValidateBirthMonthWithRegex.setMostRecentCorrectInput(Integer.parseInt(currentEditTextInput));
        } else if(!currentEditTextInput.isEmpty() && regexType == RegexType.BIRTH_YEAR){
            ValidateBirthYearWithRegex.setMostRecentCorrectInput(Integer.parseInt(currentEditTextInput));
        }
    }

    private void onRegexUnmatched(ImageView errorIconImageView) {

        errorIconImageView.setVisibility(View.VISIBLE);
        errorIconImageView.setImageResource(R.drawable.error_icon);

        if(regexType == RegexType.BIRTH_MONTH) {
            ValidateBirthMonthWithRegex.setCorrectInput(false);
        } else if(regexType == RegexType.BIRTH_YEAR) {
            ValidateBirthYearWithRegex.setCorrectInput(false);
        } else if(regexType == RegexType.EMAIL){
            ValidateEmailWithRegex.setCorrectInput(false);
        }
    }

}