package com.example.mashu.walkinggroup.controller.messenger.activity.button.setup.message.handling;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.model.Group;
import com.example.mashu.walkinggroup.model.Message;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import java.util.List;

import retrofit2.Call;

/**
 * The SendMessage class handles
 * sending different kinds of messages.
 */

public class SendMessage {

    private static final String TAG = "MessengerActivity";

    private Activity activity;


    public SendMessage(Activity activity) {
        this.activity = activity;
    }

    public Message sendMessageFromCurrentUser(String text) {

        Message message = new Message();
        message.setText(text);
        message.setFromUser(CurrentUserManager.getCurrentUser());

        return message;
    }

    public void sendMessageToGroup(Group group, Message message) {

        Call<List<Message>> caller = ProxyManager.getProxy(activity).newMessageToGroup(group.getId(), message);
        ProxyBuilder.callProxy(activity, caller, this::messageSent);
    }

    public void sendMessageToParents(Message message) {

        Call<List<Message>> caller = ProxyManager.getProxy(activity)
                                                 .newMessageToParentsOf(CurrentUserManager.getCurrentUser().getId(), message);
        ProxyBuilder.callProxy(activity, caller, this::messageSent);
    }

    private void messageSent(List<Message> messages) {

        Toast.makeText(activity, R.string.message_sent_toast, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "" + messages.toString());
    }

}