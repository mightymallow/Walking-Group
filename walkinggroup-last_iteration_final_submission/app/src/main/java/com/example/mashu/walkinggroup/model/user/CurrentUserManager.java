package com.example.mashu.walkinggroup.model.user;

/**
 * The CurrentUserManager class handles instantiating the current user
 * for a session of the app running.
 */

public class CurrentUserManager {

    private static User currentUser;


    public static User getCurrentUser() {

        if(currentUser == null){
            currentUser = new User();
        }

        return currentUser;
    }

    public static void setCurrentUser(User newCurrentUser){
        currentUser = newCurrentUser;
    }

}