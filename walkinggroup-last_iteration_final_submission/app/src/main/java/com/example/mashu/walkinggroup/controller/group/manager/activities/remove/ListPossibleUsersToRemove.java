package com.example.mashu.walkinggroup.controller.group.manager.activities.remove;

import android.app.Activity;

import com.example.mashu.walkinggroup.controller.group.manager.activities.SharedManagerFunctions;
import com.example.mashu.walkinggroup.model.Group;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.model.user.User;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import java.util.List;

import retrofit2.Call;

/**
 * The ListPossibleUsersToRemove class deals with populating the list of
 * possible users to remove in AddGroupActivity.
 */

public class ListPossibleUsersToRemove {

    private Activity activity;
    private Group clickedGroup;
    private List<User> validUsersToDisplay;
    private List<User> membersUsers;


    public ListPossibleUsersToRemove(Activity activity, List<User> membersUsers, Group clickedGroup,
                                     List<User> validUsersToDisplay) {

        this.activity = activity;
        this.membersUsers = membersUsers;
        this.clickedGroup = clickedGroup;
        this.validUsersToDisplay = validUsersToDisplay;
    }

    public void determineValidUsers(){

        if(clickedGroup.getLeader() != null && SharedManagerFunctions.currentUserIsGroupLeader(clickedGroup)){
            determineValidUserWithLeaderAsCurrentUser();
        } else {
            determineValidUserWithoutLeaderAsCurrentUser();
        }
    }

    private void determineValidUserWithLeaderAsCurrentUser() {

        Call<List<User>> caller = ProxyManager.getProxy(activity).getUsers();
        ProxyBuilder.callProxy(activity, caller, this::onAllUsersReceived);
    }

    private void onAllUsersReceived(List<User> allUsersReceived) {

        for(User userToFind: allUsersReceived){

            if(SharedManagerFunctions.userIsFoundInList(userToFind, membersUsers)){
                validUsersToDisplay.add(userToFind);
            }
        }

        ComposeListOfUsersToRemove composeListOfUsersToRemove =
                new ComposeListOfUsersToRemove(activity, clickedGroup, validUsersToDisplay, membersUsers);

        composeListOfUsersToRemove.compose();
    }

    private void determineValidUserWithoutLeaderAsCurrentUser() {

        if (SharedManagerFunctions.userIsFoundInList(CurrentUserManager.getCurrentUser(), membersUsers)) {
            validUsersToDisplay.add(CurrentUserManager.getCurrentUser());
        }

        Call<List<User>> caller = ProxyManager.getProxy(activity).getMonitorsUsers(CurrentUserManager.getCurrentUser().getId());
        ProxyBuilder.callProxy(activity, caller, this::onMonitorsUsersReceived);
    }

    private void onMonitorsUsersReceived(List<User> monitorsUsersReceived) {

        for (User user: monitorsUsersReceived){

            if(SharedManagerFunctions.userIsFoundInList(user, membersUsers)){
                validUsersToDisplay.add(user);
            }
        }

        ComposeListOfUsersToRemove composeListOfUsersToRemove =
                new ComposeListOfUsersToRemove(activity, clickedGroup, validUsersToDisplay, membersUsers);

        composeListOfUsersToRemove.compose();
    }

}