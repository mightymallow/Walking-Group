package com.example.mashu.walkinggroup.controller.messenger.activity.animations;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.model.Message;

import java.util.List;

/**
 * The AnimateViews class is responsible for handling the
 * fade in and fade out animations for the three buttons
 * and list view in the MessengerActivity.
 */

public class AnimateViews {

    private static final int ANIMATION_DELAY_TIME = 2000;

    private Activity activity;
    private List<Message> listOfMessages;


    public AnimateViews(Activity activity, List<Message> listOfMessages) {

        this.activity = activity;
        this.listOfMessages = listOfMessages;
    }

    public void setFadeInAndFadeOutAnimationsForViews() {

        TextView broadcastText = activity.findViewById(R.id.broadcast_message_icon_text);
        TextView newMessageText = activity.findViewById(R.id.new_message_icon_text);
        TextView emergencyText = activity.findViewById(R.id.emergency_message_icon_text);
        TextView getMessagesText = activity.findViewById(R.id.getting_messages_text);

        broadcastText.setEnabled(false);
        newMessageText.setEnabled(false);
        emergencyText.setEnabled(false);

        final Handler handler = new Handler();
        handler.postDelayed(() -> {

            Animation animation = AnimationUtils.loadAnimation(activity, R.anim.fadeout);

            broadcastText.startAnimation(animation);
            newMessageText.startAnimation(animation);
            emergencyText.startAnimation(animation);

            broadcastText.setVisibility(View.INVISIBLE);
            newMessageText.setVisibility(View.INVISIBLE);
            emergencyText.setVisibility(View.INVISIBLE);

            if (listOfMessages.size() == 0) {

                getMessagesText.setText(R.string.no_message_found);

            } else {

                getMessagesText.startAnimation(animation);
                getMessagesText.setVisibility(View.INVISIBLE);

            }

            broadcastText.setEnabled(true);
            newMessageText.setEnabled(true);
            emergencyText.setEnabled(true);

        }, ANIMATION_DELAY_TIME);
    }

}