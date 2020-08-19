package com.example.mashu.walkinggroup.controller.group.manager.activities.remove;

import com.example.mashu.walkinggroup.model.Group;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.model.user.User;

import java.util.List;

/**
 * The RemoveCurrentUserIfClicked class handles removing
 * the current user if the current user was clicked and
 * is a member of the group that was clicked on the map.
 */

public class RemoveCurrentUserIfClicked {

    private List<User> validUsersToDisplay;
    private Group clickedGroup;
    private int position;


    public RemoveCurrentUserIfClicked(List<User> validUsersToDisplay, Group clickedGroup, int position) {

        this.validUsersToDisplay = validUsersToDisplay;
        this.clickedGroup = clickedGroup;
        this.position = position;
    }

    public void removeFromGroupIfNeeded() {

        if(CurrentUserManager.getCurrentUser().getId().equals(validUsersToDisplay.get(position).getId())){
            searchThroughGroupsCurrentUserIsAMemberOf();
        }
    }

    private void searchThroughGroupsCurrentUserIsAMemberOf() {

        for(Group group: CurrentUserManager.getCurrentUser().getMemberOfGroups()){

            if(isClickedGroup(group)){
                findClickedGroupInGroupsCurrentUserIsAMemberOf(group);
            }

            break;
        }
    }

    private boolean isClickedGroup(Group group) {
        return group.getId().equals(clickedGroup.getId());
    }

    private void findClickedGroupInGroupsCurrentUserIsAMemberOf(Group group) {

        List<Group> currentUserMemberOfGroups = CurrentUserManager.getCurrentUser().getMemberOfGroups();

        for(Group currentGroup: currentUserMemberOfGroups){

            if(group.getId().equals(currentGroup.getId())){

                currentUserMemberOfGroups.remove(group);
                CurrentUserManager.getCurrentUser().setMemberOfGroups(currentUserMemberOfGroups);
                break;
            }
        }
    }

}