package com.example.mashu.walkinggroup.controller.messenger.activity.button.setup.message.handling;

import android.app.Activity;
import android.app.Dialog;
import android.widget.Switch;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.model.Group;
import com.example.mashu.walkinggroup.model.Message;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;

/**
 * The MessageHandler class deals with all types of messages
 * received in the MessengerActivity.
 */

public class MessageHandler {

    private Activity activity;
    private Switch emergencySwitch;
    private boolean isEmergency;
    private boolean sendBroadcast;
    private int indexSelected;
    private Dialog dialog;


    public MessageHandler(Activity activity, Switch emergencySwitch, boolean isEmergency,
                          boolean sendBroadcast, int indexSelected, Dialog dialog) {

        this.activity = activity;
        this.emergencySwitch = emergencySwitch;
        this.isEmergency = isEmergency;
        this.sendBroadcast = sendBroadcast;
        this.indexSelected = indexSelected;
        this.dialog = dialog;
    }

    public void handleMessage(String text) {

        SendMessage sendMessage = new SendMessage(activity);
        Message message = sendMessage.sendMessageFromCurrentUser(text);

        if (emergencySwitch.isChecked() || isEmergency) {
            handleEmergencyMessage(text, message);
        } else {
            message.setEmergency(false);
        }

        if (sendBroadcast) {

            Group group = CurrentUserManager.getCurrentUser().getLeadsGroups().get(indexSelected);
            sendMessage.sendMessageToGroup(group, message);

        } else {

            sendMessage.sendMessageToParents(message);

        }

        dialog.dismiss();
    }

    public void handleEmergencyMessage(String text, Message message) {

        message.setEmergency(true);

        if (text.length() == 0) {
            message.setText(activity.getString(R.string.emergency_message));
        }
    }

}