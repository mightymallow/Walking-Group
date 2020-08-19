package com.example.mashu.walkinggroup.controller.group.manager.activities;

import com.example.mashu.walkinggroup.model.Group;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.model.user.User;

import java.util.List;

/**
 * The SharedManagerFunctions class handles functionality
 * that is common to adding and removing users.
 */

public class SharedManagerFunctions {

    public static boolean userIsFoundInList(User userToFind, List<User> listOfUsers) {

        if(listOfUsers == null){
            return false;
        }

        for(User user: listOfUsers){

            if(user.getId().equals(userToFind.getId())){
                return true;
            }
        }

        return false;
    }

    public static boolean currentUserIsGroupLeader(Group clickedGroup) {

        return clickedGroup.getLeader() != null &&
               clickedGroup.getLeader().getId().equals(CurrentUserManager.getCurrentUser().getId());
    }

}