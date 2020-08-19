package com.example.mashu.walkinggroup.controller.map.activity.location.methods.location.search.bar;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.mashu.walkinggroup.R;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The OnEditProcess handles the edit action listener
 * process for both edit texts in the MapActivity.
 */

public class OnEditProcess {

    private final static int MAX_RESULTS = 1;

    private Activity activity;
    private LocationSearchBar locationSearchBar;


    public OnEditProcess(Activity activity, LocationSearchBar locationSearchBar) {

        this.activity = activity;
        this.locationSearchBar = locationSearchBar;
    }

    public boolean onEdit(EditText locationEditText, int actionId, KeyEvent keyEvent, boolean isOriginEditText) {

        if (areProperActionsTakenOnEditText(actionId, keyEvent) && isOriginEditText) {
            locationSearchBar.updateOrigin(convertUserInputAddressToLatLng(locationEditText.getText().toString()));
        } else if(areProperActionsTakenOnEditText(actionId, keyEvent)){
            locationSearchBar.updateDestination(convertUserInputAddressToLatLng(locationEditText.getText().toString()));
        }

        if(isOriginEditText) {
            hideKeyboardWhenBothInputsFilled(R.id.destination_editText, locationEditText, actionId);
        } else {
            hideKeyboardWhenBothInputsFilled(R.id.origin_editText, locationEditText, actionId);
        }

        locationSearchBar.onOriginDestinationUpdated();

        return false;
    }

    private boolean areProperActionsTakenOnEditText(int actionId, KeyEvent keyEvent) {

        return actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT ||
                keyEvent.getAction() == KeyEvent.ACTION_DOWN || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER;
    }

    private void hideKeyboardWhenBothInputsFilled(int otherInputID, EditText lastEditedInput, int actionId) {

        EditText otherInput = activity.findViewById(otherInputID);
        InputMethodManager manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        if(actionId == EditorInfo.IME_ACTION_NEXT && !otherInput.getText().toString().isEmpty() && manager != null){
            manager.hideSoftInputFromWindow(lastEditedInput.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
    }

    private LatLng convertUserInputAddressToLatLng(String addressFromUserInput) {

        Geocoder geocoder = new Geocoder(activity);
        List<Address> listOfAddresses = new ArrayList<>();

        try {
            listOfAddresses = geocoder.getFromLocationName(addressFromUserInput, MAX_RESULTS);
        } catch (IOException e) {
            Log.e(activity.getString(R.string.exception), e.getMessage());
        }

        return extractSingleAddressInList(listOfAddresses);
    }

    private LatLng extractSingleAddressInList(List<Address> listOfAddresses) {

        LatLng addressCoordinates = null;

        if (listOfAddresses.size() > 0) {

            Address address = listOfAddresses.get(0);
            addressCoordinates = new LatLng(address.getLatitude(), address.getLongitude());
        }

        return addressCoordinates;
    }

}