package com.project.pontusgoaltracker.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {

    private String username;
    private String email;
    private String uId;
    private HashMap<String,Goal> userGoals;
    private ArrayList<String> userGoalsIds;

    //constructors
    //warning: do not delete this empty constructor
    public User(){

    }

    public User(String username, String email, String uId){
        this.username = username;
        this.email = email;
        this.uId=uId;
        this.userGoals = new HashMap<>();
        userGoalsIds = new ArrayList<>();
    }


    //setters
    	//todo: add setters when neccesary

    //getters

    public String getuId() {
        return uId;
    }

    public String getUsername() {
        return username;
    }   

    public String getEmail() {
        return email;
    }
    public List<String> getUserGoalsIds(){
    	return userGoalsIds;
    }

    public HashMap<String,Goal> getUserGoals() {
        return userGoals;
    }

    public void addGoal(Goal goal){
        userGoals.put(goal.getGoalIdString(),goal);
        userGoalsIds.add(goal.getGoalIdString());
    }

}



