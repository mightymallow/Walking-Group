package com.example.mashu.walkinggroup.controller.map.activity.walking.group.methods.setup.create.button.functionality;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.map.activity.shared.methods.UpdateOriginAndDestination;
import com.example.mashu.walkinggroup.controller.shared.methods.AnimationBuilder;
import com.example.mashu.walkinggroup.controller.shared.methods.help.methods.dialog.display.HelpMethodDialog;
import com.example.mashu.walkinggroup.controller.toast.ToastDisplay;

/**
 * The SetupCreateButton class handles setting up the
 * create group button.
 */

public class SetupCreateButton {

    private FragmentActivity activity;


    public SetupCreateButton(FragmentActivity activity) {
        this.activity = activity;
    }

    public void setupCreateGroupButton() {

        ImageView createGroupImageView = activity.findViewById(R.id.create_group_imageView);
        createGroupImageView.setOnClickListener((View view) -> {

            if (isOriginSet() && isDestinationSet()) {

                HelpMethodDialog dialog = new HelpMethodDialog(activity);
                dialog.buildGroupNameDialog();

            } else {

                ToastDisplay display = new ToastDisplay(activity);
                display.displayToast(R.string.specify_meet_and_destination_points, Toast.LENGTH_LONG);

            }
        });
    }

    private boolean isOriginSet() {
        return UpdateOriginAndDestination.getOrigin() != null;
    }

    private boolean isDestinationSet() {
        return UpdateOriginAndDestination.getDestination() != null;
    }

    public void resetToNewWalkingGroupScreen() {

        Update update = new Update(activity);
        update.handleUpdates();

        makeLinearLayoutWithEditTextsVisible();
        makeCreateGroupImageViewGone();
    }

    private void makeLinearLayoutWithEditTextsVisible() {

        LinearLayout editTextsLinearLayout = activity.findViewById(R.id.map_editTexts_linear_layout);
        editTextsLinearLayout.setVisibility(View.VISIBLE);
    }

    private void makeCreateGroupImageViewGone() {

        ImageView createGroupImageView = activity.findViewById(R.id.create_group_imageView);
        createGroupImageView.setVisibility(View.GONE);

        fadeOutCreateGroupImageView(createGroupImageView);
    }

    private void fadeOutCreateGroupImageView(ImageView createGroupImageView) {

        AnimationBuilder builder = new AnimationBuilder(activity);

        ImageView placePickerImageView = activity.findViewById(R.id.place_picker_imageView);
        createGroupImageView.setAnimation(builder.fadeOut(placePickerImageView));
    }

}