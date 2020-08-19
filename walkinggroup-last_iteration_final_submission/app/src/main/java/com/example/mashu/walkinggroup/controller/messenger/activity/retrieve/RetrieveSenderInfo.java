package com.example.mashu.walkinggroup.controller.messenger.activity.retrieve;

import android.app.Activity;
import android.widget.ArrayAdapter;

import com.example.mashu.walkinggroup.model.Message;
import com.example.mashu.walkinggroup.model.user.User;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import java.util.List;

import retrofit2.Call;

/**
 * The RetrieveSenderInfo class is responsible for
 * retrieving information on the message sender
 * from the server.
 */

public class RetrieveSenderInfo {

    private Activity activity;
    private List<Message> listOfMessages;
    private ArrayAdapter<Message> adapter;
    private long userID;
    private int index;


    public RetrieveSenderInfo(Activity activity, List<Message> listOfMessages, ArrayAdapter<Message> adapter,
                              long userID, int index) {

        this.activity = activity;
        this.listOfMessages = listOfMessages;
        this.adapter = adapter;
        this.userID = userID;
        this.index = index;
    }

    public void retrieveInformationOnMessageSenderFromServer() {

        Call<User> caller = ProxyManager.getProxy(activity).getUserById(userID);
        ProxyBuilder.callProxy(activity, caller, returnedUser -> onMessageSenderInfoReceived(returnedUser, index));

    }

    private void onMessageSenderInfoReceived(User messageSenderReceived, int index) {

        listOfMessages.get(index).setFromUser(messageSenderReceived);
        adapter.notifyDataSetChanged();
    }

}