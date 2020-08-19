package com.example.mashu.walkinggroup.controller.map.activity.location.methods.location.search.bar;

import android.app.Activity;
import android.widget.EditText;

import com.example.mashu.walkinggroup.R;
import com.google.android.gms.maps.GoogleMap;

/**
 * The SetupSearchBar class handles setting up
 * the location search bar.
 */

public class SetupSearchBar {

    private Activity activity;
    private LocationSearchBar locationSearchBar;


    public SetupSearchBar(Activity activity, GoogleMap googleMap) {

        this.activity = activity;
        this.locationSearchBar = new LocationSearchBar(activity, googleMap);
    }

    public void setupSearchBar() {
        setupOriginAndDestinationEditTexts();
    }

    private void setupOriginAndDestinationEditTexts() {

        OnEditProcess onEditProcess = new OnEditProcess(activity, locationSearchBar);

        EditText originEditText = activity.findViewById(R.id.origin_editText);
        originEditText.setOnEditorActionListener((textView, actionId, keyEvent) ->
                onEditProcess.onEdit(originEditText, actionId, keyEvent, true));

        EditText destinationEditText = activity.findViewById(R.id.destination_editText);
        destinationEditText.setOnEditorActionListener((textView, actionId, keyEvent) ->
                onEditProcess.onEdit(destinationEditText, actionId, keyEvent, false));
    }

}