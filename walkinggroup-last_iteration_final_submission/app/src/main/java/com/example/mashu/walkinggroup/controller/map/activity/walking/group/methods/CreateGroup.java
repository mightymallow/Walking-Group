package com.example.mashu.walkinggroup.controller.map.activity.walking.group.methods;

import android.app.Activity;
import com.example.mashu.walkinggroup.controller.map.activity.shared.methods.UpdateOriginAndDestination;
import com.example.mashu.walkinggroup.controller.toast.ToastDisplay;
import com.example.mashu.walkinggroup.model.Group;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;
import com.google.android.gms.maps.GoogleMap;

import retrofit2.Call;

/**
 * The CreateGroupClass handles creating a new group
 * from the map activity.
 */
public class CreateGroup {

    private Activity activity;
    private GoogleMap googleMap;
    private ToastDisplay display;


    public CreateGroup(Activity activity, GoogleMap googleMap) {

        this.activity = activity;
        this.googleMap = googleMap;
        this.display = new ToastDisplay(activity);
    }

    public void addNewGroup(String groupName){

        display.displayRequestSentToMakeGroup(groupName);

        Group.getCurrentGroup().setLeader(CurrentUserManager.getCurrentUser());
        Group.getCurrentGroup().setGroupDescription(groupName);

        createGroupOnServer();
    }

    private void createGroupOnServer() {

        double originLatitude = UpdateOriginAndDestination.getOrigin().latitude;
        double destinationLatitude = UpdateOriginAndDestination.getDestination().latitude;
        Group.getCurrentGroup().setRouteLatArray(new double[] {originLatitude, destinationLatitude});

        double originLongitude = UpdateOriginAndDestination.getOrigin().longitude;
        double destinationLongitude = UpdateOriginAndDestination.getDestination().longitude;
        Group.getCurrentGroup().setRouteLngArray(new double[] {originLongitude, destinationLongitude});

        Call<Group> caller = ProxyManager.getProxy(activity).createGroup(Group.getCurrentGroup());
        ProxyBuilder.callProxy(activity, caller, this::onGroupCreated);
    }

    private void onGroupCreated(Group createdGroup) {

        String groupDescription = createdGroup.getGroupDescription();

        ToastDisplay display = new ToastDisplay(activity);
        display.displayGroupRelatedToast(groupDescription, true);

        googleMap.clear();

        DisplayWalkingGroups displayWalkingGroups = new DisplayWalkingGroups(googleMap, activity);
        displayWalkingGroups.getWalkingGroupsToShow();
    }

}