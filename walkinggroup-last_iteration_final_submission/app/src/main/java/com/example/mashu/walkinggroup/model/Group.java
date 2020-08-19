package com.example.mashu.walkinggroup.model;

import com.example.mashu.walkinggroup.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * The Group class describes
 * a single group in the application.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Group extends IdItemBase{

    private final static int DESTINATION_INDEX = 1;

    private User leader;
    private String groupDescription;
    private double[] routeLatArray;
    private double[] routeLngArray;
    private List<User> memberUsers;

    private static Group currentGroup;


    private Group() {}

    public static Group getCurrentGroup(){

        if(currentGroup == null){
            currentGroup = new Group();
        }

        return currentGroup;
    }

    public User getLeader() {
        return leader;
    }

    public void setLeader(User leader) {
        this.leader = leader;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public double[] getRouteLatArray() {
        return routeLatArray;
    }

    public void setRouteLatArray(double[] routeLatArray) {
        this.routeLatArray = routeLatArray;
    }

    public double[] getRouteLngArray() {
        return routeLngArray;
    }

    public void setRouteLngArray(double[] routeLngArray) {
        this.routeLngArray = routeLngArray;
    }

    public List<User> getMemberUsers() {
        return memberUsers;
    }

    public void setMemberUsers(List<User> memberUsers) {
        this.memberUsers = memberUsers;
    }

    public double accessDestinationLatitude(){
        return routeLatArray[DESTINATION_INDEX];
    }

    public double accessDestinationLongitude(){
        return routeLngArray[DESTINATION_INDEX];
    }

}