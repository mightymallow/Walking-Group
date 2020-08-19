package com.example.mashu.walkinggroup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mashu.walkinggroup.controller.shared.methods.CustomToolbar;
import com.example.mashu.walkinggroup.model.permission.requests.Authorizor;
import com.example.mashu.walkinggroup.model.permission.requests.PermissionRequest;
import com.example.mashu.walkinggroup.model.permission.requests.PermissionStatus;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.model.user.User;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import retrofit2.Call;

public class ReceivePermissions extends AppCompatActivity {

    private final String TAG = "ReceivedPermissions";

    private Activity activity = ReceivePermissions.this;
    private List<PermissionRequest> permissionRequestList;
    private ArrayAdapter<PermissionRequest> adapter;

    private final int delayTimeInMs = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_permissions);

        CustomToolbar.setupCustomToolbar(ReceivePermissions.this, R.id.permissions_custom_toolbar, getString(R.string.permissions_toolbar));

        permissionRequestList = new ArrayList<>();

        populateListView();

        receivePermissions();
        registerClickCallBack();

    }

    private void receivePermissions(){

        Call<List<PermissionRequest>> caller = ProxyManager.getProxy(this).getPermissionsForUser(CurrentUserManager.getCurrentUser().getId());
        ProxyBuilder.callProxy(activity, caller, this::permissionsReceived);

    }

    private void permissionsReceived(List<PermissionRequest> permissionRequests){

        Log.i(TAG, "permissions : "  + permissionRequests);
        permissionRequestList = permissionRequests;
        Collections.reverse(permissionRequestList);

        for(PermissionRequest permissionRequest : permissionRequestList){

            getUserInfo(permissionRequest);
        }

        populateListView();
        setDelay(delayTimeInMs);

    }

    private void setDelay(int time) {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                TextView text = findViewById(R.id.getting_permissions_text);

                if(permissionRequestList.size() == 0){

                    text.setText(getString(R.string.noPermissionFoundString));
                }
                else{

                    text.setVisibility(View.INVISIBLE);
                }

                ListView listView = findViewById(R.id.permissions_list);
                listView.setVisibility(View.VISIBLE);

            }
        }, time);

    }

    private String setWhoAcceptedOrDenied(PermissionRequest permissionRequest){

        StringBuilder accepted = new StringBuilder();
        StringBuilder denied = new StringBuilder();

        for (Authorizor authorizor : permissionRequest.getAuthorizors()) {

            if(authorizor.getWhoApprovedOrDenied() != null) {

                long currentID = CurrentUserManager.getCurrentUser().getId();
                long authorizorID = authorizor.getWhoApprovedOrDenied().getId();

                if(currentID == authorizorID){

                    if(authorizor.getStatus() == PermissionStatus.APPROVED){

                        accepted.append("you(" + CurrentUserManager.getCurrentUser().getEmail() + ") and ");
                    }
                    else if(authorizor.getStatus() == PermissionStatus.DENIED){

                        denied.append("you(" + CurrentUserManager.getCurrentUser().getEmail() + ") and ");
                    }
                }
                else if (authorizor.getStatus() == PermissionStatus.APPROVED) {

                    accepted.append(authorizor.getWhoApprovedOrDenied().getName() + "(" + authorizor.getWhoApprovedOrDenied().getEmail() + ") and ");

                } else if (authorizor.getStatus() == PermissionStatus.DENIED) {

                    denied.append(authorizor.getWhoApprovedOrDenied().getName() + "(" + authorizor.getWhoApprovedOrDenied().getEmail() + ") and ");

                }
            }
        }

        accepted = fixString(accepted, getString(R.string.acceptedString));
        denied = fixString(denied, getString(R.string.deniedString));

        return accepted.toString() + denied.toString();
    }


    private StringBuilder fixString(StringBuilder string, String message){

        if(string.length() != 0) {

            string.delete(string.length() - 4, string.length() - 1);
            string.insert(0, "\n\n" + message);

        }

        return string;
    }


    private void getUserInfo(PermissionRequest permissionRequest){

        for(Authorizor authorizor : permissionRequest.getAuthorizors()){

            if(authorizor.getWhoApprovedOrDenied() != null){

                getUserByID(permissionRequest, authorizor.getWhoApprovedOrDenied().getId());
            }

        }

    }

    private void getUserByID(PermissionRequest permissionRequest, long id){

        Call<User> caller = null;
        caller = ProxyManager.getProxy(this).getUserById(id);
        ProxyBuilder.callProxy(activity, caller, serverMsg -> receivedUser(permissionRequest, id, serverMsg));

    }

    private void receivedUser(PermissionRequest permission, long id, User user){

        for(Authorizor authorizor : permission.getAuthorizors()){

            if(authorizor.getWhoApprovedOrDenied() != null) {

                if (authorizor.getWhoApprovedOrDenied().getId() == id) {

                    authorizor.setWhoApprovedOrDenied(user);
                }
            }
        }
    }

    private void setPermission(PermissionRequest permissionRequest, PermissionStatus status){

        Call<PermissionRequest> caller = null;
        caller = ProxyManager.getProxy(this).approveOrDenyPermissionRequest(permissionRequest.getId(), status);
        ProxyBuilder.callProxy(activity, caller, this::statusReceived);

    }

    private void statusReceived(PermissionRequest permissionRequest){

        Log.i(TAG, "status permission message received = "  + permissionRequest);
    }

    private void populateListView() {

        adapter = new PermissionsListAdapter();
        ListView permissionListView = findViewById(R.id.permissions_list);
        permissionListView.setAdapter(adapter);

    }

    public class PermissionsListAdapter extends ArrayAdapter<PermissionRequest> {

        public PermissionsListAdapter() {

            super(activity, R.layout.receive_permissions_list_view_layout, permissionRequestList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View itemView = convertView;

            if (itemView == null) {

                itemView = getLayoutInflater()
                        .inflate(R.layout.receive_permissions_list_view_layout, parent, false);
            }

            PermissionRequest currentPermission = permissionRequestList.get(position);
            LinearLayout layout = itemView.findViewById(R.id.linear_layout);
            ImageView statusIcon = itemView.findViewById(R.id.status_icon);

            setColors(currentPermission, layout, statusIcon);


            TextView statusText = itemView.findViewById(R.id.status_text);
            TextView messageText = itemView.findViewById(R.id.message_text);

            if(currentPermission.getStatus() == PermissionStatus.PENDING){
                statusText.setTypeface(null, Typeface.BOLD);
                messageText.setTypeface(null, Typeface.BOLD);
            }
            else{
                statusText.setTypeface(null, Typeface.NORMAL);
                messageText.setTypeface(null, Typeface.NORMAL);
            }

            messageText.setText("" + currentPermission.getMessage());
            statusText.setText(""  + currentPermission.getStatus());

            return itemView;
        }
    }

    private void setColors(PermissionRequest permissionRequest, LinearLayout layout, ImageView statusIcon){

        if(permissionRequest.getStatus() == PermissionStatus.APPROVED){

            layout.setBackgroundColor(getResources().getColor(R.color.green));
            statusIcon.setImageResource(R.drawable.accepted_icon);

        }
        else if(permissionRequest.getStatus() == PermissionStatus.DENIED){

            layout.setBackgroundColor(getResources().getColor(R.color.red));
            statusIcon.setImageResource(R.drawable.denied_icon);
        }
        else{
            assert(permissionRequest.getStatus().equals(PermissionStatus.PENDING));
            layout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            statusIcon.setImageResource(R.drawable.pending_icon);
        }

    }


    private void isHideButtons(Button acceptedButton, Button deniedButton, boolean hide){

        if(hide){
            acceptedButton.setEnabled(false);
            deniedButton.setEnabled(false);
            acceptedButton.setVisibility(View.INVISIBLE);
            deniedButton.setVisibility(View.INVISIBLE);
        }
    }

    private void findSentToUsers(PermissionRequest permission){

        // for testing purposes
        int count = 0;

        for(Authorizor authorizor : permission.getAuthorizors()){

            count++;

            for(User user : authorizor.getUsers()){

                Log.i(TAG, "count: " + count + "    status: " + authorizor.getStatus()  +  "    user:" + user.getId() + "   current" + CurrentUserManager.getCurrentUser().getId());
            }

        }

    }

    private boolean canUserAcceptRequest(PermissionRequest permissionRequest){

        for(Authorizor authorizor : permissionRequest.getAuthorizors()){

            if(authorizor.getWhoApprovedOrDenied() != null){

                for(User user : authorizor.getUsers()){

                    long userID = user.getId();
                    long currentID = CurrentUserManager.getCurrentUser().getId();

                    if(currentID == userID){

                        return false;
                    }

                }
            }
        }

        return true;
    }

    private void setUpAlertDialog(PermissionRequest permissionRequest){

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View dialogView = getLayoutInflater().inflate(R.layout.permissions_dialog_view, null);

        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();

        LinearLayout layout = dialogView.findViewById(R.id.view_message_linear_layout);
        ImageView statusIcon  = dialogView.findViewById(R.id.status_icon);
        TextView info = dialogView.findViewById(R.id.additional_info);
        TextView messageText = dialogView.findViewById(R.id.message_text);
        Button acceptedButton  = dialog.findViewById(R.id.accept_request_button);
        Button deniedButton = dialog.findViewById(R.id.deny_request_button);

        long currentUserID = CurrentUserManager.getCurrentUser().getId(),
                userWhoRequested = permissionRequest.getRequestingUser().getId();

        if(permissionRequest.getStatus() != PermissionStatus.PENDING || currentUserID == userWhoRequested || !canUserAcceptRequest(permissionRequest)){
            isHideButtons(acceptedButton, deniedButton, true);
        }

        setColors(permissionRequest,layout, statusIcon);
        info.setText(permissionRequest.getStatus().toString());
        messageText.setText(permissionRequest.getMessage() + setWhoAcceptedOrDenied(permissionRequest));

        acceptedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setPermission(permissionRequest, PermissionStatus.APPROVED);
                setApprovedOrDenied(permissionRequest, currentUserID, true);
                isHideButtons(acceptedButton, deniedButton, true);
                setColors(permissionRequest,layout, statusIcon);

                messageText.setText(permissionRequest.getMessage() + setWhoAcceptedOrDenied(permissionRequest));

            }
        });

        deniedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setPermission(permissionRequest, PermissionStatus.DENIED);
                setApprovedOrDenied(permissionRequest, currentUserID, false);
                isHideButtons(acceptedButton, deniedButton, true);
                setColors(permissionRequest,layout, statusIcon);

                messageText.setText(permissionRequest.getMessage() + setWhoAcceptedOrDenied(permissionRequest));

            }
        });

        adapter.notifyDataSetChanged();

    }
    private void registerClickCallBack() {

        ListView listView = findViewById(R.id.permissions_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                findSentToUsers(permissionRequestList.get(position));
                setUpAlertDialog(permissionRequestList.get(position));
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setApprovedOrDenied(PermissionRequest permission, long currentUserID, boolean setApprove){

        for (Authorizor authorizor : permission.getAuthorizors()) {

            for (User user : authorizor.getUsers()) {

                if(setApprove) {

                    if (authorizor.getStatus() != PermissionStatus.APPROVED) {

                        if (user.getId() == currentUserID) {

                            authorizor.setWhoApprovedOrDenied(user);
                            authorizor.setStatus(PermissionStatus.APPROVED);

                        }
                    }
                }

                if(!setApprove){

                    if (user.getId() == currentUserID) {

                        authorizor.setWhoApprovedOrDenied(user);
                        authorizor.setStatus(PermissionStatus.DENIED);

                    }
                }

                Log.i(TAG, "status: " + authorizor.getStatus() + "     USER:" + user.getId() +
                        "   CurrentUser: " + CurrentUserManager.getCurrentUser().getId() +
                        "   RequestingUser:" + permission.getRequestingUser().getId());
            }
        }

        if(setApprove) {

            setPermissionToTrue(permission);
        }
        else{

            permission.setStatus(PermissionStatus.DENIED);
        }
    }


    private void setPermissionToTrue(PermissionRequest permissionRequest){

        if(isAllApproved(permissionRequest.getAuthorizors())){

            permissionRequest.setStatus(PermissionStatus.APPROVED);
//            Toast.makeText(activity, "all authorizors set to true", Toast.LENGTH_SHORT).show();

        }
    }

    private boolean isAllApproved(Set<Authorizor> authorizorSet){

        for(Authorizor authorizor : authorizorSet){

            if(authorizor.getStatus() != PermissionStatus.APPROVED){

                return false;
            }
        }

        return true;
    }

    public static Intent makeIntent(Context context){

        return new Intent(context, ReceivePermissions.class);
    }


}
