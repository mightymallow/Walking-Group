package com.example.mashu.walkinggroup.controller.dashboard.map.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.dashboard.map.activity.placer.placing.process.CarryOutPlacement;
import com.example.mashu.walkinggroup.controller.map.activity.help.methods.DisplayHelp;
import com.example.mashu.walkinggroup.controller.map.activity.location.methods.CurrentDeviceLocation;
import com.example.mashu.walkinggroup.controller.map.activity.location.methods.CurrentLocationSettings;
import com.example.mashu.walkinggroup.controller.map.activity.location.methods.LocationPermission;
import com.example.mashu.walkinggroup.controller.dashboard.map.activity.placer.UserFinder;
import com.example.mashu.walkinggroup.controller.map.activity.shared.methods.EvaluatePermissionResult;
import com.example.mashu.walkinggroup.controller.shared.methods.CustomToolbar;
import com.example.mashu.walkinggroup.controller.shared.methods.dashboard.marker.dialog.display.MarkerDisplay;
import com.example.mashu.walkinggroup.controller.toast.ToastDisplay;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;

/**
 * The DashboardMapActivity handles displaying the map on the
 * dashboard part of the application.
 */

public class DashboardMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private final static int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_map);

        CustomToolbar.setupCustomToolbar(DashboardMapActivity.this, R.id.dashboard_map_custom_toolbar, getString(R.string.dashboard_map_activity_subtitle));

        LocationPermission.isLocationPermissionGranted(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.dashboard_map);
        mapFragment.getMapAsync(DashboardMapActivity.this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {

            EvaluatePermissionResult evaluate = new EvaluatePermissionResult(DashboardMapActivity.this);
            evaluate.eval(permissions, grantResults);

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        CurrentDeviceLocation locator = new CurrentDeviceLocation(DashboardMapActivity.this, googleMap);
        locator.getLocation();

        CurrentLocationSettings locationSettings = new CurrentLocationSettings(DashboardMapActivity.this, googleMap);
        locationSettings.updateLocationUI();

        ImageView dashboardMapHelpImageView = findViewById(R.id.dashboard_map_help_imageView);
        DisplayHelp.setupHelpButton(DashboardMapActivity.this, dashboardMapHelpImageView,
                                    R.string.dashboard_map_help_dialog_message);

        UserFinder userFinder = new UserFinder(DashboardMapActivity.this, googleMap);
        userFinder.retrieveAllUsersToMarkFromServer();

        ImageView addMarkersImageView = findViewById(R.id.display_markers_imageView);
        addMarkersImageView.setOnClickListener((View view) -> {

            if(CarryOutPlacement.areAllMarkersUpdated()) {

                CarryOutPlacement.setAllMarkersUpdated(false);
                userFinder.retrieveAllUsersToMarkFromServer();

            } else {

                ToastDisplay display = new ToastDisplay(DashboardMapActivity.this);
                display.displayToast(R.string.markers_updating, Toast.LENGTH_SHORT);

            }

        });

        googleMap.setOnMarkerClickListener((Marker marker) -> {

            MarkerDisplay markerDisplay = new MarkerDisplay(DashboardMapActivity.this, marker.getTitle());
            markerDisplay.displayDashboardMarkerDialog();
            return true;
        });

    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, DashboardMapActivity.class);
    }

}