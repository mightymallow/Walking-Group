package com.example.mashu.walkinggroup.controller.rewardsmanagement;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mashu.walkinggroup.R;
import com.example.mashu.walkinggroup.controller.shared.methods.CustomToolbar;
import com.example.mashu.walkinggroup.model.EarnedRewards;
import com.example.mashu.walkinggroup.model.Reward;
import com.example.mashu.walkinggroup.model.user.CurrentUserManager;
import com.example.mashu.walkinggroup.model.user.User;
import com.example.mashu.walkinggroup.proxy.ProxyBuilder;
import com.example.mashu.walkinggroup.proxy.ProxyManager;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;

public class OldRewardsActivity extends AppCompatActivity {

    private List<Reward> purchasedRewards;
    private User theUser;
    private EarnedRewards earnedRewards;
    private Reward activeReward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_rewards);

        CustomToolbar.setupCustomToolbar(this, R.id.old_rewards_custom_toolbar,
                getString(R.string.old_rewards_subtitle));

        initialInfoSetup();
        setupListViews();

    }

    private void initialInfoSetup(){
        theUser = CurrentUserManager.getCurrentUser();
        earnedRewards = theUser.getRewards();
        purchasedRewards = earnedRewards.getUnlockedRewards();
        activeReward = earnedRewards.getCurrentAvatar();
        updateTitle();
    }

    private void updateTitle(){
        String tempTitle = activeReward.getRewardTitle();
        String newTitle;
        if (tempTitle.equalsIgnoreCase("null")){
            newTitle = "You do not currently have an avatar!";
        } else {
            newTitle = "Your current avatar is the " + tempTitle;
        }

        TextView titleTxt = (TextView) findViewById(R.id.current_avatar_title);
        titleTxt.setText(newTitle);
    }

    private void setupListViews(){
        ListView unlockedRewards = (ListView) findViewById(R.id.unlocked_rewards);
        ArrayAdapter<Reward> adapter = new myListAdapter();
        unlockedRewards.setAdapter(adapter);
    }

    private class myListAdapter extends ArrayAdapter<Reward>{
        public myListAdapter(){
            super(OldRewardsActivity.this,R.layout.icon_view, purchasedRewards);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View itemView = convertView;
            if (itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.icon_view, parent, false);
            }

            Reward currentReward = purchasedRewards.get(position);

            ImageView avatarIcon = (ImageView) itemView.findViewById(R.id.icon_avatar);
            avatarIcon.setImageResource(currentReward.getIconID());

            TextView titleText = (TextView) itemView.findViewById(R.id.icon_reward_title);
            titleText.setText(currentReward.getRewardTitle());

            TextView costText = (TextView) itemView.findViewById(R.id.icon_point_cost);
            String temp = currentReward.getPointsCost() + " points";
            costText.setText(temp);

            ListView unlockedRewards = (ListView) findViewById(R.id.unlocked_rewards);
            unlockedRewards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                        Toast.makeText(OldRewardsActivity.this,"You have activated the " + purchasedRewards.get(position).getRewardTitle() + " reward!", Toast.LENGTH_LONG).show();
                        Reward tempReward = (Reward) parent.getAdapter().getItem(position);
                        activeReward = tempReward;
                        earnedRewards.setCurrentAvatar(activeReward);
                        updateTitle();
                        Call<User> caller = ProxyManager.getProxy(OldRewardsActivity.this).updateUser(theUser.getId(), theUser);
                        ProxyBuilder.callProxy(OldRewardsActivity.this, caller, this::serverMessage);

                }

                private void serverMessage(User user) { }
            });

            return itemView;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public static Intent makeIntent(Context context){
        return new Intent(context, OldRewardsActivity.class);
    }
}
