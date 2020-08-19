package com.example.mashu.walkinggroup.controller.messenger.activity.button.setup;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.mashu.walkinggroup.R;

/**
 * The LayoutBuilder class is responsible for building layouts
 * for the MessangerActivity.
 */

public class LayoutBuilder {

    private Activity activity;


    public LayoutBuilder(Activity activity) {
        this.activity = activity;
    }

    public LinearLayout buildLayout(boolean isEmergency, boolean sendBroadcast, View dialogView) {

        LinearLayout linearLayout = dialogView.findViewById(R.id.view_message_linear_layout);
        LinearLayout groupSpinnerLayout = dialogView.findViewById(R.id.group_spinner_layout);

        if (sendBroadcast) {
            buildBroadcastLayout(linearLayout, groupSpinnerLayout);
        } else if (isEmergency) {
            buildEmergencyLayout(linearLayout);
        } else {
            buildDefaultLayout(linearLayout);
        }

        return linearLayout;
    }

    private void buildBroadcastLayout(LinearLayout linearLayout, LinearLayout groupSpinnerLayout) {
        groupSpinnerLayout.setVisibility(View.VISIBLE);
        setBroadcastLayoutBackgroundColor(linearLayout);
    }

    public void setBroadcastLayoutBackgroundColor(LinearLayout linearLayout) {
        linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimaryDark));
    }

    public void buildEmergencyLayout(LinearLayout linearLayout) {
        linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.red));
    }

    private void buildDefaultLayout(LinearLayout linearLayout) {
        linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.green));
    }

}