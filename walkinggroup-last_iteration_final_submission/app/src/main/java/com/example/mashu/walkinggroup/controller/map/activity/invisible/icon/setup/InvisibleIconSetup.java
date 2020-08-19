package com.example.mashu.walkinggroup.controller.map.activity.invisible.icon.setup;

import android.app.Activity;

/**
 * The InvisibleIconSetup class handles setting up all the invisible icons
 * on the MapActivity screen.
 */

public class InvisibleIconSetup {

    private Activity activity;


    public InvisibleIconSetup(Activity activity) {
        this.activity = activity;
    }

    public void setupInvisibleIcons(){

        PlacePick placePicker = new PlacePick(activity);
        placePicker.setupPlacePicker();
    }

}