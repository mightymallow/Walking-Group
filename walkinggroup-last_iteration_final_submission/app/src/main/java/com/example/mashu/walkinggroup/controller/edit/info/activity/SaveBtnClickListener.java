package com.example.mashu.walkinggroup.controller.edit.info.activity;

import android.app.Activity;
import android.content.Intent;
import android.widget.EditText;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.account.monitoring.activities.monitors.activity.EditUser;
import com.example.mashu.walkinggroup.controller.account.monitoring.activities.monitors.activity.MonitorsActivity;
import com.example.mashu.walkinggroup.controller.toast.ToastDisplay;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.model.user.User;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import retrofit2.Call;

/**
 * The SaveBtnClickListener class handles the save button on click listener
 * for the Edit Info Activity.
 */

public class SaveBtnClickListener {

    private Activity activity;
    private User userOfInterest;


    public SaveBtnClickListener(Activity activity, User userOfInterest) {

        this.activity = activity;
        this.userOfInterest = userOfInterest;
    }

    public void updateUserOfInterestInfo(){

        if(userOfInterest.getUserType() == CurrentUserTypeEnum.CHILD){
            saveChildInfo();
        }

        saveOtherUserInfo();
    }

    private void saveChildInfo() {

        EditText gradeEditText = activity.findViewById(R.id.current_grade_editText);
        userOfInterest.setGrade(gradeEditText.getText().toString());

        EditText teacherNameEditText = activity.findViewById(R.id.current_teacher_name_editText);
        userOfInterest.setTeacherName(teacherNameEditText.getText().toString());

        EditText emergencyEditText = activity.findViewById(R.id.current_emergency_contact_info_editText);
        userOfInterest.setEmergencyContactInfo(emergencyEditText.getText().toString());
    }

    private void saveOtherUserInfo() {

        EditText usernameEditText = activity.findViewById(R.id.current_user_name_editText);
        userOfInterest.setName(usernameEditText.getText().toString());

        EditText addressEditText = activity.findViewById(R.id.current_address_editText);
        userOfInterest.setAddress(addressEditText.getText().toString());

        EditText cellPhoneEditText = activity.findViewById(R.id.current_cell_phone_editText);
        userOfInterest.setCellPhone(cellPhoneEditText.getText().toString());

        EditText homePhoneEditText = activity.findViewById(R.id.current_home_phone_editText);
        userOfInterest.setHomePhone(homePhoneEditText.getText().toString());

        updateUserInfoOnServer();
    }

    private void updateUserInfoOnServer() {

        Call<User> caller = ProxyManager.getProxy(activity).updateUser(userOfInterest.getId(), userOfInterest);
        ProxyBuilder.callProxy(activity, caller, this::OnCurrentUserUpdated);
    }

    private void OnCurrentUserUpdated(User currentUserUpdated) {

        updateCurrentUserIfNeeded();

        if(EditInfoActivity.isMonitorsUpdated(activity.getIntent())){

            EditUser.updateMonitorsUsers();

            Intent intent = MonitorsActivity.addNameExtraToIntent(userOfInterest);

            activity.setResult(Activity.RESULT_OK, intent);

        } else {

            ToastDisplay display = new ToastDisplay(activity);
            display.displayUserEditedToast(userOfInterest.getName());

        }

        activity.finish();
    }

    private void updateCurrentUserIfNeeded() {

        if(userOfInterest.getId().equals(CurrentUserManager.getCurrentUser().getId())){
            CurrentUserManager.setCurrentUser(userOfInterest);
        }
    }

}