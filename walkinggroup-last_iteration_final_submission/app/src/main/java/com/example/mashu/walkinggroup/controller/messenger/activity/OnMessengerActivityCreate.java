package com.example.mashu.walkinggroup.controller.messenger.activity;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.mashu.walkinggroup.controller.messenger.activity.animations.AnimateViews;
import com.example.mashu.walkinggroup.controller.messenger.activity.button.setup.ButtonSetup;
import com.example.mashu.walkinggroup.controller.messenger.activity.messages.list.MessagesListClickListener;
import com.example.mashu.walkinggroup.controller.messenger.activity.retrieve.RetrieveMessages;
import com.example.mashu.walkinggroup.model.Message;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The OnMessengerActivityCreate class handles the
 * functionality of the MessengerActivity when its
 * onCreate method is called.
 */

public class OnMessengerActivityCreate {

    private static final int TIME_AS_MILLISECONDS = 1000;
    private static final int TIME_IN_SECONDS_TO_WAIT_TO_GET_MESSAGES = 60;
    private static final String TAG = "MessengerActivity";

    private Activity activity;
    private List<Message> listOfMessages;
    private ArrayAdapter<Message> adapter;
    private RetrieveMessages retrieveMessages;
    private Timer timer;
    private int timePassed;


    public OnMessengerActivityCreate(Activity activity, List<Message> listOfMessages, ArrayAdapter<Message> adapter, Timer timer) {

        this.activity = activity;
        this.listOfMessages = listOfMessages;
        this.adapter = adapter;
        this.timer = timer;
    }

    public void onActivityCreated(){

        retrieveMessages = new RetrieveMessages(activity, listOfMessages, adapter);
        retrieveMessages.retrieveAllMessagesFromTheServerToTheCurrentUser();
        getLatestMessagesEveryMinute();

        AnimateViews animateViews = new AnimateViews(activity, listOfMessages);
        animateViews.setFadeInAndFadeOutAnimationsForViews();

        ButtonSetup buttonSetup = new ButtonSetup(activity);
        buttonSetup.setup();

        MessagesListClickListener messagesListClickListener = new MessagesListClickListener(activity, listOfMessages, adapter);
        messagesListClickListener.setup();
    }

    private void getLatestMessagesEveryMinute() {

        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {

                timePassed++;

                if(timePassed % TIME_IN_SECONDS_TO_WAIT_TO_GET_MESSAGES == 0){

                    Log.i(TAG, "getting messages every min");
                    retrieveMessages.retrieveAllMessagesFromTheServerToTheCurrentUser();
                }

            }
        };

        timer.scheduleAtFixedRate(timerTask, TIME_AS_MILLISECONDS, TIME_AS_MILLISECONDS);
    }

}