package com.example.mashu.walkinggroup.controller.main.menu.activity.buttons.setup;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.ReceivePermissions;
import com.example.mashu.walkinggroup.controller.account.monitoring.activities.common.MonitorEnum;
import com.example.mashu.walkinggroup.controller.account.monitoring.activities.monitored.by.activity.MonitoredByActivity;
import com.example.mashu.walkinggroup.controller.account.monitoring.activities.monitors.activity.MonitorsActivity;
import com.example.mashu.walkinggroup.controller.edit.info.activity.EditInfoActivity;
import com.example.mashu.walkinggroup.controller.permissions.activity.PermissionsActivity;
import com.example.mashu.walkinggroup.controller.toast.ToastDisplay;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

/**
 * The CenterButtonSetup class handles setting up
 * the long buttons in the center of the main menu.
 */

public class CenterButtonSetup {

    private Activity activity;


    public CenterButtonSetup(Activity activity) {
        this.activity = activity;
    }

    public void setup(){
        setupCenterMenuButtons();
    }

    private void setupCenterMenuButtons() {

        setupAccountMonitoringButtons();
        setupEditMyInfoBtn();
        setupViewPendingRequestBtn();
    }

    private void setupAccountMonitoringButtons() {

        Button monitorsBtn = activity.findViewById(R.id.monitors_btn);
        monitorsBtn.setOnClickListener((View view) -> onClickResponse(MonitorEnum.MONITORS_ACTIVITY));

        Button monitoredByBtn = activity.findViewById(R.id.monitored_by_btn);
        monitoredByBtn.setOnClickListener((View view) -> onClickResponse(MonitorEnum.MONITORED_BY_ACTIVITY));
    }

    private void onClickResponse(MonitorEnum monitorEnum) {

        if(ProxyManager.getToken().isEmpty()){

            ToastDisplay display = new ToastDisplay(activity);
            display.displayToast(R.string.user_not_logged_in, Toast.LENGTH_SHORT);

        } else {

            startAppropriateActivity(monitorEnum);

        }
    }

    private void startAppropriateActivity(MonitorEnum monitorEnum) {

        if (monitorEnum == MonitorEnum.MONITORS_ACTIVITY) {
            activity.startActivity(MonitorsActivity.makeIntent(activity));
        } else if (monitorEnum == MonitorEnum.MONITORED_BY_ACTIVITY) {
            activity.startActivity(MonitoredByActivity.makeIntent(activity));
        }
    }

    private void setupEditMyInfoBtn() {
        Button editMyInfoBtn = activity.findViewById(R.id.edit_info_btn);
        editMyInfoBtn.setOnClickListener((View v) -> onEditInfoButtonClicked());
    }

    private void onEditInfoButtonClicked() {
        activity.startActivity(EditInfoActivity.makeIntent(activity, CurrentUserManager.getCurrentUser().getId(), false));
    }

    private void setupViewPendingRequestBtn() {
        Button viewPendingRequestsBtn = activity.findViewById(R.id.view_pending_requests_btn);
        viewPendingRequestsBtn.setOnClickListener((View v) -> onViewPendingRequestsBtnClicked());
    }

    private void onViewPendingRequestsBtnClicked() {
        activity.startActivity(ReceivePermissions.makeIntent(activity));
    }

}