package com.example.mashu.walkinggroup.controller.main.menu.activity.tracking;

import android.app.Activity;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.main.menu.activity.tracking.click.listeners.StartGpsUpload;
import com.example.mashu.walkinggroup.controller.toast.ToastDisplay;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;

/**
 * The GPSTracking class handles setting up the GPS tracking
 * for the Map Activity.
 */

public class GPSTracking {

    private static final int MINIMUM_SIZE = 0;

    private Activity activity;
    private LocationUpdater locationUpdater;


    public GPSTracking(Activity activity) {

        this.activity = activity;
        this.locationUpdater = new LocationUpdater(activity);
    }

    public void checkForCurrentUserInvolvementWithGroup() {

        if (isCurrentUserAGroupLeader() || isCurrentUserAGroupMember()) {

            StartGpsUpload.setIsClicked(true);
            getCurrentLocation();
            locationUpdater.updateLastKnownLocation();

        } else {

            ToastDisplay display = new ToastDisplay(activity);
            display.displayToast(R.string.currently_not_in_a_group, Toast.LENGTH_LONG);

        }
    }

    private void getCurrentLocation() {
        try {

            Task<Location> locationResult = LocationServices.getFusedLocationProviderClient(activity)
                    .getLastLocation();

            locationResult.addOnSuccessListener(activity, this::setUserStartingPoint);

        } catch(SecurityException securityException)  {

            Log.e(activity.getString(R.string.exception), securityException.getMessage());

        }
    }

    private void setUserStartingPoint(Location location) {

        LatLng userStartingPoint = new LatLng(location.getLatitude(), location.getLongitude());
        ConfirmUserLocation.setUserStartingPointOnStartGPS(userStartingPoint);
    }

    private boolean isCurrentUserAGroupLeader() {
        return CurrentUserManager.getCurrentUser().getLeadsGroups().size() > MINIMUM_SIZE;
    }

    private boolean isCurrentUserAGroupMember() {
        return CurrentUserManager.getCurrentUser().getMemberOfGroups().size() > MINIMUM_SIZE;
    }

}





