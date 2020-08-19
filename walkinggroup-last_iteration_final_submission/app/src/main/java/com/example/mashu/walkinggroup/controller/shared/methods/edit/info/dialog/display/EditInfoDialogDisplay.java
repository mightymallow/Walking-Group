package com.example.mashu.walkinggroup.controller.shared.methods.edit.info.dialog.display;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.edit.info.activity.SaveBtnClickListener;
import com.example.mashu.walkinggroup.controller.edit.info.activity.validate.birth.data.ValidateBirthMonthWithRegex;
import com.example.mashu.walkinggroup.controller.edit.info.activity.validate.birth.data.ValidateBirthYearWithRegex;
import com.example.mashu.walkinggroup.controller.newuser.activity.validate.inputs.ValidateEmailWithRegex;
import com.example.mashu.walkinggroup.controller.shared.methods.BuildAndSetDialog;
import com.example.mashu.walkinggroup.model.user.User;

/**
 * The EditInfoDialogDisplay class is used to display any dialogs relevant
 * to the EditInfo Activity.
 */

public class EditInfoDialogDisplay {

    private static final int DEFAULT_BIRTH_MONTH = 0;
    private static final int DEFAULT_BIRTH_YEAR = -1;

    private Activity activity;
    private User userOfInterest;
    private BuildAndSetDialog display;


    public EditInfoDialogDisplay(Activity activity, User userOfInterest) {

        this.activity = activity;
        this.userOfInterest = userOfInterest;
        this.display = new BuildAndSetDialog(activity);
    }

    public AlertDialog buildSaveNewUserInfoDialog(){

        AlertDialog alertDialog = display.buildAndSetAlertDialogMessage(R.string.save_new_info);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, activity.getString(R.string.positive_response),
            (DialogInterface dialog, int which) -> {

                if(ValidateEmailWithRegex.getMostRecentCorrectInput() != null) {
                    userOfInterest.setEmail(ValidateEmailWithRegex.getMostRecentCorrectInput());
                }

                if(ValidateBirthYearWithRegex.getMostRecentCorrectInput() != DEFAULT_BIRTH_YEAR) {
                    userOfInterest.setBirthYear(ValidateBirthYearWithRegex.getMostRecentCorrectInput());
                }

                if(ValidateBirthMonthWithRegex.getMostRecentCorrectInput() != DEFAULT_BIRTH_MONTH) {
                    userOfInterest.setBirthMonth(ValidateBirthMonthWithRegex.getMostRecentCorrectInput());
                }

                SaveBtnClickListener listener = new SaveBtnClickListener(activity, userOfInterest);
                listener.updateUserOfInterestInfo();
            });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, activity.getString(R.string.negative_response),
            (DialogInterface dialog, int which) -> {

                dialog.dismiss();
                activity.finish();
            });

        alertDialog.setCancelable(false);
        return alertDialog;
    }

}