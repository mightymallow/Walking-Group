package com.example.mashu.walkinggroup.controller.messenger.activity.button.setup;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.model.Group;

/**
 * The SetupSpinner class is used to setup the
 * spinner for the broadcast messages.
 */

public class SetupSpinner {

    private Activity activity;
    private int indexSelected;


    public SetupSpinner(Activity activity) {
        this.activity = activity;
    }

    public int setup(Spinner listOfGroups){

        int numberOfGroups = CurrentUserManager.getCurrentUser().getLeadsGroups().size();
        String[] strings = new String[numberOfGroups];

        for(int i = 0; i < numberOfGroups; i++){

            Group group = CurrentUserManager.getCurrentUser().getLeadsGroups().get(i);

            if(group.getGroupDescription() == null){
                strings[i] = group.getId().toString();
            } else {
                strings[i] = group.getGroupDescription();
            }
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_dropdown_item, strings);
        listOfGroups.setAdapter(spinnerAdapter);

        listOfGroups.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                indexSelected = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });


        return indexSelected;
    }

}