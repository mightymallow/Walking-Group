package com.example.mashu.walkinggroup.controller.shared.methods;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.map.activity.walking.group.methods.setup.create.button.functionality.SetupCreateButton;
import com.example.mashu.walkinggroup.controller.toast.ToastDisplay;

/**
 * The AnimationBuilder class is responsible for building
 * animations and returning them.
 */

public class AnimationBuilder {

    private static final int FADE_IN_DURATION = 2500;
    private static final int FADE_OUT_DURATION = 625;
    private static final int INVISIBLE = 0;
    private static final int VISIBLE = 1;

    private Activity activity;


    public AnimationBuilder(Activity activity) {
        this.activity = activity;
    }

    public Animation fadeInCreateGroupImageView(){

        Animation fadeIn = new AlphaAnimation(INVISIBLE, VISIBLE);

        fadeIn.setDuration(FADE_IN_DURATION);

        fadeIn.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {

                ToastDisplay display = new ToastDisplay(activity);
                display.displayToast(R.string.add_name, Toast.LENGTH_LONG);

                SetupCreateButton setup = new SetupCreateButton((FragmentActivity) activity);
                setup.setupCreateGroupButton();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        return fadeIn;
    }

    public Animation fadeOut(ImageView imageViewToMakeVisible){

        Animation fadeOut = new AlphaAnimation(VISIBLE, INVISIBLE);
        fadeOut.setDuration(FADE_OUT_DURATION);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                makeViewVisible(imageViewToMakeVisible);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        return fadeOut;
    }

    private void makeViewVisible(ImageView imageViewToMakeVisible) {
        imageViewToMakeVisible.setVisibility(View.VISIBLE);
    }

}