/**
 * This program computes the logic to create an Exam item. It includes constructors or creating
 *      Exam objects elsewhere, along with getters and setters for each field.
 * CPSC 312-01, Fall 2019
 * Final Project - Porcupine Planner
 * No sources to cite.
 *
 * @author Kellie Colson and Elizabeth Larson
 * @version v1.0 12/11/19
 */

package com.example.porcupineplanner;

public class Exam {
    // Fields:
    //      title - String representation of an Exam item's title
    //      description - String representation of an Exam item's description
    //      dueDate - String representation of an Exam item's due date (or, the day of the exam, in this case)
    //      reminderDate - String representation of an Exam item's reminder date
    //      reminderHour - integer representation of an Exam item's reminder hour
    //      reminderMinute - integer representation of an Exam item's reminder minute
    String title;
    String description;
    String dueDate; // Exam date
    String reminderDate;
    int reminderHour;
    int reminderMinute;

    /**
     * DVC for the Exam class
     * Sets all fields to a default value
     */
    public Exam(){
        title = "TITLE";
        description = "DESCRIPTION";
        dueDate = "DUE DATE";
        reminderDate = "REMINDER DATE";
        reminderHour = 0;
        reminderMinute = 0;
    }

    /**
     * EVC for the Exam class
     * Sets all fields to the passed-in values
     *
     * @param title passed-in String representation of a title
     * @param description passed-in String representation of a description
     * @param dueDate passed-in String representation of a due date
     * @param reminderDate passed-in String representation of a reminder date
     * @param reminderHour passed-in integer representation of a reminder hour
     * @param reminderMinute passed-in integer representation of a reminder minute
     */
    public Exam(String title, String description, String dueDate, String reminderDate, int reminderHour, int reminderMinute) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.reminderDate = reminderDate;
        this.reminderHour = reminderHour;
        this.reminderMinute = reminderMinute;
    }

    /**
     * Getter function for the title field
     *
     * @return desired Exam title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter function for the title field
     *
     * @param title new String value for the title field
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter function for the description field
     *
     * @return desired Exam description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter function for the description field
     *
     * @param description new String value for the description field
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter function for the dueDate field
     *
     * @return desired Exam due date
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     * Setter function for the dueDate field
     *
     * @param dueDate new String value for the dueDate field
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Getter function for the reminderDate field
     *
     * @return desired Exam reminder date
     */
    public String getReminderDate() {
        return reminderDate;
    }

    /**
     * Setter function for the reminderDate field
     *
     * @param reminderDate new String value for the reminderDate field
     */
    public void setReminderDate(String reminderDate) {
        this.reminderDate = reminderDate;
    }

    /**
     * Getter function for the reminderHour field
     *
     * @return desired Exam reminder hour
     */
    public int getReminderHour() {
        return reminderHour;
    }

    /**
     * Setter function for the reminderHour field
     *
     * @param reminderHour new integer value for the reminderHour field
     */
    public void setReminderHour(int reminderHour) {
        this.reminderHour = reminderHour;
    }

    /**
     * Getter function for the reminderMinute field
     *
     * @return desired Exam reminder minute
     */
    public int getReminderMinute() {
        return reminderMinute;
    }

    /**
     * Setter function for the reminderMinute field
     *
     * @param reminderMinute new String value for the reminderMinute field
     */
    public void setReminderMinute(int reminderMinute) {
        this.reminderMinute = reminderMinute;
    }
}