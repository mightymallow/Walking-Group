package com.example.mashu.walkinggroup.controller.map.activity.help.methods;

import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.example.mashu.walkinggroup.controller.shared.methods.help.methods.dialog.display.HelpMethodDialog;

public class DisplayHelp {


    public static void setupHelpButton(FragmentActivity activity, ImageView helpImageView, int messageResourceID) {
        helpImageView.setOnClickListener(view -> new HelpMethodDialog(activity).buildHelpDialog(messageResourceID));
    }

}