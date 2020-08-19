package com.example.mashu.walkinggroup.controller.edit.info.activity;

import android.app.Activity;

import com.example.mashu.walkinggroup.controller.edit.info.activity.validate.birth.data.ValidateBirthMonthWithRegex;
import com.example.mashu.walkinggroup.controller.edit.info.activity.validate.birth.data.ValidateBirthYearWithRegex;
import com.example.mashu.walkinggroup.controller.newuser.activity.validate.inputs.ValidateEmailWithRegex;
import com.example.mashu.walkinggroup.controller.shared.methods.BuildAndSetDialog;
import com.example.mashu.walkinggroup.controller.shared.methods.edit.info.dialog.display.WrongDisplay;
import com.example.mashu.walkinggroup.controller.shared.methods.edit.info.dialog.display.EditInfoDialogDisplay;
import com.example.mashu.walkinggroup.model.user.User;

/**
 * The EnsureCorrectInputs class handles making sure
 * that the inputs when making edits are filled
 * appropriately.
 */

public class EnsureCorrectInputs {

    private EditInfoDialogDisplay display;
    private WrongDisplay wrongDisplay;


    public EnsureCorrectInputs(Activity activity, User userOfInterest) {

        this.display = new EditInfoDialogDisplay(activity, userOfInterest);
        this.wrongDisplay = new WrongDisplay(activity, userOfInterest, new BuildAndSetDialog(activity));
    }

    public void onSave(){
        ensureBirthMonthIsCorrect();
    }

    private void ensureBirthMonthIsCorrect() {

        if(!ValidateBirthMonthWithRegex.isInputCorrect() && ValidateBirthMonthWithRegex.isTextChanged()){
            wrongDisplay.buildBirthMonthWrongDialog().show();
        } else  {
            ensureBirthYearIsCorrect();
        }
    }

    private void ensureBirthYearIsCorrect(){

        if(!ValidateBirthYearWithRegex.isInputCorrect() && ValidateBirthYearWithRegex.isTextChanged()){
            wrongDisplay.buildBirthYearWrongDialog().show();
        } else  {
            ensureEmailInputIsCorrect();
        }
    }

    private void ensureEmailInputIsCorrect() {

        if(!ValidateEmailWithRegex.isInputCorrect() && ValidateEmailWithRegex.isTextChanged()){
            wrongDisplay.buildEmailWrongDialog().show();
        } else {
            display.buildSaveNewUserInfoDialog().show();
        }
    }

}