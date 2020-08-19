package com.example.mashu.walkinggroup.controller.messenger.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.messenger.activity.messages.list.MessagesListAdapter;
import com.example.mashu.walkinggroup.controller.shared.methods.CustomToolbar;
import com.example.mashu.walkinggroup.model.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * The MessengerActivity class is responsible for
 * allowing messaging between users of the application.
 */

public class MessengerActivity extends AppCompatActivity {

    private List<Message> listOfMessages;
    private Activity activity = MessengerActivity.this;
    private ArrayAdapter<Message> adapter;
    private Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        CustomToolbar.setupCustomToolbar(this, R.id.messenger_custom_toolbar, getString(R.string.messenger_activity_subtitle));

        listOfMessages = new ArrayList<>();
        populateListOfMessagesListView();
        timer = new Timer();

        OnMessengerActivityCreate onMessengerActivityCreate =
                new OnMessengerActivityCreate(activity, listOfMessages, adapter, timer);

        onMessengerActivityCreate.onActivityCreated();
    }

    private void populateListOfMessagesListView() {

        adapter = new MessagesListAdapter(MessengerActivity.this, listOfMessages);
        ListView messagesList = findViewById(R.id.messages_list);
        messagesList.setAdapter(adapter);
    }

    @Override
    protected void onStop() {

        timer.cancel();
        super.onStop();
    }

    public static Intent startMessenger(Context context){
        return new Intent(context, MessengerActivity.class);
    }

}