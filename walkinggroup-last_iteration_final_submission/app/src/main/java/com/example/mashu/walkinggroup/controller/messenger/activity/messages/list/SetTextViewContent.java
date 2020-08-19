package com.example.mashu.walkinggroup.controller.messenger.activity.messages.list;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.model.Message;

/**
 * The SetTextViewContent handles setting the
 * content of all textviews in the
 * MessengerActivity.
 */

public class SetTextViewContent {

    private Activity activity;
    private View dialogView;
    private Message messageClicked;


    public SetTextViewContent(Activity activity, View dialogView, Message messageClicked) {

        this.activity = activity;
        this.dialogView = dialogView;
        this.messageClicked = messageClicked;
    }

    public void setContent(){

        TextView messageDialogText = dialogView.findViewById(R.id.message_dialog_text);
        messageDialogText.setText(activity.getString(R.string.messages_text, messageClicked.getText()));

        TextView nameDialogText = dialogView.findViewById(R.id.from_user_name);
        nameDialogText.setText(activity.getString(R.string.user_name_textView_content, messageClicked.getFromUser().getName()));

        TextView emailDialogText = dialogView.findViewById(R.id.from_user_email);
        emailDialogText.setText(activity.getString(R.string.user_email_textView_content, messageClicked.getFromUser().getEmail()));

        TextView timestampDialogText = dialogView.findViewById(R.id.from_user_timestamp);
        timestampDialogText.setText(activity.getString(R.string.user_time_sent_textView_content, messageClicked.getTimestamp()));
    }

}