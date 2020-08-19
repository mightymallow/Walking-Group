package com.example.mashu.walkinggroup.controller.messenger.activity.button.setup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.messenger.activity.button.setup.message.handling.SetupSendBtn;

/**
 * The SetupAlertDialog class is responsible
 * for setting up the alert dialog for
 * the MessengerActivity.
 */

public class SetupAlertDialog {

    private Activity activity;
    private LayoutBuilder layoutBuilder;

    public SetupAlertDialog(Activity activity) {

        this.activity = activity;
        this.layoutBuilder = new LayoutBuilder(activity);
    }

    public void setup(boolean showEmergencySwitch, boolean isEmergency, boolean sendBroadcast) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        @SuppressLint("InflateParams")
        View dialogView = activity.getLayoutInflater().inflate(R.layout.send_message_layout, null);

        builder.setView(dialogView);

        LinearLayout linearLayout = layoutBuilder.buildLayout(isEmergency, sendBroadcast, dialogView);

        Switch emergencySwitch = dialogView.findViewById(R.id.emergency_switch);
        emergencySwitchAction(showEmergencySwitch, linearLayout, emergencySwitch);

        int indexSelected = buildSpinner(dialogView);
        EditText messageEditText = dialogView.findViewById(R.id.enter_message_text);
        Dialog dialog = createDialogAndShow(builder, dialogView);

        SetupSendBtn setupSendBtn = new SetupSendBtn(activity, dialogView, messageEditText, isEmergency,
                                                     sendBroadcast, indexSelected, emergencySwitch, dialog);

        setupSendBtn.setup();
    }

    private Dialog createDialogAndShow(AlertDialog.Builder builder, View dialogView) {

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        Button cancelButton = dialogView.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener((View view) -> dialog.dismiss());

        return dialog;
    }

    private int buildSpinner(View dialogView) {

        Spinner listOfGroups = dialogView.findViewById(R.id.select_group_spinner);
        SetupSpinner setupSpinner = new SetupSpinner(activity);
        return setupSpinner.setup(listOfGroups);
    }

    private void emergencySwitchAction(boolean showEmergencySwitch, LinearLayout linearLayout, Switch emergencySwitch) {

        if (showEmergencySwitch) {

            emergencySwitch.setOnCheckedChangeListener((CompoundButton compoundButton, boolean b) -> {

                if (emergencySwitch.isChecked()) {
                    layoutBuilder.buildEmergencyLayout(linearLayout);
                } else {
                    layoutBuilder.setBroadcastLayoutBackgroundColor(linearLayout);
                }
            });

        } else {

            emergencySwitch.setVisibility(View.INVISIBLE);

        }
    }

}