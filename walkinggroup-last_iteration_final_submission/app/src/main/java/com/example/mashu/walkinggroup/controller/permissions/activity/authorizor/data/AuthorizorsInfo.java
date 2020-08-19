package com.example.mashu.walkinggroup.controller.permissions.activity.authorizor.data;

import android.app.Activity;
import android.widget.ListView;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.permissions.activity.PermissionsListAdapter;
import com.example.mashu.walkinggroup.model.user.User;
import com.example.mashu.walkinggroup.model.permission.requests.PermissionRequest;
import com.example.mashu.walkinggroup.model.permission.requests.PermissionStatus;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * The AuthorizorsInfo class handles acquiring all the information
 * regarding the authorizors for permission requests for the current user.
 */

public class AuthorizorsInfo {

    private Activity activity;
    private List<PermissionRequest> approvedPermissionRequests;
    private List<PermissionRequest> deniedPermissionRequests;
    private List<PermissionRequest> pendingPermissionRequests;

    private static List<User> allUsers;


    public AuthorizorsInfo(Activity activity, List<PermissionRequest> approvedPermissionRequests,
                           List<PermissionRequest> deniedPermissionRequests,
                           List<PermissionRequest> pendingPermissionRequests) {

        this.activity = activity;
        this.approvedPermissionRequests = approvedPermissionRequests;
        this.deniedPermissionRequests = deniedPermissionRequests;
        this.pendingPermissionRequests = pendingPermissionRequests;
    }

    public static String getNameOrEmailOfUserWithMatchingID(Long matchingID) {

        for(User user: allUsers){

            if(user.getId().equals(matchingID)){
                return returnUserNameOrEmail(user);
            }
        }

        return "";
    }

    private static String returnUserNameOrEmail(User user) {

        if(user.getName().isEmpty()){
            return user.getEmail();
        } else {
            return user.getName();
        }
    }

    public void getData(){
        getAllUsersFromServer();
    }

    private void getAllUsersFromServer() {

        Call<List<User>> caller = ProxyManager.getProxy(activity).getUsers();
        ProxyBuilder.callProxy(activity, caller, this::onAllUsersReceivedFromServer);
    }

    private void onAllUsersReceivedFromServer(List<User> allUsersReceivedFromServer) {

        allUsers = allUsersReceivedFromServer;

        HandlePermissionRequests handle = new HandlePermissionRequests(approvedPermissionRequests, PermissionStatus.APPROVED, activity);
        handle.goThroughPermissionRequests();

        handle = new HandlePermissionRequests(deniedPermissionRequests, PermissionStatus.DENIED, activity);
        handle.goThroughPermissionRequests();

        handle = new HandlePermissionRequests(pendingPermissionRequests, PermissionStatus.PENDING, activity);
        handle.goThroughPermissionRequests();

        List<PermissionRequest> relevantPermissionRequests = new ArrayList<>();
        relevantPermissionRequests.addAll(approvedPermissionRequests);
        relevantPermissionRequests.addAll(deniedPermissionRequests);
        relevantPermissionRequests.addAll(pendingPermissionRequests);

        populatePermissionRequestsListView(relevantPermissionRequests);
    }

    private void populatePermissionRequestsListView(List<PermissionRequest> relevantPermissionRequests) {

        PermissionsListAdapter adapter = new PermissionsListAdapter(activity, relevantPermissionRequests);
        ListView permissionsList = activity.findViewById(R.id.permissions_requests_listView);
        permissionsList.setAdapter(adapter);
    }

}