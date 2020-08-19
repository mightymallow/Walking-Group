package com.example.mashu.walkinggroup.model.permission.requests;

import com.example.mashu.walkinggroup.model.user.User;

import java.util.Set;

/**
 * The Authorizor class defines the characteristics
 * of an authorizing group for a permission request.
 */

public class Authorizor {

    private Set<User> users;
    private PermissionStatus status;
    private User whoApprovedOrDenied;


    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public PermissionStatus getStatus() {
        return status;
    }

    public void setStatus(PermissionStatus status) {
        this.status = status;
    }

    public User getWhoApprovedOrDenied() {
        return whoApprovedOrDenied;
    }

    public void setWhoApprovedOrDenied(User whoApprovedOrDenied) {
        this.whoApprovedOrDenied = whoApprovedOrDenied;
    }

}