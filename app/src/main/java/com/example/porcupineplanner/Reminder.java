package com.example.porcupineplanner;

public class Reminder {
    String title;
    String description;
    String reminderDate;
    int reminderHour;
    int reminderMinute;

    public Reminder(){
        title = "TITLE";
        description = "DESCRIPTION";
        reminderDate = "REMINDER DATE";
        reminderHour = 0;
        reminderMinute = 0;
    }

    public Reminder(String title, String description, String reminderDate, int reminderHour, int reminderMinute) {
        this.title = title;
        this.description = description;
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
