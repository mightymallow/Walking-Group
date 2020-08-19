package com.example.mashu.walkinggroup.controller.shared.methods;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.group.manager.activities.add.ListPossibleUsersToAdd;
import com.example.mashu.walkinggroup.controller.group.manager.activities.remove.GroupUpdateAfterRemove;
import com.example.mashu.walkinggroup.model.Group;
import com.example.mashu.walkinggroup.model.user.User;

import java.util.List;

/**
 * The BuildAddAndRemoveUserFromGroupDialog class is
 * used to create dialogs involving adding
 * a user to a group or removing a user from
 * a group.
 */

public class BuildAddAndRemoveUserFromGroupDialog {

    private Activity activity;
    private BuildAndSetDialog buildAndSetDialog;


    public BuildAddAndRemoveUserFromGroupDialog(Activity activity, BuildAndSetDialog buildAndSetDialog) {

        this.activity = activity;
        this.buildAndSetDialog = buildAndSetDialog;
    }

    public AlertDialog buildAddUserToGroupDialog(int position, List<User> membersUsers, Group clickedGroup,
                                                 List<User> validUsersToDisplay) {

        AlertDialog alertDialog = buildAndSetDialog.buildAndSetAlertDialogMessage(R.string.add_to_group_question);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, activity.getString(R.string.positive_response),
            (DialogInterface dialog, int which) -> {

                ListPossibleUsersToAdd listToAdd = new ListPossibleUsersToAdd(activity, membersUsers,
                        clickedGroup, validUsersToDisplay);
                listToAdd.addMemberToGroupIfNotAlreadyPresent(position);
            });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, activity.getString(R.string.negative_response),
                (DialogInterface dialog, int which) -> dialog.dismiss());

        alertDialog.setCancelable(false);
        return alertDialog;
    }

    public AlertDialog buildRemoveUserFromGroupDialog(int position, Group clickedGroup, List<User> validUsersToDisplay) {

        AlertDialog alertDialog = buildAndSetDialog.buildAndSetAlertDialogMessage(R.string.remove_from_group_question);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, activity.getString(R.string.positive_response),
            (DialogInterface dialog, int which) -> {

                GroupUpdateAfterRemove groupUpdateAfterRemove =
                        new GroupUpdateAfterRemove(activity, validUsersToDisplay, clickedGroup, position);
                groupUpdateAfterRemove.updateGroup();
            });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, activity.getString(R.string.negative_response),
                (DialogInterface dialog, int which) -> dialog.dismiss());

        alertDialog.setCancelable(false);
        return alertDialog;
    }

}