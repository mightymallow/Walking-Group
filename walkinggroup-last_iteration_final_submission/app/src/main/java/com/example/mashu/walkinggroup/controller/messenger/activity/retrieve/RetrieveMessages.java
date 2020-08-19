package com.example.mashu.walkinggroup.controller.messenger.activity.retrieve;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.toast.ToastDisplay;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.model.Message;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import java.util.List;

import retrofit2.Call;

/**
 * The RetrieveMessages class is responsible for getting
 * messages of the current user from the server.
 */

public class RetrieveMessages {

    private static final String TAG = "MessengerActivity";

    private Activity activity;
    private List<Message> listOfMessages;
    private ArrayAdapter<Message> adapter;


    public RetrieveMessages(Activity activity, List<Message> listOfMessages, ArrayAdapter<Message> adapter) {

        this.activity = activity;
        this.listOfMessages = listOfMessages;
        this.adapter = adapter;
    }

    public void retrieveAllMessagesFromTheServerToTheCurrentUser(){

        Call<List<Message>> caller = ProxyManager.getProxy(activity).getMessages(CurrentUserManager.getCurrentUser().getId());
        ProxyBuilder.callProxy(activity, caller, this::onAllMessagesReceived);
    }

    private void onAllMessagesReceived(List<Message> allMessagesReceived) {

        if (allMessagesReceived == null) {

            ToastDisplay display = new ToastDisplay(activity);
            display.displayToast(R.string.getting_messages_error, Toast.LENGTH_SHORT);
            return;
        }

        allMessagesReceived = bubbleSortMessagesWithRespectToTimeStamps(allMessagesReceived);
        filterOutDuplicateMessages(allMessagesReceived);

        for (int i = 0; i < listOfMessages.size(); i++) {

            long userID = listOfMessages.get(i).getFromUser().getId();
            RetrieveSenderInfo retrieveSenderInfo = new RetrieveSenderInfo(activity, listOfMessages, adapter, userID, i);
            retrieveSenderInfo.retrieveInformationOnMessageSenderFromServer();
        }


        adapter.notifyDataSetChanged();
        DelayPostMessagesReceived delayPostMessagesReceived = new DelayPostMessagesReceived(activity);
        delayPostMessagesReceived.setDelay();
    }

    private List<Message> bubbleSortMessagesWithRespectToTimeStamps(List<Message> listOfMessages) {

        for (int i = 0; i < listOfMessages.size() - 1; i++) {
            for (int j = 0; j < listOfMessages.size() - i - 1; j++) {

                if (listOfMessages.get(j).getTimestamp().before(listOfMessages.get(j + 1).getTimestamp())) {

                    Message temp = listOfMessages.get(j);
                    listOfMessages.set(j, listOfMessages.get(j + 1));
                    listOfMessages.set(j + 1, temp);
                }
            }
        }

        return listOfMessages;
    }

    private void filterOutDuplicateMessages(List<Message> messages) {

        for(int messagePos = 0; messagePos < messages.size(); messagePos++){

            Message message = messages.get(messagePos);
            Log.i(TAG, "\nmessages received from user + " + message.getFromUser() + "   message text = " + message.getText());

            if (!message.getFromUser().getId().equals(CurrentUserManager.getCurrentUser().getId())) {
                ensureNoDuplicates(messagePos, message);
            }
        }
    }

    private void ensureNoDuplicates(int messagePosInMessages, Message message) {

        if(listOfMessages.size() - 1 < messagePosInMessages){
            listOfMessages.add(message);
        } else {
            listOfMessages.set(messagePosInMessages, message);
        }
    }

}