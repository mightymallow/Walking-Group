package com.example.mashu.walkinggroup.controller.permissions.activity;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.model.permission.requests.PermissionRequest;

import java.util.List;

/**
 * The PermissionsListAdapter handles creating
 * the adapter for the list of permissions in the
 * PermissionsActivity.
 */

public class PermissionsListAdapter extends ArrayAdapter<PermissionRequest> {

    private Activity activity;
    private List<PermissionRequest> listOfPermissionRequests;


    public PermissionsListAdapter(Activity activity, List<PermissionRequest> listOfPermissionRequests) {

        super(activity, R.layout.permissions_request_list_item, listOfPermissionRequests);

        this.activity = activity;
        this.listOfPermissionRequests = listOfPermissionRequests;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View itemView = convertView;

        if (itemView == null) {
            itemView = activity.getLayoutInflater().inflate(R.layout.permissions_request_list_item, parent, false);
        }

        PermissionRequest currentPermissionRequest = listOfPermissionRequests.get(position);

        TextView statusTextView = itemView.findViewById(R.id.status_textView);
        TextView requestDetailsTextView = itemView.findViewById(R.id.request_details_textView);
        TextView authorizorsTextView = itemView.findViewById(R.id.authorizors_textView);

        statusTextView.setText(activity.getString(R.string.status_textView_content, currentPermissionRequest.getStatus()));
        requestDetailsTextView.setText(activity.getString(R.string.request_details_textView_content, currentPermissionRequest.getMessage()));
        authorizorsTextView.setText(activity.getString(R.string.authorizors_textView_content, currentPermissionRequest.revealAuthorizorContent()));

        return itemView;
    }

}