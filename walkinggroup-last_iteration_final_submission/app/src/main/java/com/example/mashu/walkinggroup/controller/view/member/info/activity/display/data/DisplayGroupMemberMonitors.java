package com.example.mashu.walkinggroup.controller.view.member.info.activity.display.data;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.model.user.User;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import java.util.List;

import retrofit2.Call;

/**
 * The DisplayGroupMemberMonitors class is responsible for setting
 * up the views for the group members monitors of the clicked group
 * in the ViewMemberInfoActivity.
 */

public class DisplayGroupMemberMonitors {

    private Activity activity;
    private List<User> validUsersToDisplay;
    private String displayContent;
    private int numUserMonitors;
    private int counter;


    public DisplayGroupMemberMonitors(Activity activity, List<User> validUsersToDisplay) {

        this.activity = activity;
        this.validUsersToDisplay = validUsersToDisplay;
    }

    public void setupListViewForClickedGroupMemberAndLeaderMonitors() {

        ListView viewUserInfoListView = activity.findViewById(R.id.view_user_info_listView);

        viewUserInfoListView.setOnItemClickListener(
            (AdapterView<?> parent, View view, int position, long id) -> {

                if (doesUserAtPositionHaveMonitors(position)) {

                    List<User> userMonitors = validUsersToDisplay.get(position).getMonitoredByUsers();
                    displayContent = "Parent Information:";
                    numUserMonitors = userMonitors.size();
                    counter = 0;

                    retrieveFullUserMonitorsData(userMonitors);
                }
            }
        );

    }

    private boolean doesUserAtPositionHaveMonitors(int position) {
        return validUsersToDisplay.get(position).getMonitoredByUsers().size() > 0;
    }

    private void retrieveFullUserMonitorsData(List<User> listOfMonitors){

        for(User currentMonitor : listOfMonitors){

            Call<User> caller = ProxyManager.getProxy(activity).getUserById(currentMonitor.getId());
            ProxyBuilder.callProxy(activity, caller, this::onMonitoringUserReceived);
        }
    }

    private void onMonitoringUserReceived (User userOfInterest){
        createStringToDisplayMonitorUsers(userOfInterest);
    }

    private void createStringToDisplayMonitorUsers(User userOfInterest) {

        displayContent = displayContent + "\n\n" + formatUserDetails(userOfInterest);
        counter++;

        if (areAllMonitorsAddedToDisplayContent()){

            GeneratePopUpWindow generate = new GeneratePopUpWindow(activity, displayContent);
            generate.popUpWindowGenerator();
        }
    }

    private String formatUserDetails(User userOfInterest){

        return activity.getString(R.string.format_user_details, userOfInterest.getName(), userOfInterest.getEmail(),
                                                                userOfInterest.getAddress(), userOfInterest.getCellPhone(),
                                                                userOfInterest.getHomePhone());
    }

    private boolean areAllMonitorsAddedToDisplayContent() {
        return numUserMonitors == counter;
    }

}