package com.project.pontusgoaltracker.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Goal {
    //keys :do not delete
    public static final String GOAL_TITLE="GOAL_TITLE";
    public static final String GOAL_DESCRIPTION="GOAL_DESCRIPTION";
    public static final String GOAL_TYPE="GOAL_TYPE";
    public static final String IS_COMPLETED ="IS_COMPLETED";
    public static final String DEADLINE="DEADLINE";
    public static final String TASKS="TASKS";





    private UUID goalId ;
    private String goalIdString;
    private String title;
    private String description;
    private Date dateCreated;
    private String deadline;
    private Date dateCompleted;
    private boolean isCompleted;
    private String goalType;
    private ArrayList<Task> tasks =new ArrayList<Task>();
    private int completedTaskCount = 0;
    private int taskSize;



    //todo : add id to goal
    //todo : create setDeadline method
    //todo : add deadline to the Goal
    //todo : add percentageComplete method


    //constructors
    //warning : do not use this constructor. do not delete either .
    //No constructor sets the deadline of Goal.. explicitly call setDeadline after creating an object.
    public Goal(){

        this.tasks = new ArrayList<Task>();
    }

    public Goal(String title){
        this.dateCreated = new Date();
        this.goalId = UUID.randomUUID();
        this.goalIdString=goalId.toString();
        this.title = title;
        this.tasks = new ArrayList<Task>();
    }

    public Goal(String title, String description) {
        this.dateCreated = new Date();
        this.goalId = UUID.randomUUID();
        this.goalIdString=goalId.toString();
        this.title = title;
        this.description = description;
        this.goalType = GoalType.GENERAL;
        this.tasks = new ArrayList<Task>();
    }

    public Goal(String title, String description, String type) {
        this.dateCreated = new Date();
        this.goalId = UUID.randomUUID();
        this.goalIdString=goalId.toString();
        this.title = title;
        this.description = description;
        this.goalType = type;
        this.tasks = new ArrayList<Task>();
    }

    public Goal(String title, String description, String type, ArrayList<Task> tasks) {
        this.dateCreated = new Date();
        this.goalId = UUID.randomUUID();
        this.goalIdString=goalId.toString();
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

    public void setCompletedTaskCount(int completedTaskCount) {
        this.completedTaskCount = completedTaskCount;
    }
    public void setTaskSize(int taskSize) {
        this.taskSize = taskSize;
    }

    public void setType(String type) {
        this.goalType = type;
    }

    public void emptyTasks(){
        tasks=new ArrayList<>();
    }

//    public void setGoalIdString(String goalIdString) {
//        goalIdString = goalIdString;
//    }

    public void setDeadline(String deadline) {
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

    public String getDeadline() {
        return deadline;
    }

//    public UUID getGoalId() {
//        return goalId;
//    }
    public String getGoalIdString() {
        return goalIdString;
    }


    public boolean isCompleted() {
        if(calculatePercentageComplete()>=100)return true;
        return isCompleted;
    }

    public int getCompletedTaskCount() {
        return completedTaskCount;
    }
    public int getTaskSize() {
        return taskSize;
    }



    public String getType() {
        return goalType;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    //append

    public void addTask(Task task){
        if(task.isCompleted()){
            completedTaskCount++;
        }
        tasks.add(task);
    }
    public void deleteTask(Task task){
        if(task.isCompleted()){
            completedTaskCount--;
        }
        tasks.remove(task);
    }
    public double calculatePercentageComplete(){

        double percentage  = 0.00;
        //prevents divide by zero error in cases of empty task lists
            if(taskSize>0){ percentage =(Double.valueOf(completedTaskCount)/Double.valueOf(taskSize)*100);
            }
        return percentage;
    }

}

