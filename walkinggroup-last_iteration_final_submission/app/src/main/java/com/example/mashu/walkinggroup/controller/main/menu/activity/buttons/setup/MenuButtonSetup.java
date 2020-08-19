package com.example.mashu.walkinggroup.controller.main.menu.activity.buttons.setup;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.main.menu.activity.tracking.LocationUpdater;
import com.example.mashu.walkinggroup.controller.main.menu.activity.tracking.click.listeners.StartGpsUpload;
import com.example.mashu.walkinggroup.controller.map.activity.MapActivity;
import com.example.mashu.walkinggroup.controller.rewardsmanagement.RewardsMenuActivity;

/**
 * The MenuButtonSetup class handles the setup for the
 * main menu buttons.
 */

public class MenuButtonSetup {

    private Activity activity;


    public MenuButtonSetup(Activity activity) {
        this.activity = activity;
    }

    public void setup(){

        setupLaunchMapImageView();

        CenterButtonSetup centerButtonSetup = new CenterButtonSetup(activity);
        centerButtonSetup.setup();

        DashboardButtonSetup dashboardButtonSetup = new DashboardButtonSetup(activity);
        dashboardButtonSetup.setup();

        setupLogoutBtn();
        setupRewardsBtn();
    }

    private void setupLaunchMapImageView() {

        Button launchMapBtn = activity.findViewById(R.id.launch_map_imageView);
        launchMapBtn.setOnClickListener((View view) -> activity.startActivity(MapActivity.makeIntent(activity)));
    }

    private void setupLogoutBtn() {

        Button logoutBtn = activity.findViewById(R.id.logout);

        logoutBtn.setOnClickListener((View view) -> {

            LocationUpdater.stopUploadingLocation();

            StartGpsUpload.setIsClicked(false);

            activity.setResult(Activity.RESULT_OK);
            activity.finish();
        });
    }

    private void setupRewardsBtn() {

        Button rewardsBtn = activity.findViewById(R.id.rewards);

        rewardsBtn.setOnClickListener((View view) -> {

            Intent intent = RewardsMenuActivity.makeIntent(activity.getApplicationContext());
            activity.startActivity(intent);

        });
    }

}