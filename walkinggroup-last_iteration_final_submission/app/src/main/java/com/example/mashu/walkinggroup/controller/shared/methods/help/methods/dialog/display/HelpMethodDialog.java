package com.example.mashu.walkinggroup.controller.shared.methods.help.methods.dialog.display;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.map.activity.walking.group.methods.GroupNameFragment;
import com.example.mashu.walkinggroup.controller.shared.methods.BuildAndSetDialog;

/**
 * The HelpMethodDialog class consists of dialogs that are required for
 * the help screen functionality on the map activity screen.
 */
public class HelpMethodDialog {

    private FragmentActivity activity;


    public HelpMethodDialog(FragmentActivity activity) {
        this.activity = activity;
    }

    public void buildGroupNameDialog(){

        FragmentManager manager = activity.getSupportFragmentManager();
        GroupNameFragment dialog = new GroupNameFragment();
        dialog.show(manager, activity.getString(R.string.group_name_dialog));
    }

    public void buildHelpDialog(int messageResourceID){

        BuildAndSetDialog display = new BuildAndSetDialog(activity);
        AlertDialog alertDialog = display.buildAndSetAlertDialogMessage(messageResourceID);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, activity.getString(R.string.help_positive_response),
            (DialogInterface dialog, int which) -> alertDialog.dismiss());

        alertDialog.show();
    }

}