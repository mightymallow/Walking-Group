package com.example.mashu.walkinggroup.controller.shared.methods;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.main.menu.activity.tracking.ConfirmUserLocation;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.model.Group;
import com.example.mashu.walkinggroup.model.user.User;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import retrofit2.Call;

public class PointsManager {

    private static final int DEFAULT_POINTS_TO_GIVE = 50;
    private static final int INITIAL_POINTS_TO_GIVE = 100;
    private static final int POINTS_FACTOR = 2;

    private Activity activity;
    private Group currentUserGroup;
    private Integer pointsToGive;


    public PointsManager(Activity activity, Group currentUserGroup) {

        this.activity = activity;
        this.currentUserGroup = currentUserGroup;
    }

    public void addToCurrentUserEarnedPointsAndUpdateServer() {

        pointsToGive = calculatePointsToGive(currentUserGroup);

        updateCurrentUserPoints(pointsToGive);

        Call<User> caller = ProxyManager.getProxy(activity)
                .updateUser(CurrentUserManager.getCurrentUser().getId(),
                        CurrentUserManager.getCurrentUser());

        ProxyBuilder.callProxy(activity, caller, this::onUserReturnedListener);
    }

    private Integer calculatePointsToGive(Group group) {

        Integer pointsToGive = DEFAULT_POINTS_TO_GIVE;

        if (isFirstTimeCurrentUserIsGettingPoints()) {

            CurrentUserManager.getCurrentUser().setCurrentPoints(0);
            CurrentUserManager.getCurrentUser().setTotalPointsEarned(0);
            pointsToGive += INITIAL_POINTS_TO_GIVE;
        }

        if (isCurrentUserGroupAnActualGroup(group)) {

            if (isCurrentUserTheGroupLeader(group)) {
                pointsToGive += DEFAULT_POINTS_TO_GIVE;
            }

            pointsToGive += giveUserPointsProportionalToGroupSize(group);
        }

        return pointsToGive;
    }

    private boolean isFirstTimeCurrentUserIsGettingPoints() {
        return CurrentUserManager.getCurrentUser().getCurrentPoints() == null ||
                CurrentUserManager.getCurrentUser().getTotalPointsEarned() == null;
    }

    private boolean isCurrentUserGroupAnActualGroup(Group group) {
        return group.getGroupDescription() != null;
    }

    private void updateCurrentUserPoints(Integer pointsToGive) {

        Integer currentPoints = CurrentUserManager.getCurrentUser().getCurrentPoints();
        Integer totalPoints = CurrentUserManager.getCurrentUser().getTotalPointsEarned();
        CurrentUserManager.getCurrentUser().setCurrentPoints(currentPoints + pointsToGive);
        CurrentUserManager.getCurrentUser().setTotalPointsEarned(totalPoints + pointsToGive);
    }

    private void onUserReturnedListener(User user) {

        Toast.makeText(activity, activity.getString(R.string.points_given, user.getName(), pointsToGive), Toast.LENGTH_SHORT)
             .show();

        TextView currentPointsTextView = activity.findViewById(R.id.current_points_text_view);
        currentPointsTextView.setText("Current Points: " + CurrentUserManager.getCurrentUser().getCurrentPoints());
    }

    private boolean isCurrentUserTheGroupLeader(Group group) {
        return CurrentUserManager.getCurrentUser().getId().equals(group.getLeader().getId());
    }

    private int giveUserPointsProportionalToGroupSize(Group group) {
        return group.getMemberUsers().size() * POINTS_FACTOR;
    }

}