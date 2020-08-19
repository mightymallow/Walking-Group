package com.example.mashu.walkinggroup.controller.shared.methods;

import com.example.mashu.walkinggroup.controller.edit.info.activity.CurrentUserTypeEnum;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.model.user.User;

/**
 * The DetermineUserType class is used to determine the
 * user type, either parent or child, of a given user.
 */

public class DetermineUserType {

    private User userOfInterest;


    public DetermineUserType(User userOfInterest) {
        this.userOfInterest = userOfInterest;
    }

    public void updateCurrentUserType() {

        if(currentUserIsMonitored()){
            userOfInterest.setUserType(CurrentUserTypeEnum.CHILD);
        } else {
            userOfInterest.setUserType(CurrentUserTypeEnum.PARENT);
        }

        updateCurrentUserIfNeeded();
    }

    private boolean currentUserIsMonitored() {
        return !userOfInterest.getMonitoredByUsers().isEmpty();
    }

    private void updateCurrentUserIfNeeded() {

        if(userOfInterest.getId().equals(CurrentUserManager.getCurrentUser().getId())){
            CurrentUserManager.setCurrentUser(userOfInterest);
        }
    }

}