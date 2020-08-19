package com.example.mashu.walkinggroup.controller.edit.info.activity.validate.birth.data;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mashu.walkinggroup.controller.shared.methods.CompareInputWithRegex;
import com.example.mashu.walkinggroup.controller.shared.methods.RegexType;

/**
 * The ValidateBirthYearWithRegex class handles
 * validating the birth year user input with regex.
 */

public class ValidateBirthYearWithRegex {

    private Activity activity;
    private static boolean correctInput;
    private static boolean textChanged;
    private static int mostRecentCorrectInput;


    public ValidateBirthYearWithRegex(Activity activity) {
        this.activity = activity;
    }

    public static boolean isInputCorrect(){
        return correctInput;
    }

    public static void setCorrectInput(boolean correctInput) {
        ValidateBirthYearWithRegex.correctInput = correctInput;
    }

    public static boolean isTextChanged(){
        return textChanged;
    }

    public static int getMostRecentCorrectInput() {
        return mostRecentCorrectInput;
    }

    public static void setMostRecentCorrectInput(int mostRecentCorrectInput) {
        ValidateBirthYearWithRegex.mostRecentCorrectInput = mostRecentCorrectInput;
    }

    public void addInputValidation(EditText inputEditText, TextView inputCheckText, ImageView emailErrorImageView,
                                   int instructionsResourceID, int regexResourceID){

        inputEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {

                textChanged = true;

                validationProcess(editable.toString(), inputCheckText,
                                  instructionsResourceID, regexResourceID, emailErrorImageView);
            }
        });
    }

    private void validationProcess(String currentEditTextInput, TextView instructionsTextView, int instructionsResourceID,
                                   int regexResourceID, ImageView errorIconImageView) {

        instructionsTextView.setText(instructionsResourceID);
        instructionsTextView.setVisibility(View.VISIBLE);

        CompareInputWithRegex compare = new CompareInputWithRegex(activity, currentEditTextInput,
                                                                  instructionsTextView, regexResourceID,
                                                                  errorIconImageView, RegexType.BIRTH_YEAR);
        compare.compareInputWithRegex();
    }

}