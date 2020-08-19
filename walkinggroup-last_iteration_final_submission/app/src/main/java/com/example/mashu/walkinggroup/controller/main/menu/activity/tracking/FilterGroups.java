package com.example.mashu.walkinggroup.controller.main.menu.activity.tracking;

import com.example.mashu.walkinggroup.model.Group;

import java.util.ArrayList;
import java.util.List;

/**
 * The FilterGroups class handles filtering out duplicate groups
 * in situations where the user leads a group and is also a member of
 * that same group.
 */

public class FilterGroups {

    private List<Group> currentGroupsPresentAsMember;
    private List<Group> currentGroupsPresentAsLeader;


    public FilterGroups(List<Group> currentGroupsPresentAsMember, List<Group> currentGroupsPresentAsLeader) {

        this.currentGroupsPresentAsMember = currentGroupsPresentAsMember;
        this.currentGroupsPresentAsLeader = currentGroupsPresentAsLeader;
    }

    public List<Group> getGroupsToSearchThrough(){

        List<Integer> groupPositionsToAvoid = new ArrayList<>();

        for(int i = 0; i < currentGroupsPresentAsLeader.size(); i++){

            Group group = currentGroupsPresentAsLeader.get(i);

            if(isCurrentUserAlsoGroupMember(group.getId())){
                groupPositionsToAvoid.add(i);
            }
        }

        return formGroupsToSearchThrough(groupPositionsToAvoid);
    }

    private boolean isCurrentUserAlsoGroupMember(Long leaderGroupID) {

        for(int i = 0; i < currentGroupsPresentAsMember.size(); i++){

            Long memberGroupID = currentGroupsPresentAsMember.get(i).getId();

            if(memberGroupID.equals(leaderGroupID)){
                return true;
            }
        }

        return false;
    }

    private List<Group> formGroupsToSearchThrough(List<Integer> groupPositionsToAvoid) {

        List<Group> groupsToSearchThrough = new ArrayList<>(currentGroupsPresentAsMember);

        for(int i = 0; i < currentGroupsPresentAsLeader.size(); i++){

            if(!groupPositionsToAvoid.contains(i)){
                groupsToSearchThrough.add(currentGroupsPresentAsLeader.get(i));
            }
        }

        return groupsToSearchThrough;
    }

}