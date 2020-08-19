package com.example.mashu.walkinggroup.controller.shared.methods.account.monitoring.dialog.display;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.add.user.activity.AddUserFunctionality;
import com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.MonitorEnum;
import com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.RemoveUserFromList;
import com.example.mashu.walkinggroup.controller.shared.methods.BuildAndSetDialog;

/**
 * The AccountMonitoringDialog class consists of dialogs used in
 * references to the account monitoring activities.
 */

public class AccountMonitoringDialog {

    private Activity activity;
    private MonitorEnum monitorEnum;
    private int position;


    public AccountMonitoringDialog(Activity activity, MonitorEnum monitorEnum, int position) {

        this.activity = activity;
        this.monitorEnum = monitorEnum;
        this.position = position;
    }

    public AlertDialog buildRemoveListItemDialog(){

        BuildAndSetDialog display = new BuildAndSetDialog(activity);
        AlertDialog alertDialog = display.buildAndSetAlertDialogMessage(R.string.delete_item_question);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, activity.getString(R.string.positive_response),
            (DialogInterface dialog, int which) -> {

                RemoveUserFromList remove = new RemoveUserFromList(activity);
                remove.setMonitorEnum(monitorEnum);
                remove.setUserToRemovePosition(position);

                remove.removeUserAtPosition();
            });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, activity.getString(R.string.negative_response),
                (DialogInterface dialog, int which) -> dialog.dismiss());

        alertDialog.setCancelable(false);
        return alertDialog;
    }

    public AlertDialog buildAddListItemDialog(){

        BuildAndSetDialog display = new BuildAndSetDialog(activity);
        AlertDialog alertDialog = display.buildAndSetAlertDialogMessage(R.string.add_item_question);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, activity.getString(R.string.positive_response),
            (DialogInterface dialog, int which) -> {

                AddUserFunctionality add = new AddUserFunctionality(activity, monitorEnum, position);
                add.addUserFromUsersDisplayed();
            });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, activity.getString(R.string.negative_response),
                (DialogInterface dialog, int which) -> dialog.dismiss());

        alertDialog.setCancelable(false);
        return alertDialog;
    }

}