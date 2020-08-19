package com.example.mashu.walkinggroup.controller.shared.methods;

import com.example.mashu.walkinggroup.model.Group;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.model.user.User;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

/**
 * The CheckAccessGrantedToViewGroupMembers class handles
 * checking if the CurrentUser fits any of the criteria
 * required to view the clicked group's members.
 */

public class CheckAccessGrantedToViewGroupMembers {

    public void checkAccessGranted(List<Group> groupsReceived, Marker marker) {

        DialogDisplay.setAccessGranted(false);

        double latitude = marker.getPosition().latitude;
        double longitude = marker.getPosition().longitude;

        for (Group group: groupsReceived) {

            if (isCurrentLocationGroupDestination(latitude, longitude, group)) {
                checkIfAccessIsGrantedToCurrentUser(group);
            }

            if(DialogDisplay.isAccessGranted()){
                break;
            }
        }
    }

    private boolean isCurrentLocationGroupDestination(double latitude, double longitude, Group group) {
        return group.accessDestinationLatitude() == latitude &&
                group.accessDestinationLongitude() == longitude;
    }

    private void checkIfAccessIsGrantedToCurrentUser(Group group) {

        if (isCurrentUserTheGroupLeader(group)){
            DialogDisplay.setAccessGranted(true);
        } else if(isCurrentUserAGroupMember(group)){
            DialogDisplay.setAccessGranted(true);
        } else if(isCurrentUserMonitoringAGroupMember(group)){
            DialogDisplay.setAccessGranted(true);
        }
    }

    private boolean isCurrentUserTheGroupLeader(Group group) {

        return group.getLeader() != null &&
                CurrentUserManager.getCurrentUser().getId().equals(group.getLeader().getId());
    }

    private boolean isCurrentUserAGroupMember(Group group) {

        for (User groupMember: group.getMemberUsers()) {

            if (CurrentUserManager.getCurrentUser().getId().equals(groupMember.getId())) {
                return true;
            }
        }

        return false;
    }

    private boolean isCurrentUserMonitoringAGroupMember(Group group) {

        List<User> usersMonitoredByCurrentUser = CurrentUserManager.getCurrentUser().getMonitorsUsers();

        for (User monitoredUser : usersMonitoredByCurrentUser) {
            for (User groupMember : group.getMemberUsers()) {

                if (monitoredUser.getId().equals(groupMember.getId())) {
                    return true;
                }
            }
        }

        return false;
    }

}