package com.example.mashu.walkinggroup.controller.map.activity.invisible.icon.setup;

import android.app.Activity;
import android.widget.ImageView;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.map.activity.MapActivity;
import com.example.mashu.walkinggroup.controller.map.activity.location.methods.LocationPermission;
import com.google.android.gms.location.places.ui.PlacePicker;

/**
 * The PlacePick class handles setting up the
 * place picker for the MapActivity.
 */

public class PlacePick {

    private Activity activity;


    public PlacePick(Activity activity) {
        this.activity = activity;
    }

    public void setupPlacePicker() {

        ImageView imageViewPlacePicker = activity.findViewById(R.id.place_picker_imageView);
        imageViewPlacePicker.setOnClickListener(view -> showAllNearbyPlaces());
    }

    private void showAllNearbyPlaces() {

        if (LocationPermission.isLocationPermissionGranted()) {

            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            allowUserToPickAPlace(builder);
        }
    }

    private void allowUserToPickAPlace(com.google.android.gms.location.places.ui.PlacePicker.IntentBuilder builder) {
        MapActivity.startPlacePickerActivity(activity, builder);
    }

}