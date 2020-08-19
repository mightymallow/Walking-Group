package com.example.mashu.walkinggroup.controller.main.menu.activity.buttons.setup;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.dashboard.map.activity.DashboardMapActivity;
import com.example.mashu.walkinggroup.controller.main.menu.activity.tracking.click.listeners.StartGpsUpload;
import com.example.mashu.walkinggroup.controller.main.menu.activity.tracking.click.listeners.StopGpsUpload;
import com.example.mashu.walkinggroup.controller.messenger.activity.MessengerActivity;

/**
 * The DashboardButtonSetup class handles the setup for the
 * main menu dashboard buttons.
 */

public class DashboardButtonSetup {

    private Activity activity;


    public DashboardButtonSetup(Activity activity) {
        this.activity = activity;
    }

    public void setup(){
        setupDashboardButtons();
    }

    private void setupDashboardButtons() {

        setupGPSImageViewClickListeners();
        setupDashboardMapImageBtn();
        setupMessengerBtn();
    }

    private void setupGPSImageViewClickListeners() {

        ImageView startGPSImageView = activity.findViewById(R.id.start_gps_imageView);
        startGPSImageView.setOnClickListener((View view) -> {

            StartGpsUpload start = new StartGpsUpload(activity);
            start.onStartGpsUpload();
        });

        ImageView stopGPSImageView = activity.findViewById(R.id.stop_gps_imageView);
        stopGPSImageView.setOnClickListener((View view) -> {

            StopGpsUpload stop = new StopGpsUpload(activity);
            stop.onStopGpsUpload();
        });
    }

    private void setupDashboardMapImageBtn() {
        ImageButton dashboardMapImageBtn = activity.findViewById(R.id.dashboard_map_imageButton);
        dashboardMapImageBtn.setOnClickListener((View view) -> activity.startActivity(DashboardMapActivity.makeIntent(activity)));
    }

    private void setupMessengerBtn(){

        ImageButton messengerButton = activity.findViewById(R.id.messenger_btn);
        messengerButton.setOnClickListener((View view) -> activity.startActivity(MessengerActivity.startMessenger(activity)));
    }

}