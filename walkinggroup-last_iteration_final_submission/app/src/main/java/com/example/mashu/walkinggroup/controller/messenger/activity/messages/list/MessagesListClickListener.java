package com.example.mashu.walkinggroup.controller.messenger.activity.messages.list;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.model.Message;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import java.util.List;

import retrofit2.Call;

/**
 * The MessagesListClickListener class provides
 * a click listener to the list of messages in
 * the MessengerActivity class.
 */

public class MessagesListClickListener {

    private static final String TAG = "MessengerActivity";

    private Activity activity;
    private List<Message> listOfMessages;
    private ArrayAdapter<Message> adapter;


    public MessagesListClickListener(Activity activity, List<Message> listOfMessages, ArrayAdapter<Message> adapter) {

        this.activity = activity;
        this.listOfMessages = listOfMessages;
        this.adapter = adapter;
    }

    public void setup() {

        ListView listView = activity.findViewById(R.id.messages_list);
        listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {

            Message messageClicked = listOfMessages.get(position);

            if (!messageClicked.isRead()) {
                listOfMessages.get(position).setIsRead(true);
                setMessageAsReadOnUserClick(messageClicked.getId());
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            View dialogView = activity.getLayoutInflater().inflate(R.layout.view_message_layout, null);

            LinearLayout linearLayoutDialogText = dialogView.findViewById(R.id.view_message_linear_layout);
            ImageView imageDialogText = dialogView.findViewById(R.id.view_message_icon);

            if (messageClicked.isEmergency()) {

                imageDialogText.setImageResource(R.drawable.error_icon_48);
                linearLayoutDialogText.setBackgroundColor(activity.getResources().getColor(R.color.red));

            } else {

                imageDialogText.setImageResource(R.drawable.user_messenger_icon);
                linearLayoutDialogText.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimaryDark));

            }

            SetTextViewContent setTextViewContent = new SetTextViewContent(activity, dialogView, messageClicked);
            setTextViewContent.setContent();

            builder.setView(dialogView);
            AlertDialog dialog = builder.create();
            dialog.show();

            adapter.notifyDataSetChanged();
        });

    }

    private void setMessageAsReadOnUserClick(long messageId) {

        Call<Message> caller = ProxyManager.getProxy(activity).markMessageAsRead(messageId, true);
        ProxyBuilder.callProxy(activity, caller, this::onReturnedReadMessage);
    }

    private void onReturnedReadMessage(Message message) {
        Log.i(TAG, "message received from server" + message);
    }

}