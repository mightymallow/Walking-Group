package com.example.mashu.walkinggroup.controller.edit.info.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.edit.info.activity.view.setup.EditTextInfo;
import com.example.mashu.walkinggroup.controller.edit.info.activity.view.setup.ExtraEditTextInfo;
import com.example.mashu.walkinggroup.controller.login.activity.user.info.storage.UserInfoStorage;
import com.example.mashu.walkinggroup.controller.shared.methods.CustomToolbar;
import com.example.mashu.walkinggroup.controller.shared.methods.DetermineUserType;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.model.user.User;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import retrofit2.Call;

public class EditInfoActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.mashu.walkinggroup.controller.edit.info.activity - userID";
    private static final String FOR_MONITORS_KEY = "com.example.mashu.walkinggroup.controller.edit.info.activity - for Monitors";
    private static final long DEFAULT_LONG = 0;
    private static final boolean DEFAULT_BOOLEAN = false;

    private User userOfInterest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        CustomToolbar.setupCustomToolbar(this, R.id.edit_info_custom_toolbar, getString(R.string.edit_info_subtitle));

        Long userID = getIntent().getLongExtra(USER_ID_KEY, DEFAULT_LONG);

        if(userID != DEFAULT_LONG){
            getUserFromServer(userID);
        }
    }

    private void getUserFromServer(Long userID) {

        Call<User> caller = ProxyManager.getProxy(this).getUserById(userID);
        ProxyBuilder.callProxy(this, caller, this::onUserOfInterestReceived);
    }

    private void onUserOfInterestReceived(User userOfInterest) {

        this.userOfInterest = userOfInterest;

        if(userOfInterest != null) {

            DetermineUserType determineUserType = new DetermineUserType(userOfInterest);
            determineUserType.updateCurrentUserType();

            configureEditInfoOptions();

            EditTextInfo editTextInfo = new EditTextInfo(EditInfoActivity.this, userOfInterest);
            editTextInfo.updateEditTextUserInfo();

            setupSaveBtn();
        }
    }

    private void configureEditInfoOptions() {

        if(userOfInterest.getUserType() == CurrentUserTypeEnum.CHILD){

            ExtraEditTextInfo extraInfo = new ExtraEditTextInfo(EditInfoActivity.this, userOfInterest);
            extraInfo.displayExtraEditInfo();

        }
    }

    private void setupSaveBtn() {

        Button saveBtn = findViewById(R.id.save_btn);
        saveBtn.setOnClickListener((View view) -> {

            EditText usernameEditText = findViewById(R.id.current_user_name_editText);
            CurrentUserManager.getCurrentUser().setName(usernameEditText.getText().toString());

            UserInfoStorage userInfoStorage = new UserInfoStorage(EditInfoActivity.this);
            userInfoStorage.saveUserInfo();

            EnsureCorrectInputs ensure = new EnsureCorrectInputs(EditInfoActivity.this, userOfInterest);
            ensure.onSave();
        });
    }

    public static Intent makeIntent(Context context, Long userID, boolean isChildEdit){

        Intent intent = new Intent(context, EditInfoActivity.class);
        intent.putExtra(USER_ID_KEY, userID);
        intent.putExtra(FOR_MONITORS_KEY, isChildEdit);

        return intent;
    }

    public static boolean isMonitorsUpdated(Intent intent){
        return intent.getBooleanExtra(FOR_MONITORS_KEY, DEFAULT_BOOLEAN);
    }

}