package com.example.mashu.walkinggroup.controller.account.monitoring.activities.monitors.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mashu.walkinggroup.controller.edit.info.activity.EditInfoActivity;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.model.user.User;

import java.util.List;

/**
 * The EditUser class is responsible for allowing
 * the current user to view and edit a user that he/she
 * monitors.
 */

public class EditUser {

    private Activity activity;
    private static User userToEdit;
    private static int position;


    public EditUser(Activity activity) {
        this.activity = activity;
    }

    public void onListItemClick(ListView listView){

        listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {

            EditUser.position = position;
            userToEdit = CurrentUserManager.getCurrentUser().getMonitorsUsers().get(position);

            Intent intent = EditInfoActivity.makeIntent(activity.getApplicationContext(), userToEdit.getId(), true);
            MonitorsActivity.startActivityForResult(activity, intent);
        });
    }

    public static void updateMonitorsUsers() {

        List<User> monitorsUsers = CurrentUserManager.getCurrentUser().getMonitorsUsers();
        monitorsUsers.remove(position);
        monitorsUsers.add(position, userToEdit);

        CurrentUserManager.getCurrentUser().setMonitorsUsers(monitorsUsers);
    }

}