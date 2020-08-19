package com.example.mashu.walkinggroup.controller.map.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.ViewGroups;
import com.example.mashu.walkinggroup.controller.dashboard.map.activity.DashboardMapActivity;
import com.example.mashu.walkinggroup.controller.map.activity.shared.methods.EvaluatePermissionResult;
import com.example.mashu.walkinggroup.controller.map.activity.walking.group.methods.CreateGroup;
import com.example.mashu.walkinggroup.controller.map.activity.walking.group.methods.DisplayWalkingGroups;
import com.example.mashu.walkinggroup.controller.map.activity.walking.group.methods.GroupNameFragment;
import com.example.mashu.walkinggroup.controller.map.activity.location.methods.LocationPermission;
import com.example.mashu.walkinggroup.controller.shared.methods.CustomToolbar;
import com.example.mashu.walkinggroup.controller.toast.ToastDisplay;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

/**
 * The MapActivity class handles all that entails displaying and using the map
 * for the application.
 */

public class MapActivity extends AppCompatActivity
                         implements OnMapReadyCallback, GroupNameFragment.ButtonPositiveListener {

    private final static int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private final static int PLACE_PICKER_REQUEST = 1;

    private GoogleMap mMap;
    private OnMapReady mapReady;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        CustomToolbar.setupCustomToolbar(MapActivity.this, R.id.map_custom_toolbar, getString(R.string.map_activity_create_subtitle));

        LocationPermission.isLocationPermissionGranted(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);

        viewGroups();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {

            EvaluatePermissionResult evaluate = new EvaluatePermissionResult(MapActivity.this);
            evaluate.eval(permissions, grantResults);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mapReady = new OnMapReady(MapActivity.this, googleMap);
        mapReady.execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK) {

            Place selectedPlace = PlacePicker.getPlace(this, data);
            mapReady.fillPlaceAsOriginOrDestinationAfterPlacePick(selectedPlace);
        }
    }

    @Override
    public void onButtonPositiveClick(String groupName) {

        if(groupName.isEmpty()){

            ToastDisplay display = new ToastDisplay(MapActivity.this);
            display.displayToast(R.string.group_name_requirement, Toast.LENGTH_SHORT);

        } else {

            CreateGroup createGroup = new CreateGroup(MapActivity.this, mMap);
            createGroup.addNewGroup(groupName);

        }
    }

    public static void startPlacePickerActivity(Activity activity, PlacePicker.IntentBuilder builder){

        try {
            activity.startActivityForResult(builder.build(activity), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }


    private void viewGroups() {

        ImageButton button = findViewById(R.id.view_groups_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(ViewGroups.makeIntent(MapActivity.this));

            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, MapActivity.class);
    }

    @Override
    public void onBackPressed() {

        DisplayWalkingGroups.setIsZoomApplicableToFitAllWalkingGroups(false);
        super.onBackPressed();
    }

}