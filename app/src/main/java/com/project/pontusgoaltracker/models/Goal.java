package com.project.pontusgoaltracker.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Goal {

    private UUID goalId ;
    private String title;
    private String description;
    private Date dateCreated;
    private Date deadline;
    private Date dateCompleted;
    private boolean isCompleted;
    private String goalType;
    private ArrayList<Task> tasks;

    //todo : add id to goal
    //todo : create setDeadline method
    //todo : add deadline to the Goal
    //todo : add percentageComplete method


    //constructors
    //warning : do not use this constructor. do not delete either .
    //No constructor sets the deadline of Goal.. explicitly call setDeadline after creating an object.
    public Goal(){}

    public Goal(String title){
        this.dateCreated = new Date();
        this.goalId = UUID.randomUUID();
        this.title = title;
        this.tasks = new ArrayList<Task>();
    }

    public Goal(String title, String description) {
        this.dateCreated = new Date();
        this.goalId = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.goalType = GoalType.GENERAL;
        this.tasks = new ArrayList<Task>();
    }

    public Goal(String title, String description, String type) {
        this.dateCreated = new Date();
        this.goalId = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.goalType = type;
        this.tasks = new ArrayList<Task>();
    }

    public Goal(String title, String description, String type, ArrayList<Task> tasks) {
        this.dateCreated = new Date();
        this.goalId = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.goalType = type;
        this.tasks = tasks;
    }

    //setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateCompleted(Date dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public void setType(String type) {
        this.goalType = type;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }


    //getters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getDateCompleted() {
        return dateCompleted;
    }

    public Date getDeadline() {
        return deadline;
    }


    public boolean isCompleted() {
        return isCompleted;
    }

    public String getType() {
        return goalType;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    //append

    public void addTask(Task task){
        tasks.add(task);
    }

}

