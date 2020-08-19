package com.example.mashu.walkinggroup.controller.group.manager.activities.remove;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.AccountMonitorListAdapter;
import com.example.mashu.walkinggroup.controller.group.manager.activities.PendingGroupMemberChecker;
import com.example.mashu.walkinggroup.model.Group;
import com.example.mashu.walkinggroup.model.user.User;

import java.util.List;

/**
 * The ComposeListOfUsersToRemove class handles
 * building the listview for the RemoveFromGroupActivity.
 */

public class ComposeListOfUsersToRemove {

    private Activity activity;
    private Group clickedGroup;
    private List<User> validUsersToDisplay;
    private List<User> membersUsers;


    public ComposeListOfUsersToRemove(Activity activity, Group clickedGroup, List<User> validUsersToDisplay, List<User> membersUsers) {

        this.activity = activity;
        this.clickedGroup = clickedGroup;
        this.validUsersToDisplay = validUsersToDisplay;
        this.membersUsers = membersUsers;
    }

    public void compose(){

        ListView removeFromGroupListView = activity.findViewById(R.id.remove_from_group_listView);
        updateListViewDisplay();

        removeFromGroupListView.setOnItemClickListener(
            (AdapterView<?> parent, View view, int position, long id) -> {

                PendingGroupMemberChecker pendingGroupMemberChecker =
                        new PendingGroupMemberChecker(activity, clickedGroup, position,
                                                      membersUsers, validUsersToDisplay, true);
                pendingGroupMemberChecker.checker();
            }
        );
    }

    private void updateListViewDisplay() {

        ListView removeFromGroupListView = activity.findViewById(R.id.remove_from_group_listView);

        ArrayAdapter<User> adapter = new AccountMonitorListAdapter(activity, validUsersToDisplay);
        AccountMonitorListAdapter.populateListView(removeFromGroupListView, adapter);
    }

}