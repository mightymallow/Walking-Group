package com.example.mashu.walkinggroup.controller.view.member.info.activity.display.data;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.mashu.walkinggroup.R;

/**
 * The GeneratePopUpWindow class is responsible for
 * creating the pop up window for the
 * ViewMemberInfoActivity.
 */

public class GeneratePopUpWindow {

    private static final int DIMENSION_VALUE = 0;

    private Activity activity;
    private String displayContent;


    public GeneratePopUpWindow(Activity activity, String displayContent) {

        this.activity = activity;
        this.displayContent = displayContent;
    }

    public void popUpWindowGenerator(){

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (inflater != null) {
            buildAndShowPopUpWindow(inflater);
        }
    }

    private void buildAndShowPopUpWindow(LayoutInflater inflater) {

        View popUpLayout = inflater.inflate(R.layout.popup_info, activity.findViewById(R.id.popUp1));

        TextView displayAll = popUpLayout.findViewById(R.id.display_monitoring_info);
        displayAll.setText(displayContent);

        PopupWindow showWindow =
                new PopupWindow(popUpLayout, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT, true);

        showWindow.showAtLocation(popUpLayout, Gravity.CENTER, DIMENSION_VALUE, DIMENSION_VALUE);

        Button closeButton = popUpLayout.findViewById(R.id.close_pop_up);
        closeButton.setOnClickListener((View v) -> showWindow.dismiss());
    }

}