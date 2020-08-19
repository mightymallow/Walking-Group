package com.example.mashu.walkinggroup.controller.permissions.activity.authorizor.data;

import android.app.Activity;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.model.permission.requests.Authorizor;
import com.example.mashu.walkinggroup.model.permission.requests.PermissionRequest;
import com.example.mashu.walkinggroup.model.permission.requests.PermissionStatus;
import com.example.mashu.walkinggroup.model.user.User;

import java.util.Set;

/**
 * The AppendTextForPendingPermission class is
 * used to build the authorizorContent for the
 * list of permissions with pending status.
 */

public class AppendTextForPendingPermission {

    private Activity activity;
    private StringBuilder authorizorContent;
    private int authorizorGroupCount;
    private int userCount;


    public AppendTextForPendingPermission(Activity activity) {

        this.activity = activity;
        this.authorizorContent = new StringBuilder();
        this.authorizorGroupCount = 0;
        this.userCount = 0;
    }

    public StringBuilder append(PermissionRequest permissionRequest) {

        authorizorContent.append(activity.getString(R.string.more_information));

        for(Authorizor authorizorGroup: permissionRequest.getAuthorizors()){

            if(authorizorGroup.getStatus() == PermissionStatus.PENDING){
               decideOnWhichUserIsPending(authorizorGroup, permissionRequest.getAuthorizors().size());
            }

            authorizorGroupCount++;
        }

        return authorizorContent;
    }

    private void decideOnWhichUserIsPending(Authorizor authorizorGroup, int totalNumAuthorizorGroups) {

        Set<User> usersWhoCanRespond = authorizorGroup.getUsers();

        for(User user: usersWhoCanRespond){

            String authorizorTitle = AuthorizorsInfo.getNameOrEmailOfUserWithMatchingID(user.getId());

            if(!authorizorTitle.isEmpty() && userCount == usersWhoCanRespond.size() - 1 &&
                authorizorGroupCount == totalNumAuthorizorGroups - 1){

                authorizorContent.append(authorizorTitle).append(activity.getString(R.string.has_not_responded));

            } else if(!authorizorTitle.isEmpty()){

                authorizorContent.append(authorizorTitle).append(activity.getString(R.string.has_not_responded));

            }

            userCount++;
        }

        userCount = 0;
    }

}