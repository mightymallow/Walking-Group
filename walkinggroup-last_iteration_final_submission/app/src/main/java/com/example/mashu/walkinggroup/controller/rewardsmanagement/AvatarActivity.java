package com.example.mashu.walkinggroup.controller.rewardsmanagement;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.shared.methods.CustomToolbar;
import com.example.mashu.walkinggroup.model.Reward;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;

public class AvatarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);

        CustomToolbar.setupCustomToolbar(this, R.id.member_info_custom_toolbar,
                getString(R.string.avatar_subtitle));

        setupDisplay();
    }

    private void setupDisplay(){

        Reward activeReward = CurrentUserManager.getCurrentUser().getRewards().getCurrentAvatar();
        updateImage(activeReward);
    }

    private void updateImage(Reward activeReward){

        int temp = activeReward.getRewardNumber();

        ImageView mainAvatar = (ImageView) findViewById(R.id.avatar);
        ImageView mainTitle = (ImageView) findViewById(R.id.custom_titles);

        switch(temp){
            case 1: mainAvatar.setImageResource(R.drawable.knight1_512);
                    mainTitle.setImageResource(R.drawable.commoner);
                    break;
            case 2: mainAvatar.setImageResource(R.drawable.knight2_512);
                    mainTitle.setImageResource(R.drawable.chrysalis);
                    break;
            case 3: mainAvatar.setImageResource(R.drawable.knight3_512);
                    mainTitle.setImageResource(R.drawable.truegreat);
                    break;
            case 4: mainAvatar.setImageResource(R.drawable.archer1_512);
                    mainTitle.setImageResource(R.drawable.blind);
                break;
            case 5: mainAvatar.setImageResource(R.drawable.archer2_512);
                    mainTitle.setImageResource(R.drawable.elven);
                break;
            case 6: mainAvatar.setImageResource(R.drawable.archer3_512);
                    mainTitle.setImageResource(R.drawable.master);
                break;
            case 7: mainAvatar.setImageResource(R.drawable.assassin1_512);
                    mainTitle.setImageResource(R.drawable.thief);
                break;
            case 8: mainAvatar.setImageResource(R.drawable.assassin2_512);
                    mainTitle.setImageResource(R.drawable.ninja);
                break;
            case 9: mainAvatar.setImageResource(R.drawable.assassin3_512);
                    mainTitle.setImageResource(R.drawable.shadow);
                break;
            case 10: mainAvatar.setImageResource(R.drawable.dwarf1_512);
                     mainTitle.setImageResource(R.drawable.dwarf);
                break;
            case 11: mainAvatar.setImageResource(R.drawable.dwarf2_512);
                     mainTitle.setImageResource(R.drawable.powerhouse);
                break;
            case 12: mainAvatar.setImageResource(R.drawable.dwarf3_512);
                     mainTitle.setImageResource(R.drawable.blaster);
                break;
            case 13: mainAvatar.setImageResource(R.drawable.goblin1_512);
                     mainTitle.setImageResource(R.drawable.gooey);
                break;
            case 14: mainAvatar.setImageResource(R.drawable.goblin2_512);
                     mainTitle.setImageResource(R.drawable.frontier);
                break;
            case 15: mainAvatar.setImageResource(R.drawable.goblin3_512);
                     mainTitle.setImageResource(R.drawable.hacker);
                break;
            case 16: mainAvatar.setImageResource(R.drawable.orc1_512);
                     mainTitle.setImageResource(R.drawable.orc);
                break;
            case 17: mainAvatar.setImageResource(R.drawable.orc2_512);
                     mainTitle.setImageResource(R.drawable.dream);
                break;
            case 18: mainAvatar.setImageResource(R.drawable.orc3_512);
                     mainTitle.setImageResource(R.drawable.quest);
                break;
            case 19: mainAvatar.setImageResource(R.drawable.skeleton1_512);
                     mainTitle.setImageResource(R.drawable.waste);
                break;
            case 20: mainAvatar.setImageResource(R.drawable.skeleton2_512);
                     mainTitle.setImageResource(R.drawable.hollow);
                break;
            case 21: mainAvatar.setImageResource(R.drawable.skeleton3_512);
                     mainTitle.setImageResource(R.drawable.reaper);
                break;
            case 22: mainAvatar.setImageResource(R.drawable.wizard1_512);
                     mainTitle.setImageResource(R.drawable.wooly);
                break;
            case 23: mainAvatar.setImageResource(R.drawable.wizard2_512);
                     mainTitle.setImageResource(R.drawable.magician);
                break;
            case 24: mainAvatar.setImageResource(R.drawable.wizard3_512);
                     mainTitle.setImageResource(R.drawable.high);
                break;
            default: mainAvatar.setImageResource(R.drawable.no_avatar);
                     mainTitle.setImageResource(R.drawable.select);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public static Intent makeIntent(Context context){
        return new Intent(context, AvatarActivity.class);
    }
}
