package com.example.mashu.walkinggroup.controller.edit.info.activity.view.setup;

import android.app.Activity;
import android.widget.EditText;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.model.user.User;

/**
 * The EditTextInfo class handles displaying
 * the content on the standard edit texts.
 */

public class EditTextInfo {

    private Activity activity;
    private User userOfInterest;


    public EditTextInfo(Activity activity, User userOfInterest) {
        this.activity = activity;
        this.userOfInterest = userOfInterest;
    }

    public void updateEditTextUserInfo() {

        UpdateFieldsWithValidations updateFieldsWithValidations = new UpdateFieldsWithValidations(activity, userOfInterest);
        updateFieldsWithValidations.updateFields();

        updateAddressInfo();
        updateCellPhoneInfo();
        updateHomePhoneInfo();
    }

    private void updateAddressInfo() {

        EditText addressEditText = activity.findViewById(R.id.current_address_editText);
        addressEditText.setText(userOfInterest.getAddress());
    }

    private void updateCellPhoneInfo() {

        EditText cellPhoneEditText = activity.findViewById(R.id.current_cell_phone_editText);
        cellPhoneEditText.setText(userOfInterest.getCellPhone());
    }

    private void updateHomePhoneInfo() {

        EditText homePhoneEditText = activity.findViewById(R.id.current_home_phone_editText);
        homePhoneEditText.setText(userOfInterest.getHomePhone());
    }

}