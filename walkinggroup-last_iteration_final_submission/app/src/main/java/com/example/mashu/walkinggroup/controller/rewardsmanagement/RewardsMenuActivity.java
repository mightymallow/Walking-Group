package com.example.mashu.walkinggroup.controller.rewardsmanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class RewardsMenuActivity extends AppCompatActivity {

    private List<Reward> unpurchasedRewards;
    private List<Reward> purchasedRewards;
    private User theUser;
    private EarnedRewards earnedRewards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards_menu);

        CustomToolbar.setupCustomToolbar(this, R.id.member_info_custom_toolbar,
                getString(R.string.view_rewards_menu_subtitle));

        initialInfoSetup();
        setupListViews();
        setupButtons();
    }

    private void initialInfoSetup(){

        theUser = CurrentUserManager.getCurrentUser();
        updatePoints();
        createRewards();
    }

    private void createRewards(){

        if (theUser.getRewards() == null){

            Reward reward1 = new Reward("Commoner", R.drawable.knight1_icon, 200,1);
            Reward reward2 = new Reward("Chrysalis Warrior", R.drawable.knight2_icon, 1000,2);
            Reward reward3 = new Reward("True Great One", R.drawable.knight3_icon, 5000,3);
            Reward reward4 = new Reward("Blind Bowman", R.drawable.archer1_icon, 200,4);
            Reward reward5 = new Reward("Elven Entity", R.drawable.archer2_icon, 1000,5);
            Reward reward6 = new Reward("Master Nomad", R.drawable.archer3_icon, 5000,6);
            Reward reward7 = new Reward("Talented Thief", R.drawable.assassin1_icon, 200,7);
            Reward reward8 = new Reward("Unseeable Ninja", R.drawable.assassin2_icon, 1000,8);
            Reward reward9 = new Reward("Hand of the Shadow", R.drawable.assassin3_icon, 5000,9);
            Reward reward10 = new Reward("Dumbfounded Dwarf", R.drawable.dwarf1_icon, 200,10);
            Reward reward11 = new Reward("Mad Powerhouse",R.drawable.dwarf2_icon, 1000,11);
            Reward reward12 = new Reward("Blaster Destroyer",R.drawable.dwarf3_icon, 5000,12);
            Reward reward13 = new Reward("Gooey Goblin",R.drawable.goblin1_icon, 200,13);
            Reward reward14 = new Reward("Frontier Walker",R.drawable.goblin2_icon, 1000,14);
            Reward reward15 = new Reward("Hacker Thomson",R.drawable.goblin3_icon, 5000,15);
            Reward reward16 = new Reward("Outrageous Orc",R.drawable.orc1_icon, 200,16);
            Reward reward17 = new Reward("Dream Crusher",R.drawable.orc2_icon, 1000,17);
            Reward reward18 = new Reward("Quest Obliterator",R.drawable.orc3_icon, 5000,18);
            Reward reward19 = new Reward("Waste of Skin",R.drawable.skeleton1_icon, 200,19);
            Reward reward20 = new Reward("Hollow Spirit",R.drawable.skeleton2_icon, 1000,20);
            Reward reward21 = new Reward("Reaper of Remains",R.drawable.skeleton3_icon, 5000,21);
            Reward reward22 = new Reward("Wooly Wizard",R.drawable.wizard1_icon, 200,22);
            Reward reward23 = new Reward("Magician of Flames",R.drawable.wizard2_icon, 1000,23);
            Reward reward24 = new Reward("High Priest of the Sun",R.drawable.wizard3_icon, 5000,24);

            unpurchasedRewards = new ArrayList<Reward>();
            purchasedRewards = new ArrayList<Reward>();
            unpurchasedRewards.add(reward1);
            unpurchasedRewards.add(reward2);
            unpurchasedRewards.add(reward3);
            unpurchasedRewards.add(reward4);
            unpurchasedRewards.add(reward5);
            unpurchasedRewards.add(reward6);
            unpurchasedRewards.add(reward7);
            unpurchasedRewards.add(reward8);
            unpurchasedRewards.add(reward9);
            unpurchasedRewards.add(reward10);
            unpurchasedRewards.add(reward11);
            unpurchasedRewards.add(reward12);
            unpurchasedRewards.add(reward13);
            unpurchasedRewards.add(reward14);
            unpurchasedRewards.add(reward15);
            unpurchasedRewards.add(reward16);
            unpurchasedRewards.add(reward17);
            unpurchasedRewards.add(reward18);
            unpurchasedRewards.add(reward19);
            unpurchasedRewards.add(reward20);
            unpurchasedRewards.add(reward21);
            unpurchasedRewards.add(reward22);
            unpurchasedRewards.add(reward23);
            unpurchasedRewards.add(reward24);

            theUser.setRewards(new EarnedRewards());
            theUser.getRewards().setCurrentAvatar(new Reward("null",0,0, 0));
            theUser.getRewards().setLockedRewards(unpurchasedRewards);
            theUser.getRewards().setUnlockedRewards(purchasedRewards);

        } else {
            earnedRewards = theUser.getRewards();
            unpurchasedRewards = earnedRewards.getLockedRewards();
            purchasedRewards = earnedRewards.getUnlockedRewards();
        }


    }

    private void setupListViews() {
        ListView lockedRewards = (ListView) findViewById(R.id.locked_rewards);
        ArrayAdapter<Reward> adapter = new myListAdapter();
        lockedRewards.setAdapter(adapter);
    }

    private class myListAdapter extends ArrayAdapter<Reward>{
        public myListAdapter(){
            super(RewardsMenuActivity.this,R.layout.icon_view, unpurchasedRewards);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View itemView = convertView;
            if (itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.icon_view, parent, false);
            }

            Reward currentReward = unpurchasedRewards.get(position);

            ImageView avatarIcon = (ImageView) itemView.findViewById(R.id.icon_avatar);
            avatarIcon.setImageResource(currentReward.getIconID());

            TextView titleText = (TextView) itemView.findViewById(R.id.icon_reward_title);
            titleText.setText(currentReward.getRewardTitle());

            TextView costText = (TextView) itemView.findViewById(R.id.icon_point_cost);
            String temp = currentReward.getPointsCost() + " points";
            costText.setText(temp);

            ListView lockedRewards = (ListView) findViewById(R.id.locked_rewards);
            lockedRewards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                    if (theUser.getCurrentPoints() != null && unpurchasedRewards.get(position).getPointsCost() < theUser.getCurrentPoints()){

                        Toast.makeText(RewardsMenuActivity.this,"You have bought the " + unpurchasedRewards.get(position).getRewardTitle() + " reward!", Toast.LENGTH_LONG).show();
                        theUser.setCurrentPoints(theUser.getCurrentPoints() - unpurchasedRewards.get(position).getPointsCost());
                        updatePoints();
                        Reward tempReward = (Reward) parent.getAdapter().getItem(position);
                        purchasedRewards.add(tempReward);
                        unpurchasedRewards.remove(position);
                        notifyDataSetChanged();
                        Call<User> caller = ProxyManager.getProxy(RewardsMenuActivity.this).updateUser(theUser.getId(), theUser);
                        ProxyBuilder.callProxy(RewardsMenuActivity.this, caller, this::serverMessage);

                    } else {
                        Toast.makeText(RewardsMenuActivity.this,"You don't have enough points to buy that!",Toast.LENGTH_LONG).show();
                    }

                }

                private void serverMessage(User user) {}

            });

            return itemView;
        }
    }

    private void updatePoints(){

        TextView pointsDisplay = (TextView) findViewById(R.id.current_points);

        if(theUser.getCurrentPoints() != null) {

            String temp = "" + theUser.getCurrentPoints();
            pointsDisplay.setText(temp);
        } else {
            pointsDisplay.setText("" + 0);
        }
    }

    private void setupButtons(){
        Button rewards = (Button) findViewById(R.id.rewardsBtn);
        Button avatar = (Button) findViewById(R.id.avatarBtn);

        rewards.setOnClickListener((View view) -> {

            Intent intent = OldRewardsActivity.makeIntent(this.getApplicationContext());
            this.startActivity(intent);

        });

        avatar.setOnClickListener((View view) -> {

            Intent intent = AvatarActivity.makeIntent(this.getApplicationContext());
            this.startActivity(intent);

        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public static Intent makeIntent(Context context){
        return new Intent(context, RewardsMenuActivity.class);
    }
}
