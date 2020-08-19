package com.example.mashu.walkinggroup.controller.map.activity.shared.methods;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.map.activity.location.methods.LocationPermission;
import com.example.mashu.walkinggroup.controller.toast.ToastDisplay;

/**
 * The EvaluatePermissionResult class executes a callback function which runs when
 * a response made for requesting permissions is received.
 *
 * Permissions is the string of permissions passed in to requestPermissions, requestCode is the
 * request code passed in, and grantResults is the result of whether or not they allowed access
 * to the feature for it's corresponding requestPermission.
 *
 * Also, this class sets the variable stored in checkSelfPermission(context, permission) to
 * PERMISSION_GRANTED or PERMISSION_DENIED depending on result in grantResults[].
 * Override onRequestPermissionsResult to process permission result
 */

public class EvaluatePermissionResult {

    private Activity activity;


    public EvaluatePermissionResult(Activity activity) {
        this.activity = activity;
    }

    public void eval(String[] permissions, int[] grantResults){

        boolean isPermissionGranted = permissions.length == 1 &&
                                      permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                                      grantResults[0] == PackageManager.PERMISSION_GRANTED;

        evaluatePermissionsResult(isPermissionGranted);
    }

    private void evaluatePermissionsResult(boolean isPermissionGranted) {

        if (isPermissionGranted) {
            permissionGrantedResponse();
        } else {
            permissionNotGrantedResponse();
        }
    }

    private void permissionGrantedResponse() {

        LocationPermission.setLocationPermissionGranted(true);

        ToastDisplay display = new ToastDisplay(activity);
        display.displayToast(R.string.permission_granted_message, Toast.LENGTH_LONG);
    }

    private void permissionNotGrantedResponse() {

        LocationPermission.setLocationPermissionGranted(false);

        ToastDisplay display = new ToastDisplay(activity);
        display.displayToast(R.string.permission_denied_message, Toast.LENGTH_LONG);

        activity.finish();
    }

}