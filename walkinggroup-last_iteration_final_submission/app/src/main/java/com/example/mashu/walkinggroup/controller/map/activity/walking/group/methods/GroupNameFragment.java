package com.example.mashu.walkinggroup.controller.map.activity.walking.group.methods;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.map.activity.walking.group.methods.setup.create.button.functionality.SetupCreateButton;

/**
 * The GroupNameFragment class exists to handle the function of
 * carrying out the actions that follow the clicking of the positive button
 * on the Dialog Fragment where the user has input the group name.
 */

public class GroupNameFragment extends AppCompatDialogFragment {


    public interface ButtonPositiveListener {
        void onButtonPositiveClick(String groupName);
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity())
                                  .inflate(R.layout.group_name_layout, null);

        setCancelable(false);

        DialogInterface.OnClickListener listener = implementOKButtonListener(view);

        return buildAlertDialog(view, listener);
    }

    @NonNull
    private DialogInterface.OnClickListener implementOKButtonListener(View view) {

        return (dialog, which) -> {

            if (which == DialogInterface.BUTTON_POSITIVE) {

                EditText groupNameEditText = view.findViewById(R.id.group_name_editText);
                String groupName = groupNameEditText.getText().toString();

                ((ButtonPositiveListener) getActivity()).onButtonPositiveClick(groupName);

                SetupCreateButton changeViews = new SetupCreateButton(getActivity());
                changeViews.resetToNewWalkingGroupScreen();
            }
        };
    }

    private Dialog buildAlertDialog(View view, DialogInterface.OnClickListener listener) {

        return new AlertDialog.Builder(getActivity())
                              .setTitle(R.string.choose_group_name)
                              .setView(view)
                              .setPositiveButton(android.R.string.ok, listener)
                              .create();
    }

}