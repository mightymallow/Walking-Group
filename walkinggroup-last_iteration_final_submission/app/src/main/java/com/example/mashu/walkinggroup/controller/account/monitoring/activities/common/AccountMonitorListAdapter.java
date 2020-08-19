package com.example.mashu.walkinggroup.controller.account.monitoring.activities.common;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.model.user.User;

import java.util.List;

/**
 * The AccountMonitorListAdapter class contains
 * all information regarding the adapter
 * for the list views of users in the
 * Account Monitoring Activity.
 */

public class AccountMonitorListAdapter extends ArrayAdapter<User> {

    private Activity activity;
    private List<User> users;

    public AccountMonitorListAdapter(Activity activity, List<User> users) {

        super(activity, R.layout.account_monitoring_list_item, users);

        this.activity = activity;
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent){

        TextView accountNameTextView;
        TextView accountEmailTextView;

        if (existingViewIsNotBeingReused(convertView)){

            convertView = activity.getLayoutInflater()
                                  .inflate(R.layout.account_monitoring_list_item, parent, false);
        }

        accountNameTextView = convertView.findViewById(R.id.user_name_textView);
        accountEmailTextView = convertView.findViewById(R.id.user_email_textView);

        String name = activity.getString(R.string.user_name_textView_content, users.get(position).getName());
        accountNameTextView.setText(name);

        String email = activity.getString(R.string.user_email_textView_content, users.get(position).getEmail());
        accountEmailTextView.setText(email);

        return convertView;
    }

    private boolean existingViewIsNotBeingReused(View itemView) {
        return itemView == null;
    }

    public static void populateListView(ListView listView, ArrayAdapter<User> adapter) {
        listView.setAdapter(adapter);
    }

}