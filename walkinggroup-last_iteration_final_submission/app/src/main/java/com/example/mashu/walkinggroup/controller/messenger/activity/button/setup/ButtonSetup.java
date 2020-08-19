package com.example.mashu.walkinggroup.controller.messenger.activity.button.setup;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.toast.ToastDisplay;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;

/**
 * The ButtonSetup class is responsible for
 * setting up the buttons in the MessengerActivity.
 */

public class ButtonSetup {

    private Activity activity;


    public ButtonSetup(Activity activity) {
        this.activity = activity;
    }

    public void setup() {

        SetupAlertDialog setupAlertDialog = new SetupAlertDialog(activity);

        ImageButton newMsg = activity.findViewById(R.id.new_msg_button);
        ImageButton emergencyMsg = activity.findViewById(R.id.emergency_button);
        ImageButton broadcastMsg = activity.findViewById(R.id.broadcast_button);

        broadcastMsg.setOnClickListener((View view) -> {

            if(CurrentUserManager.getCurrentUser().getLeadsGroups().size() == 0){

                ToastDisplay display = new ToastDisplay(activity);
                display.displayToast(R.string.broadcast_error_toast, Toast.LENGTH_SHORT);

                return;
            }

            setupAlertDialog.setup(true, false, true);
        });

        newMsg.setOnClickListener((View view) -> {

            if(CurrentUserManager.getCurrentUser().getMonitoredByUsers().size() == 0){

                Toast.makeText(activity, R.string.newMsg_error_toast, Toast.LENGTH_SHORT).show();
                return;
            }

            setupAlertDialog.setup(false, false, false);
        });

        emergencyMsg.setOnClickListener((View view) -> {

            if(CurrentUserManager.getCurrentUser().getMonitoredByUsers().size() == 0){

                Toast.makeText(activity, R.string.emergency_error_toast, Toast.LENGTH_SHORT).show();
                return;
            }

            setupAlertDialog.setup(false, true, false);
        });
    }

}