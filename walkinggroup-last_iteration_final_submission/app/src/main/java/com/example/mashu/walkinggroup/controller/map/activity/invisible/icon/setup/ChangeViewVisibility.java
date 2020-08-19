package com.example.mashu.walkinggroup.controller.map.activity.invisible.icon.setup;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.shared.methods.AnimationBuilder;
import com.example.mashu.walkinggroup.controller.shared.methods.CustomToolbar;

/**
 * The ChangeViewVisibility class handles changing the visibility of
 * the views during transitions.
 */

public class ChangeViewVisibility {

    private Activity activity;


    public ChangeViewVisibility(Activity activity) {
        this.activity = activity;
    }

    public void setupTransitionsForViewWalkingGroupScreen(){

        updateCreateGroupImageViewVisibility(View.VISIBLE);
        updateLinearLayoutWithEditTextsVisibility(View.GONE);
        updatePlacePickerImageViewVisibility(View.GONE);
        updateToolbarSubtitle(R.string.map_activity_view_subtitle);
    }

    private void updateCreateGroupImageViewVisibility(int visibility) {

        ImageView createGroupImageView = activity.findViewById(R.id.create_group_imageView);
        createGroupImageView.setVisibility(visibility);

        fadeInCreateGroupImageView(createGroupImageView);
    }

    private void fadeInCreateGroupImageView(ImageView createGroupImageView) {

        AnimationBuilder builder = new AnimationBuilder(activity);
        createGroupImageView.setAnimation(builder.fadeInCreateGroupImageView());
    }

    private void updateLinearLayoutWithEditTextsVisibility(int visibility) {

        LinearLayout editTextsLinearLayout = activity.findViewById(R.id.map_editTexts_linear_layout);
        editTextsLinearLayout.setVisibility(visibility);
    }

    private void updatePlacePickerImageViewVisibility(int visibility) {

        ImageView placePickerImageView = activity.findViewById(R.id.place_picker_imageView);
        placePickerImageView.setVisibility(visibility);
    }

    private void updateToolbarSubtitle(int subtitleResourceID) {

        CustomToolbar.updateToolbarSubTitle((AppCompatActivity) activity, R.id.map_custom_toolbar,
                                             activity.getString(subtitleResourceID));
    }

}