package com.example.mashu.walkinggroup.controller.main.menu.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mashu.walkinggroup.ReceivePermissions;
import com.example.mashu.walkinggroup.controller.leaderboard.activity.LeaderBoardActivity;
import com.example.mashu.walkinggroup.controller.main.menu.activity.buttons.setup.MenuButtonSetup;
import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.toast.ToastDisplay;
import com.example.mashu.walkinggroup.model.Message;
import com.example.mashu.walkinggroup.model.user.User;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import java.util.List;

import retrofit2.Call;

public class MainMenuActivity extends AppCompatActivity {

    private final String TAG = "MainMenuActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        ToastDisplay display = new ToastDisplay(MainMenuActivity.this);
        display.displayWelcomeToast();

        Log.i(TAG, getString(R.string.user_info) + CurrentUserManager.getCurrentUser().toString());

        MenuButtonSetup menuButtonSetup = new MenuButtonSetup(MainMenuActivity.this);
        menuButtonSetup.setup();

        retrieveAllUnreadMessagesFromServer();

        setupLeaderBoardBtn();
        updateCurrentPointsTxt();
    }

    private void updateCurrentPointsTxt() {
        Call<User> caller = ProxyManager.getProxy(this)
                .getUserById(CurrentUserManager.getCurrentUser().getId());
        ProxyBuilder.callProxy(this, caller, this::onUserReturnedListener);
    }

    private void onUserReturnedListener(User user) {
        TextView txtCurrentPoints = findViewById(R.id.current_points_text_view);
        if (user.getCurrentPoints() != null) {
            txtCurrentPoints.setText("Current Points: " + user.getCurrentPoints());
        }
    }

    private void setupLeaderBoardBtn() {

        Button leaderBoardBtn = findViewById(R.id.leader_board_btn);
        leaderBoardBtn.setOnClickListener((View view) ->
            startActivity(LeaderBoardActivity.makeIntent(MainMenuActivity.this))
        );
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        finishAffinity();
    }


    public static Intent makeIntent(Context context){
        return  new Intent(context, MainMenuActivity.class);
    }

    private void retrieveAllUnreadMessagesFromServer(){

        Call<List<Message>> caller = ProxyManager.getProxy(MainMenuActivity.this)
                .getUnreadMessages(CurrentUserManager.getCurrentUser().getId(), null);

        ProxyBuilder.callProxy(MainMenuActivity.this, caller, this::onUnreadMessagesReceived);
    }

    private void onUnreadMessagesReceived(List<Message> unreadMessagesReceived){

        Log.i(TAG, "Get number of unread unreadMessagesReceived");
        int numUnreadMessages = 0;

        for(int i = 0; i < unreadMessagesReceived.size(); i++){

            Message message = unreadMessagesReceived.get(i);

            if(!message.getFromUser().getId().equals(CurrentUserManager.getCurrentUser().getId())){
                numUnreadMessages++;
            }
        }

        TextView messengerButtonText = findViewById(R.id.messenger_btn_textView);
        messengerButtonText.setText(getString(R.string.messages_num_text, numUnreadMessages));
    }

    @Override
    protected void onResume() {

        retrieveAllUnreadMessagesFromServer();
        super.onResume();
        updateCurrentPointsTxt();
    }

}
