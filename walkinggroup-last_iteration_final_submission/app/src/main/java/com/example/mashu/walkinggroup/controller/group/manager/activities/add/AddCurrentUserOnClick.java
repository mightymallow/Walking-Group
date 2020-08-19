package com.example.mashu.walkinggroup.controller.group.manager.activities.add;

import com.example.mashu.walkinggroup.model.Group;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.model.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * The AddCurrentUserOnClick class handles adding the user
 * when the user has been clicked in the ListPossibleUsersToAdd class.
 */

public class AddCurrentUserOnClick {

    private List<User> validUsersToDisplay;
    private Group clickedGroup;
    private int position;


    public AddCurrentUserOnClick(List<User> validUsersToDisplay, Group clickedGroup, int position) {

        this.validUsersToDisplay = validUsersToDisplay;
        this.clickedGroup = clickedGroup;
        this.position = position;
    }

    public void add() {

        if(CurrentUserManager.getCurrentUser().getId().equals(validUsersToDisplay.get(position).getId())){

            List<Group> currentUserMemberOfGroups = CurrentUserManager.getCurrentUser().getMemberOfGroups();

            if(currentUserMemberOfGroups == null) {
                currentUserMemberOfGroups = new ArrayList<>();
                CurrentUserManager.getCurrentUser().setMemberOfGroups(currentUserMemberOfGroups);
            }

            currentUserMemberOfGroups.add(clickedGroup);
            CurrentUserManager.getCurrentUser().setMemberOfGroups(currentUserMemberOfGroups);
        }
    }

}