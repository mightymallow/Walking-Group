package com.example.mashu.walkinggroup.controller.edit.info.activity.view.setup;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.model.user.User;

/**
 * The ExtraEditTextInfo class handles displaying
 * the extra content on the extra edit texts.
 */

public class ExtraEditTextInfo {

    private Activity activity;
    private User userOfInterest;


    public ExtraEditTextInfo(Activity activity, User userOfInterest) {
        this.activity = activity;
        this.userOfInterest = userOfInterest;
    }

    public void displayExtraEditInfo() {

        LinearLayout currentGradeLayout = activity.findViewById(R.id.current_grade_layout);
        currentGradeLayout.setVisibility(View.VISIBLE);

        EditText gradeEditText = activity.findViewById(R.id.current_grade_editText);
        gradeEditText.setText(userOfInterest.getGrade());

        LinearLayout currentTeacherNameLayout = activity.findViewById(R.id.current_teacher_name_layout);
        currentTeacherNameLayout.setVisibility(View.VISIBLE);

        EditText teacherNameEditText = activity.findViewById(R.id.current_teacher_name_editText);
        teacherNameEditText.setText(userOfInterest.getTeacherName());

        LinearLayout currentEmergencyContactInfoLayout = activity.findViewById(R.id.current_emergency_contact_info_layout);
        currentEmergencyContactInfoLayout.setVisibility(View.VISIBLE);

        EditText emergencyEditText = activity.findViewById(R.id.current_emergency_contact_info_editText);
        emergencyEditText.setText(userOfInterest.getEmergencyContactInfo());
    }

}