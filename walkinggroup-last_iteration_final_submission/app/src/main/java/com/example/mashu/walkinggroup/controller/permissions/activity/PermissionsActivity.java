package com.example.mashu.walkinggroup.controller.permissions.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.permissions.activity.update.list.view.RetrievePermissionRequests;
import com.example.mashu.walkinggroup.controller.shared.methods.CustomToolbar;

/**
 * The PermissionsActivity class describes the
 * activity that displays all pending permissions
 * for the current user and allows the
 * current user to allow or deny permissions request.
 */

public class PermissionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);

        CustomToolbar.setupCustomToolbar(PermissionsActivity.this, R.id.permissions_custom_toolbar,
                                         getString(R.string.pending_permissions_subtitle));

        RetrievePermissionRequests retrievePermissionRequests = new RetrievePermissionRequests(PermissionsActivity.this);
        retrievePermissionRequests.retrieve();
    }

    public static Intent makeIntent(Context context){
        return new Intent(context, PermissionsActivity.class);
    }

}