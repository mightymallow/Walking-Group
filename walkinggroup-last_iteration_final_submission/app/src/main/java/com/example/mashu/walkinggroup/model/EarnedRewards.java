package com.example.mashu.walkinggroup.model;

import android.graphics.Color;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The EarnedRewards class contains information
 * regarding the rewards the user gets in the application.
 *
 */
public class EarnedRewards {

    private Reward currentAvatar;
    private List<Reward> unlockedRewards;
    private List<Reward> lockedRewards;

    public EarnedRewards() {}

    public Reward getCurrentAvatar() {
        return currentAvatar;
    }

    public List<Reward> getUnlockedRewards() {
        return unlockedRewards;
    }

    public List<Reward> getLockedRewards() {
        return lockedRewards;
    }

    public void setCurrentAvatar(Reward currentAvatar) {
        this.currentAvatar = currentAvatar;
    }

    public void setUnlockedRewards(List<Reward> unlockedRewards) {
        this.unlockedRewards = unlockedRewards;
    }

    public void setLockedRewards(List<Reward> lockedRewards) {
        this.lockedRewards = lockedRewards;
    }
}