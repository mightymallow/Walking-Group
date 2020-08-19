package com.example.mashu.walkinggroup.controller.messenger.activity.retrieve;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;

import com.example.mashu.walkinggroup.R;

/**
 * The DelayPostMessagesReceived class is used to
 * start the time delay after all messages of the
 * current user have been received and dealt with
 * appropriately.
 */

public class DelayPostMessagesReceived {

    private static final int TIME_DELAY = 2000;

    private Activity activity;


    public DelayPostMessagesReceived(Activity activity) {
        this.activity = activity;
    }

    public void setDelay() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            public void run() {

                ListView messagesList = activity.findViewById(R.id.messages_list);
                messagesList.setVisibility(View.VISIBLE);

                handler.postDelayed(this, TIME_DELAY);
            }

        }, TIME_DELAY);
    }

}