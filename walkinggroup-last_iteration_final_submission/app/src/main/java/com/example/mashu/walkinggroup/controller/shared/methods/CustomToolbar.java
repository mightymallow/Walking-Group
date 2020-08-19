package com.example.mashu.walkinggroup.controller.shared.methods;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.mashu.walkinggroup.R;

/**
 * The CustomToolbar class deals with
 * setting up a custom toolbar for each screen.
 */

public class CustomToolbar {

    public static void setupCustomToolbar(AppCompatActivity activity, int customToolbarResourceId, String subtitle) {

        Toolbar toolbar = activity.findViewById(customToolbarResourceId);
        activity.setSupportActionBar(toolbar);

        updateToolbarSubTitle(activity, customToolbarResourceId, subtitle);
    }

    public static void updateToolbarSubTitle(AppCompatActivity activity, int customToolbarResourceId, String subtitle){

        Toolbar toolbar = activity.findViewById(customToolbarResourceId);

        TextView toolbarSubtitle = toolbar.findViewById(R.id.action_bar_subtitle);
        toolbarSubtitle.setText(subtitle);
    }

}