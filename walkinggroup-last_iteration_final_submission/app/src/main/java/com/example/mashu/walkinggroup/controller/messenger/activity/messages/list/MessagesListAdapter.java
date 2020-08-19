package com.example.mashu.walkinggroup.controller.messenger.activity.messages.list;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.model.Message;

import java.util.List;

/**
 * The MessagesListAdapter class handles creating an adapter
 * for the MessagesList ListView.
 */

public class MessagesListAdapter extends ArrayAdapter<Message> {

    private Activity activity;
    private List<Message> listOfMessages;


    public MessagesListAdapter(Activity activity, List<Message> listOfMessages) {

        super(activity, R.layout.messenger_layout, listOfMessages);

        this.activity = activity;
        this.listOfMessages = listOfMessages;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View itemView = convertView;

        if (itemView == null) {
            itemView = activity.getLayoutInflater().inflate(R.layout.messenger_layout, parent, false);
        }

        Message currentMessage = listOfMessages.get(position);
        ImageView userIconImageView = itemView.findViewById(R.id.user_icon);

        if (currentMessage.isEmergency()) {
            userIconImageView.setImageResource(R.drawable.error_icon_48);
        } else {
            userIconImageView.setImageResource(R.drawable.user_messenger_icon);
        }

        TextView senderNameText = itemView.findViewById(R.id.sender_name);
        TextView latestMessageText = itemView.findViewById(R.id.latest_message);
        TextView timeSentText = itemView.findViewById(R.id.time_sent);

        if (!currentMessage.isRead()) {

            senderNameText.setTypeface(null, Typeface.BOLD);
            latestMessageText.setTypeface(null, Typeface.BOLD);

        } else {

            senderNameText.setTypeface(null, Typeface.NORMAL);
            latestMessageText.setTypeface(null, Typeface.NORMAL);

        }

        senderNameText.setText(activity.getString(R.string.messages_text, currentMessage.getFromUser().getName()));
        latestMessageText.setText(activity.getString(R.string.messages_text, currentMessage.getText()));
        timeSentText.setText(activity.getString(R.string.messages_text, currentMessage.getTimestamp()));

        return itemView;
    }

}