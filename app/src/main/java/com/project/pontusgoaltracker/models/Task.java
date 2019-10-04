package com.project.pontusgoaltracker.models;

import java.util.Date;
import java.util.UUID;

public class Task{



    private UUID taskId;
        private String title;
        private String description;
        public boolean isCompleted;
        private Date reminderDate;

        //TODO : 

        //constructors
        public Task(UUID taskId, String title, String description, boolean isCompleted, Date reminderDate) {
            this.taskId = taskId;
            this.title = title;
            this.description = description;
            this.isCompleted = isCompleted;
            this.reminderDate = reminderDate;
        }
        public Task(){
            taskId= UUID.randomUUID();
        }
        public Task(String title){
            taskId= UUID.randomUUID();
            this.title = title;

        }
    public Task(Boolean isCompleted) {
        this.isCompleted=isCompleted;

    }

        public Task(String title, String description) {
            taskId= UUID.randomUUID();
            this.title = title;
            this.description = description;
        }

        public Task(String title, String description, Date reminderDate) {
            taskId= UUID.randomUUID();
            this.title = title;
            this.description = description;
            this.reminderDate = reminderDate;
        }



    //SETTERS

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setIsCompleted(boolean completed) {
            this.isCompleted = completed;
        }

        public void setReminderDate(Date reminderDate) {
            this.reminderDate = reminderDate;
        }

        //GETTERS

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public boolean getIsCompleted() {
            return isCompleted;
        }

        public Date getReminderDate() {
            return reminderDate;
        }


}
