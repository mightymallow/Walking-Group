package com.example.mashu.walkinggroup.controller.messenger.activity.button.setup.message.handling;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.toast.ToastDisplay;

/**
 * The SetupSendBtn class is responsible for
 * the functionality of the send button in
 * the MessengerActivity.
 */

public class SetupSendBtn {

    private Activity activity;
    private View dialogView;
    private EditText messageText;
    private boolean isEmergency;
    private boolean sendBroadcast;
    private int indexSelected;
    private Switch emergencySwitch;
    private Dialog dialog;

    public SetupSendBtn(Activity activity, View dialogView, EditText messageText,
                        boolean isEmergency, boolean sendBroadcast, int indexSelected,
                        Switch emergencySwitch, Dialog dialog) {

        this.activity = activity;
        this.dialogView = dialogView;
        this.messageText = messageText;
        this.isEmergency = isEmergency;
        this.sendBroadcast = sendBroadcast;
        this.indexSelected = indexSelected;
        this.emergencySwitch = emergencySwitch;
        this.dialog = dialog;
    }

    public void setup(){

        Button sendButton = dialogView.findViewById(R.id.send_button);
        sendButton.setOnClickListener((View view) -> {

            String text = messageText.getText().toString();

            if (text.length() == 0 && !isEmergency) {

                ToastDisplay display = new ToastDisplay(activity);
                display.displayToast(R.string.empty_message_error_text, Toast.LENGTH_SHORT);

            } else {

                MessageHandler messageHandler = new MessageHandler(activity, emergencySwitch, isEmergency,
                                                                   sendBroadcast, indexSelected, dialog);
                messageHandler.handleMessage(text);

            }
        });
    }

}