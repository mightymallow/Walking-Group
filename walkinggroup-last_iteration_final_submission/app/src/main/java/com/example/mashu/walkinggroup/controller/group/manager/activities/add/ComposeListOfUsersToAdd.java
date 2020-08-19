package com.example.mashu.walkinggroup.controller.group.manager.activities.add;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.AccountMonitorListAdapter;
import com.example.mashu.walkinggroup.controller.group.manager.activities.PendingGroupMemberChecker;
import com.example.mashu.walkinggroup.controller.group.manager.activities.SharedManagerFunctions;
import com.example.mashu.walkinggroup.model.Group;
import com.example.mashu.walkinggroup.model.permission.requests.PermissionRequest;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.model.user.User;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import java.util.List;

import retrofit2.Call;

/**
 * The ComposeListOfUsersToAdd class handles
 * building the listview for the AddToGroupActivity.
 */

public class ComposeListOfUsersToAdd {

    private Activity activity;
    private List<User> membersUsers;
    private Group clickedGroup;
    private List<User> validUsersToDisplay;
    private List<PermissionRequest> pendingPermissionRequests;
    private boolean isCurrentUserPendingToLeadClickedGroup;


    public ComposeListOfUsersToAdd(Activity activity, List<User> membersUsers, Group clickedGroup,
                                   boolean isCurrentUserPendingToLeadClickedGroup,
                                   List<User> validUsersToDisplay, List<PermissionRequest> pendingPermissionRequests) {

        this.activity = activity;
        this.membersUsers = membersUsers;
        this.clickedGroup = clickedGroup;
        this.validUsersToDisplay = validUsersToDisplay;
        this.pendingPermissionRequests = pendingPermissionRequests;
        this.isCurrentUserPendingToLeadClickedGroup = isCurrentUserPendingToLeadClickedGroup;
    }

    public void compose(){

        Call<List<User>> caller = ProxyManager.getProxy(activity).getMonitorsUsers(CurrentUserManager.getCurrentUser().getId());
        ProxyBuilder.callProxy(activity, caller, this::onMonitorsUsersReceived);
    }

    private void onMonitorsUsersReceived(List<User> monitorsUsersReceived) {

        for (User monitoredUser: monitorsUsersReceived){

            if(monitoredUser.getId().equals(CurrentUserManager.getCurrentUser().getId()) &&
               isCurrentUserPendingToLeadClickedGroup){

                continue;

            }

            if(isMonitoredUserPendingToLeadTheClickedGroup(monitoredUser)){
                continue;
            }

            if(!SharedManagerFunctions.userIsFoundInList(monitoredUser, membersUsers) &&
                userIsNotGroupLeader(monitoredUser)){

                validUsersToDisplay.add(monitoredUser);

            }
        }

        ListView addToGroupListView = activity.findViewById(R.id.add_to_group_listView);

        updateListViewDisplay();

        addToGroupListView.setOnItemClickListener(
            (AdapterView<?> parent, View view, int position, long id) -> {

                PendingGroupMemberChecker pendingGroupMemberChecker =
                        new PendingGroupMemberChecker(activity, clickedGroup, position,
                                                      membersUsers, validUsersToDisplay, false);
                pendingGroupMemberChecker.checker();
            }
        );
    }

    private boolean isMonitoredUserPendingToLeadTheClickedGroup(User monitoredUser) {

        for(PermissionRequest permissionRequest: pendingPermissionRequests){

            if(!permissionRequest.getAction().equals("A_LEAD_GROUP")){
                continue;
            }

            if(permissionRequest.getUserA().getId().equals(monitoredUser.getId())){
                return true;
            }
        }

        return false;
    }

    private boolean userIsNotGroupLeader(User user) {
        return clickedGroup.getLeader() == null || !user.getId().equals(clickedGroup.getLeader().getId());
    }

    private void updateListViewDisplay() {

        ListView addToGroupListView = activity.findViewById(R.id.add_to_group_listView);

        ArrayAdapter<User> adapter = new AccountMonitorListAdapter(activity, validUsersToDisplay);
        AccountMonitorListAdapter.populateListView(addToGroupListView, adapter);
    }

}