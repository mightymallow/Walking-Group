package com.example.mashu.walkinggroup.controller.map.activity.walking.group.methods.setup.create.button.functionality;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.map.activity.shared.methods.UpdateOriginAndDestination;
import com.example.mashu.walkinggroup.controller.shared.methods.CustomToolbar;

/**
 * The Update class handles updates required in
 * the SetupCreateButton class.
 */

public class Update {

    private Activity activity;


    public Update(Activity activity) {
        this.activity = activity;
    }

    public void handleUpdates(){

        updateOrigin();
        updateDestination();
        updateToolbarSubtitle();
    }

    private void updateOrigin() {

        clearOriginEditText();
        UpdateOriginAndDestination.clearOriginMarker();
    }

    private void clearOriginEditText() {

        EditText originEditText = activity.findViewById(R.id.origin_editText);
        originEditText.setText("");
    }

    private void updateDestination() {

        clearDestinationEditText();
        UpdateOriginAndDestination.clearDestinationMarker();
    }

    private void clearDestinationEditText() {

        EditText destinationEditText = activity.findViewById(R.id.destination_editText);
        destinationEditText.setText("");
    }

    private void updateToolbarSubtitle() {

        CustomToolbar.updateToolbarSubTitle((AppCompatActivity) activity, R.id.map_custom_toolbar,
                                             activity.getString(R.string.map_activity_create_subtitle));
    }

}