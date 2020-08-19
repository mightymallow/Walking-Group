package com.example.mashu.walkinggroup.controller.permissions.activity.authorizor.data;

import android.app.Activity;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.model.permission.requests.Authorizor;
import com.example.mashu.walkinggroup.model.permission.requests.PermissionRequest;
import com.example.mashu.walkinggroup.model.permission.requests.PermissionStatus;

/**
 * The AppendTextForApprovedOrDeniedPermission class is
 * used to build the authorizorContent for the
 * list of permissions with approved or denied status.
 */

public class AppendTextForApprovedOrDeniedPermission {

    private Activity activity;
    private PermissionStatus permissionStatus;
    private StringBuilder authorizorContent;
    private int userCount;

    public AppendTextForApprovedOrDeniedPermission(Activity activity, PermissionStatus permissionStatus) {

        this.activity = activity;
        this.permissionStatus = permissionStatus;
        this.authorizorContent = new StringBuilder();
        this.userCount = 0;
    }

    public StringBuilder append(PermissionRequest permissionRequest) {

        authorizorContent.append(activity.getString(R.string.more_information));

        for (Authorizor authorizingGroup : permissionRequest.getAuthorizors()) {

            String authorizorTitle = null;
            if(authorizingGroup.getWhoApprovedOrDenied() != null) {
                authorizorTitle = AuthorizorsInfo.getNameOrEmailOfUserWithMatchingID(authorizingGroup.getWhoApprovedOrDenied().getId());
            }

            if (permissionStatus == PermissionStatus.APPROVED && authorizorTitle != null) {

                addToAuthorizorContentForApprovedRequest(authorizorTitle, permissionRequest.getAuthorizors().size());

            } else if (permissionStatus == PermissionStatus.DENIED &&
                    authorizingGroup.getStatus() == PermissionStatus.DENIED && authorizorTitle != null) {

                addToAuthorizorContentForDeniedRequest(authorizorTitle, permissionRequest.getAuthorizors().size());

            }

            userCount++;
        }

        return authorizorContent;
    }

    private void addToAuthorizorContentForApprovedRequest(String nameOfAuthorizorFromGroup, int numAuthorizorGroups) {

        if (!nameOfAuthorizorFromGroup.isEmpty() && userCount != numAuthorizorGroups - 1) {
            authorizorContent.append(nameOfAuthorizorFromGroup).append(activity.getString(R.string.approved_this_request));
        } else if(!nameOfAuthorizorFromGroup.isEmpty()){
            authorizorContent.append(nameOfAuthorizorFromGroup).append(activity.getString(R.string.approved_this_request));
        }
    }

    private void addToAuthorizorContentForDeniedRequest(String nameOfAuthorizorFromGroup, int numAuthorizorGroups) {

        if (!nameOfAuthorizorFromGroup.isEmpty() && userCount != numAuthorizorGroups - 1) {
            authorizorContent.append(nameOfAuthorizorFromGroup).append(activity.getString(R.string.denied_this_request_whitespace));
        } else if (!nameOfAuthorizorFromGroup.isEmpty()){
            authorizorContent.append(nameOfAuthorizorFromGroup).append(activity.getString(R.string.denied_this_request));
        }
    }

}