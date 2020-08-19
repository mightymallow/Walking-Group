package com.example.mashu.walkinggroup.controller.shared.methods.edit.info.dialog.display;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.edit.info.activity.validate.birth.data.ValidateBirthMonthWithRegex;
import com.example.mashu.walkinggroup.controller.newuser.activity.validate.inputs.ValidateEmailWithRegex;
import com.example.mashu.walkinggroup.controller.shared.methods.BuildAndSetDialog;
import com.example.mashu.walkinggroup.model.user.User;

/**
 * The WrongDisplay class is used to
 * build and display the EmailWrong, BirthMonthWrong and BirthYearWrong dialogs.
 */

public class WrongDisplay {

    private Activity activity;
    private User userOfInterest;
    private BuildAndSetDialog display;


    public WrongDisplay(Activity activity, User userOfInterest, BuildAndSetDialog display) {

        this.activity = activity;
        this.userOfInterest = userOfInterest;
        this.display = display;
    }

    public AlertDialog buildBirthMonthWrongDialog(){

        AlertDialog alertDialog = display.buildAndSetAlertDialogMessage(R.string.birth_month_wrong);

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, activity.getString(R.string.ok_response),
            (DialogInterface dialog, int which) -> {

                EditText birthMonthEditText = activity.findViewById(R.id.current_birth_month_editText);
                birthMonthEditText.setText(activity.getString(R.string.integer, userOfInterest.getBirthMonth()));
                ValidateBirthMonthWithRegex.setCorrectInput(true);

                dialog.dismiss();
            });

        alertDialog.setCancelable(false);
        return alertDialog;
    }

    public AlertDialog buildBirthYearWrongDialog(){

        AlertDialog alertDialog = display.buildAndSetAlertDialogMessage(R.string.birth_month_wrong);

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, activity.getString(R.string.ok_response),
            (DialogInterface dialog, int which) -> {

                EditText birthMonthEditText = activity.findViewById(R.id.current_birth_month_editText);
                birthMonthEditText.setText(activity.getString(R.string.integer, userOfInterest.getBirthMonth()));
                ValidateBirthMonthWithRegex.setCorrectInput(true);

                dialog.dismiss();
            });

        alertDialog.setCancelable(false);
        return alertDialog;
    }

    public AlertDialog buildEmailWrongDialog(){

        AlertDialog alertDialog = display.buildAndSetAlertDialogMessage(R.string.email_wrong);

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, activity.getString(R.string.ok_response),
            (DialogInterface dialog, int which) -> {

                EditText emailEditText = activity.findViewById(R.id.current_email_editText);
                emailEditText.setText(userOfInterest.getEmail());
                ValidateEmailWithRegex.setCorrectInput(true);

                dialog.dismiss();
            });

        alertDialog.setCancelable(false);
        return alertDialog;
    }

}