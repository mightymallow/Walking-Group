package com.example.mashu.walkinggroup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mashu.walkinggroup.controller.shared.methods.CustomToolbar;
import com.example.mashu.walkinggroup.model.Group;
import com.example.mashu.walkinggroup.model.user.User;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class ViewGroups extends AppCompatActivity {


    private List<Group> groupsList;
    private ArrayAdapter<Group> adapter;
    private Activity activity = ViewGroups.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_groups);

        CustomToolbar.setupCustomToolbar(ViewGroups.this, R.id.view_groups_toolbar, "View Groups");

        groupsList = new ArrayList<>();
        populateListView();

        getGroups();

    }

    private void getGroups(){

        Call<List<Group>> caller = ProxyManager.getProxy(this).getGroups();
        ProxyBuilder.callProxy(activity, caller, this::groupsReceived);

    }

    private void groupsReceived(List<Group> groups){

        groupsList = groups;

        for(int i = 0; i < groups.size(); i++){

            if(groups.get(i).getLeader() != null) {
                getGroupLeader(groups.get(i));
            }

            if(groups.get(i).getMemberUsers() != null){

                for(int j = 0; j < groups.get(i).getMemberUsers().size(); j++){

                    getMemberID(groups.get(i), j);
                }
            }
        }

        setDelay(2000);
        populateListView();
    }

    private void setDelay(int time) {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                TextView text = findViewById(R.id.getting_group_text);

                if(groupsList.size() == 0){

                    text.setText(R.string.no_groups_found);
                }
                else{

                    text.setVisibility(View.INVISIBLE);
                }

                ListView listView = findViewById(R.id.groups_list);
                listView.setVisibility(View.VISIBLE);

            }
        }, time);

    }

    private void getMemberID(Group group, int index) {

        Call<User> caller = null;
        caller = ProxyManager.getProxy(this).getUserById(group.getMemberUsers().get(index).getId());
        ProxyBuilder.callProxy(activity, caller, serverMsg -> msgFromServer(group, index, serverMsg));
    }

    private void msgFromServer(Group group, int index, User user){

        group.getMemberUsers().set(index, user);
        adapter.notifyDataSetChanged();

    }

    private void getGroupLeader(Group group){

        Call<User> caller = null;
        caller = ProxyManager.getProxy(this).getUserById(group.getLeader().getId());
        ProxyBuilder.callProxy(activity, caller, serverMsg -> receivedUser(group, serverMsg));

    }

    private void receivedUser(Group group, User user){

        group.setLeader(user);
        adapter.notifyDataSetChanged();
    }

    private void populateListView() {

        adapter = new ViewGroups.GroupsListAdapter();
        ListView groupListView = findViewById(R.id.groups_list);
        groupListView.setAdapter(adapter);

    }

    public class GroupsListAdapter extends ArrayAdapter<Group> {

        public GroupsListAdapter() {

            super(activity, R.layout.receive_permissions_list_view_layout, groupsList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View itemView = convertView;

            if (itemView == null) {

                itemView = getLayoutInflater()
                        .inflate(R.layout.groups_list_view, parent, false);
            }

            TextView groupName = itemView.findViewById(R.id.group_name);
            TextView groupLeader = itemView.findViewById(R.id.leader_name);
            TextView membersList = itemView.findViewById(R.id.member_names_text);

            groupName.setText(getString(R.string.group_name_text) + groupsList.get(position).getGroupDescription());

            if(groupsList.get(position).getLeader() != null){

                groupLeader.setText(getString(R.string.group_leader_name) + groupsList.get(position).getLeader().getName());
            }
            else{

                groupLeader.setText(R.string.no_leader_found);
            }

            if(groupsList.get(position).getMemberUsers() != null){

                membersList.setText(convertMembersToString(groupsList.get(position).getMemberUsers()));

            }
            else{

                membersList.setText( R.string.group_has_no_members);
            }

            return itemView;
        }
    }

    private String convertMembersToString(List<User> users){

        if(users.size() == 0){

            return getString(R.string.group_has_no_members);
        }

        StringBuilder listOfUsers = new StringBuilder();

        listOfUsers.append(getString(R.string.members_text));

        for(User user : users){

            listOfUsers.append("\n" + user.getName() + "(" + user.getEmail() + ")");
        }

        return listOfUsers.toString();

    }


    public static Intent makeIntent(Context context){

        return new Intent(context, ViewGroups.class);

    }

}
