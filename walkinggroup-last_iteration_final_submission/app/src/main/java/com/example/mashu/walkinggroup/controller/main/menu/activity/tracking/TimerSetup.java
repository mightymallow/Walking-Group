package com.example.mashu.walkinggroup.controller.main.menu.activity.tracking;

import android.app.Activity;
import android.widget.Toast;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.toast.ToastDisplay;
import com.example.mashu.walkinggroup.model.Group;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The TimerSetup class is used to set the timer
 * when the user reaches the destination point.
 */

public class TimerSetup {

    private static final long TIME_BEFORE_NEXT_TIME_PASSED_INCREMENT = 1000;
    private static final int DELAY_TO_STOP_LOCATION_UPDATES = 600000;
    private static final int DELAY_BEFORE_TIMER_STARTS = 0;

    private Activity activity;
    private long timePassed;
    private Group groupAtDestination;


    public TimerSetup(Activity activity, long timePassed, Group groupAtDestination) {

        this.activity = activity;
        this.timePassed = timePassed;
        this.groupAtDestination = groupAtDestination;
    }

    public Timer setTimerAfterWhichToStopLocationUpdates() {

        ToastDisplay display = new ToastDisplay(activity);
        display.displayToast(R.string.stopping_gps_tracking_after_ten_minutes, Toast.LENGTH_LONG);

        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {

                timePassed += TIME_BEFORE_NEXT_TIME_PASSED_INCREMENT;

                if (timePassed == DELAY_TO_STOP_LOCATION_UPDATES) {
                    LocationUpdater.stopUploadingLocation();
                }
            }
        };

        timer.scheduleAtFixedRate(timerTask, DELAY_BEFORE_TIMER_STARTS, TIME_BEFORE_NEXT_TIME_PASSED_INCREMENT);

        return timer;
    }

}