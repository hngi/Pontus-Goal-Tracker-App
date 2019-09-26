package com.project.pontusgoaltracker.models;

import java.util.List;

public class User {

    private String username;
    private String email;
    private String uId;
    private List<String> userGoalsIds;

    //constructors
    //warning: do not delete this empty constructor
    public User(){

    }

    public User(String username, String email, String uId){
        this.username = username;
        this.email = email;
        this.uId=uId;
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

}



