package com.example.mashu.walkinggroup.controller.map.activity;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.map.activity.help.methods.DisplayHelp;
import com.example.mashu.walkinggroup.controller.map.activity.invisible.icon.setup.InvisibleIconSetup;
import com.example.mashu.walkinggroup.controller.map.activity.location.methods.CurrentDeviceLocation;
import com.example.mashu.walkinggroup.controller.map.activity.location.methods.CurrentLocationSettings;
import com.example.mashu.walkinggroup.controller.map.activity.location.methods.location.search.bar.SetupSearchBar;
import com.example.mashu.walkinggroup.controller.map.activity.shared.methods.MoveCamera;
import com.example.mashu.walkinggroup.controller.map.activity.shared.methods.UpdateOriginAndDestination;
import com.example.mashu.walkinggroup.controller.map.activity.walking.group.methods.CreateGroupPermissionChecker;
import com.example.mashu.walkinggroup.controller.map.activity.walking.group.methods.DisplayWalkingGroups;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.GoogleMap;

/**
 * The OnMapReady class handles executing
 * the functionality required once the map
 * is ready.
 */

public class OnMapReady {

    private Activity activity;
    private GoogleMap googleMap;


    public OnMapReady(Activity activity, GoogleMap googleMap) {

        this.activity = activity;
        this.googleMap = googleMap;
    }

    public void execute(){

        CurrentDeviceLocation locator = new CurrentDeviceLocation(activity, googleMap);
        locator.getLocation();

        SetupSearchBar setupSearchBar = new SetupSearchBar(activity, googleMap);
        setupSearchBar.setupSearchBar();

        ImageView mapActivityHelpImageView = activity.findViewById(R.id.map_activity_help_imageView);
        DisplayHelp.setupHelpButton((FragmentActivity) activity, mapActivityHelpImageView, R.string.map_activity_help_dialog_message);

        DisplayWalkingGroups displayWalkingGroups = new DisplayWalkingGroups(googleMap, activity);
        displayWalkingGroups.getWalkingGroupsToShow();

        InvisibleIconSetup setup = new InvisibleIconSetup(activity);
        setup.setupInvisibleIcons();

        CreateGroupPermissionChecker checker = new CreateGroupPermissionChecker(activity);
        checker.checkForPermissions();
    }

    public void fillPlaceAsOriginOrDestinationAfterPlacePick(Place selectedPlace){

        EditText originEditText = activity.findViewById(R.id.origin_editText);
        EditText destinationEditText = activity.findViewById(R.id.destination_editText);

        if(originEditText.getText().toString().isEmpty()){
            fillPlaceAsOrigin(selectedPlace, originEditText);
        } else {
            fillPlaceAsDestination(selectedPlace, destinationEditText);
        }
    }

    private void fillPlaceAsOrigin(Place selectedPlace, EditText originEditText) {

        UpdateOriginAndDestination.updateOrigin(activity, selectedPlace.getLatLng(), googleMap);
        originEditText.setText(selectedPlace.getName());
        MoveCamera.move(selectedPlace.getLatLng(), googleMap);
    }

    private void fillPlaceAsDestination(Place selectedPlace, EditText destinationEditText) {

        UpdateOriginAndDestination.updateDestination(activity, selectedPlace.getLatLng(), googleMap);
        destinationEditText.setText(selectedPlace.getName());
        MoveCamera.moveCameraToIncludeWalkingGroupPath(googleMap);
    }

}