package com.example.mashu.walkinggroup.controller.leaderboard.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.shared.methods.CustomToolbar;
import com.example.mashu.walkinggroup.model.user.User;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;

public class LeaderBoardActivity extends AppCompatActivity {

    private List<User> userListSorted;
    private List<String> userDescriptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        CustomToolbar.setupCustomToolbar(LeaderBoardActivity.this,
                                         R.id.leader_board_custom_toolbar,
                                         getString(R.string.leader_board_subtitle));

        Call<List<User>> caller = ProxyManager.getProxy(this).getUsers();
        ProxyBuilder.callProxy(this, caller, this::onUserListReturnedListener);
    }

    private void onUserListReturnedListener(List<User> userList) {

        generateUserDescriptionList(userList);
        setupListView();
    }

    private void generateUserDescriptionList(List<User> userList) {

        addUsersWithPointsToUserList(userList);
        createAscendingOrderListOfUsersBasedOnPoints();
        createUserDescriptionsListFromUserSortedList();
    }

    private void addUsersWithPointsToUserList(List<User> userList) {

        userListSorted = new ArrayList<>();

        for (User user : userList) {

            if (user.getTotalPointsEarned() != null) {
                userListSorted.add(user);
            }
        }
    }

    private void createAscendingOrderListOfUsersBasedOnPoints() {

        Collections.sort(userListSorted, (User user1, User user2) ->
                user2.getTotalPointsEarned().compareTo(user1.getTotalPointsEarned())
        );
    }

    private void createUserDescriptionsListFromUserSortedList() {

        userDescriptions = new ArrayList<>();

        for (User user : userListSorted) {
            userDescriptions.add(user.getUserDescription());
        }
    }

    private void setupListView() {

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(getApplicationContext(), R.layout.user_description, userDescriptions);

        ListView userDescriptionListView = findViewById(R.id.user_descriptions_list_view);
        userDescriptionListView.setAdapter(adapter);
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, LeaderBoardActivity.class);
    }

}