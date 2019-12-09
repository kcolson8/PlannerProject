package com.example.porcupineplanner;

public class Exam {
    String title;
    String description;
    String dueDate; //exam date
    String reminderDate;
    int reminderHour;
    int reminderMinute;

    public Exam(){
        title = "TITLE";
        description = "DESCRIPTION";
        dueDate = "DUE DATE";
        reminderDate = "REMINDER DATE";
        reminderHour = 0;
        reminderMinute = 0;
    }

    public Exam(String title, String description, String dueDate, String reminderDate, int reminderHour, int reminderMinute) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.reminderDate = reminderDate;
        this.reminderHour = reminderHour;
        this.reminderMinute = reminderMinute;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(String reminderDate) {
        this.reminderDate = reminderDate;
    }

    public int getReminderHour() {
        return reminderHour;
    }

    public void setReminderHour(int reminderHour) {
        this.reminderHour = reminderHour;
    }

    public int getReminderMinute() {
        return reminderMinute;
    }

    public void setReminderMinute(int reminderMinute) {
        this.reminderMinute = reminderMinute;
    }
}
