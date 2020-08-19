package com.example.mashu.walkinggroup.model;

public class Reward {

    private String rewardTitle;
    private int iconID;
    private int pointsCost;
    private int rewardNumber;


    public Reward() {}

    public Reward(String rewardTitle, int iconID, int pointsCost, int rewardNumber){

        this.rewardTitle = rewardTitle;
        this.iconID = iconID;
        this.pointsCost = pointsCost;
        this.rewardNumber = rewardNumber;
    }

    public String getRewardTitle() {
        return rewardTitle;
    }

    public int getIconID() {
        return iconID;
    }

    public int getPointsCost() {
        return pointsCost;
    }

    public int getRewardNumber(){
        return rewardNumber;
    }

}