package com.example.mashu.walkinggroup.model.permission.requests;

import com.example.mashu.walkinggroup.model.Group;
import com.example.mashu.walkinggroup.model.IdItemBase;
import com.example.mashu.walkinggroup.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The PermissionRequest class stores information about the permission requests
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class PermissionRequest extends IdItemBase {

    private String action;
    private PermissionStatus status;
    private User userA;
    private User userB;
    private Group groupG;
    private User requestingUser;
    private Set<Authorizor> authorizors;
    private String message;
    private String authorizorContent;


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public PermissionStatus getStatus() {
        return status;
    }

    public void setStatus(PermissionStatus status) {
        this.status = status;
    }

    public User getUserA() {
        return userA;
    }

    public void setUserA(User userA) {
        this.userA = userA;
    }

    public User getUserB() {
        return userB;
    }

    public void setUserB(User userB) {
        this.userB = userB;
    }

    public Group getGroupG() {
        return groupG;
    }

    public void setGroupG(Group groupG) {
        this.groupG = groupG;
    }

    public User getRequestingUser() {
        return requestingUser;
    }

    public void setRequestingUser(User requestingUser) {
        this.requestingUser = requestingUser;
    }

    public Set<Authorizor> getAuthorizors() {
        return authorizors;
    }

    public void setAuthorizors(Set<Authorizor> authorizors) {
        this.authorizors = authorizors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String revealAuthorizorContent(){
        return  authorizorContent;
    }

    public void updateAuthorizorContent(String authorizorContent){
        this.authorizorContent = authorizorContent;
    }

    @Override
    public String toString() {
        return "PermissionRequest{" +
                "action='" + action + '\'' +
                ", status=" + status +
                ", userA=" + userA +
                ", userB=" + userB +
                ", groupG=" + groupG +
                ", requestingUser=" + requestingUser +
                ", authorizors=" + authorizors +
                ", message='" + message + '\'' +
                ", id=" + id +
                ", hasFullData=" + hasFullData +
                ", href='" + href + '\'' +
                '}';
    }

}