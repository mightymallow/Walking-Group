package com.example.mashu.walkinggroup.controller.newuser.activity.validate.inputs;

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
 * The ValidateEmailWithRegex class handles validating
 * email inputs with regex.
 */

public class ValidateEmailWithRegex {

    private static boolean correctInput;
    private static boolean textChanged;
    private static String mostRecentCorrectInput;

    private Activity activity;
    private ValidateUserInputs validator;


    public ValidateEmailWithRegex(Activity activity) {
        this.activity = activity;
        this.validator = new ValidateUserInputs(activity);
    }

    public static boolean isInputCorrect(){
        return correctInput;
    }

    public static void setCorrectInput(boolean correctInput) {
        ValidateEmailWithRegex.correctInput = correctInput;
    }

    public static boolean isTextChanged(){
        return textChanged;
    }

    public static String getMostRecentCorrectInput() {
        return mostRecentCorrectInput;
    }

    public static void setMostRecentCorrectInput(String mostRecentCorrectInput) {
        ValidateEmailWithRegex.mostRecentCorrectInput = mostRecentCorrectInput;
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

                validationProcess(editable.toString(), inputCheckText, instructionsResourceID,
                                  regexResourceID, emailErrorImageView);
            }
        });
    }

    private void validationProcess(String currentEditTextInput, TextView instructionsTextView, int instructionsResourceID,
                                   int regexResourceID, ImageView errorIconImageView) {

        instructionsTextView.setText(instructionsResourceID);
        instructionsTextView.setVisibility(View.VISIBLE);

        validator.checkLength(currentEditTextInput, errorIconImageView);

        CompareInputWithRegex compareInputWithRegex =
                new CompareInputWithRegex(activity, currentEditTextInput, instructionsTextView, regexResourceID,
                                          errorIconImageView, RegexType.EMAIL);
        compareInputWithRegex.compareInputWithRegex();
    }

}