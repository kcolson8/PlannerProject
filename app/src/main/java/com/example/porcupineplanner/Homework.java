package com.example.porcupineplanner;

import android.widget.DatePicker;

import java.util.Calendar;

public class Homework {
    String title;
    String subject;
    String description;
    String dueDate;
    String reminderDate;
    int reminderHour;
    int reminderMinute;

    public Homework(String title, String subject, String description, String dueDate, String reminderDate, int reminderHour, int reminderMinute) {
        this.title = title;
        this.subject = subject;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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
