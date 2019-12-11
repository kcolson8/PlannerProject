/**
 * This program handles all of the database storage logic for the planner. All planner items
 *      are saved in one table within the database. This program handles connecting to the
 *      database, creating the table, inserting instances into the table, updating them based
 *      on id value, deleting based on id value, and querying specific records in the database.
 * CPSC 312-01, Fall 2019
 * Final Project - Porcupine Planner
 * No sources to cite.
 *
 * @author Kellie Colson and Elizabeth Larson
 * @version v1.0 12/11/19
 */

package com.example.porcupineplanner;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    // Fields:
    //      DATABASE_NAME - name of the database planner items is being stored in
    //      DATABASE_VERSION_NUMBER - version of the database planner items is being stored in
    //      PLANNER_TABLE - table that planner item data is being stored in
    //      ID - attribute name for the unique identifier for each planner item
    //      TYPE_OF_ITEM - attribute name for the type of item being stored (homework, exam, or reminder)
    //      TITLE - attribute name for the planner item's title
    //      DESCRIPTION - attribute name for the planner item's description
    //      DUE_DATE - attribute name for the planner item's due date
    //      REMINDER_DATE - attribute name for the planner item's reminder date
    //      REMINDER_HOUR - attribute name for the planner item's reminder hour
    //      REMINDER_MINUTE - attribute name for the planner item's reminder minute
    static final String DATABASE_NAME = "plannerDatabase";
    static final int DATABASE_VERSION_NUMBER = 1;
    static final String PLANNER_TABLE = "plannerTable";
    static final String ID = "_id";
    static final String TYPE_OF_ITEM = "typeOfItem";
    static final String TITLE = "title";
    static final String DESCRIPTION = "description";
    static final String DUE_DATE = "dueDate";
    static final String REMINDER_DATE = "reminderDate";
    static final String REMINDER_HOUR = "reminderHour";
    static final String REMINDER_MINUTE = "reminderMinute";

    /**
     * Connect to the database when a DatabaseOpenHelper object is created
     *
     * @param context connect to the database based on the given Context
     */
    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION_NUMBER);
    }

    /**
     * Not utilized in this program, but needed to implement the database
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    /**
     * Use an SQL create statement to create a table called plannerTable into the database
     *
     * @param sqLiteDatabase database that the created table is going in
     */
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // CREATE TABLE plannerTable (_id INTEGER PRIMARY KEY AUTOINCREMENT,
        //			                   typeOfItem TEXT,
        //			                   title TEXT,
        //			                   class TEXT,
        //			                   description TEXT,
        //			                   dueDate TEXT,
        //			                   reminderDate TEXT,
        //			                   reminderHour TEXT,
        //			                   reminderMinute TEXT
        String sqlCreate = "CREATE TABLE " + PLANNER_TABLE + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TYPE_OF_ITEM + " TEXT, " +
                TITLE + " TEXT, " +
                DESCRIPTION + " TEXT, " +
                DUE_DATE + " TEXT, " +
                REMINDER_DATE + " TEXT, " +
                REMINDER_HOUR + " TEXT, " +
                REMINDER_MINUTE + " TEXT)";
        // Execute the statement, thus creating the table
        sqLiteDatabase.execSQL(sqlCreate);
    }

    /**
     * Use an SQL insert statement to insert values from a Homework object into the table
     *
     * @param homework Homework item that the user wants to add to the planner
     */
    public void insertHomeworkItem(Homework homework) {
        // INSERT INTO plannerTable VALUES (null, 'homework', <title>, <description>, <dueDate>, <reminderDate>, <reminderMinute>, <reminderHour>)
        String sqlInsert = "INSERT INTO " + PLANNER_TABLE + " VALUES (null, 'homework', '" +
                homework.getTitle() + "', '" + homework.getDescription() + "', '" +
                homework.getDueDate() + "', '" + homework.getReminderDate() + "', '" +
                homework.getReminderHour() + "', '" + homework.getReminderMinute() + "')";
        // Get a reference to the database for writing
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlInsert);
        // Close the writable database
        db.close();
    }

    /**
     * Use an SQL insert statement to insert values from a Exam object into the table
     *
     * @param exam Exam item that the user wants to add to the planner
     */
    public void insertExamItem(Exam exam) {
        // INSERT INTO plannerTable VALUES (null, 'exam', <title>, <description>, <dueDate>, <reminderDate>, <reminderMinute>, <reminderHour>)
        String sqlInsert = "INSERT INTO " + PLANNER_TABLE + " VALUES (null, 'exam', '" +
                exam.getTitle() + "', '" + exam.getDescription() + "', '" +
                exam.getDueDate() + "', '" + exam.getReminderDate() + "', '" +
                exam.getReminderHour() + "', '" + exam.getReminderMinute() + "')";
        // Get a reference to the database for writing
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlInsert);
        // Close the writable database
        db.close();
    }

    /**
     * Use an SQL insert statement to insert values from a Reminder object into the table
     *
     * @param reminder Reminder item that the user wants to add to the planner
     */
    public void insertReminderItem(Reminder reminder) {
        // INSERT INTO plannerTable VALUES (null, 'reminder', <title>, <description>, null, <reminderDate>, <reminderMinute>, <reminderHour>)
        // dueDate is set to null, because this field is not needed for a simple reminder
        String sqlInsert = "INSERT INTO " + PLANNER_TABLE + " VALUES (null, 'reminder', '" +
                reminder.getTitle() + "', '" + reminder.getDescription() + "', null, '" +
                reminder.getReminderDate() + "', '" + reminder.getReminderHour() + "', '" +
                reminder.getReminderMinute() + "')";
        // Get a reference to the database for writing
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlInsert);
        // Close the writable database
        db.close();
    }

    /**
     * Use an SQL query to create a cursor for table parsing
     *
     * @return cursor used to parse the table
     */
    public Cursor getAllItemsCursor(){
        // SELECT * FROM plannerTable
        String sqlSelectHomework = "SELECT * FROM " + PLANNER_TABLE;
        // Get a reference to the database querying
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlSelectHomework, null);
        return cursor;
    }

    /**
     * Use an SQL query to retrieve the type of item (homework, exam, or reminder) stored at the given id spot
     *
     * @param id integer value representing the item's unique id value
     * @return the type of item stored as a String
     */
    public String getItemType(int id){
        String itemType = "";
        // SELECT typeOfItem FROM plannerTable WHERE id=<id>
        String sqlSelect = "SELECT " + TYPE_OF_ITEM + " FROM " + PLANNER_TABLE +
                " WHERE " + ID + "=" + id;
        // Get a reference to the database for reading
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlSelect, null);

        while(cursor.moveToNext()) {
            itemType = cursor.getString(0);
        }
        return itemType;
    }

    /**
     * Use an SQL query to retrieve the data for a specific entry (based on ID)
     * Store the resulting information in a Homework object
     *
     * @param id long value representing the item's unique id value
     * @return the generated Homework item, filled with data from the table
     */
    public Homework getHomeworkByID(long id){
        Homework homework = new Homework();
        // SELECT * FROM plannerTable WHERE id=<id>
        String sqlSelect = "SELECT * FROM " + PLANNER_TABLE +
                " WHERE " + ID + "=" + id;
        // Get a reference to the database for reading
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlSelect, null);

        // Traverse through each attribute and save the results as a new homework item
        while(cursor.moveToNext()) {
            String title = cursor.getString(2);
            String description = cursor.getString(3);
            String dueDate = cursor.getString(4);
            String reminderDate = cursor.getString(5);
            int reminderHour = cursor.getInt(6);
            int reminderMinute = cursor.getInt(7);
            homework = new Homework(title, description, dueDate, reminderDate, reminderHour, reminderMinute);
        }
        return homework;
    }

    /**
     * Use an SQL query to retrieve the data for a specific entry (based on ID)
     * Store the resulting information in an Exam object
     *
     * @param id long value representing the item's unique id value
     * @return the generated Exam item, filled with data from the table
     */
    public Exam getExamByID(long id){
        Exam exam = new Exam();
        // SELECT * FROM plannerTable WHERE id=<id>
        String sqlSelect = "SELECT * FROM " + PLANNER_TABLE +
                " WHERE " + ID + "=" + id;
        // Get a reference to the database for reading
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlSelect, null);

        // Traverse through each attribute and save the results as a new exam item
        while(cursor.moveToNext()) {
            String title = cursor.getString(2);
            String description = cursor.getString(3);
            String dueDate = cursor.getString(4);
            String reminderDate = cursor.getString(5);
            int reminderHour = cursor.getInt(6);
            int reminderMinute = cursor.getInt(7);
            exam = new Exam(title, description, dueDate, reminderDate, reminderHour, reminderMinute);
        }
        return exam;
    }

    /**
     * Use an SQL query to retrieve the data for a specific entry (based on ID)
     * Store the resulting information in a Reminder object
     *
     * @param id long value representing the item's unique id value
     * @return the generated Reminder item, filled with data from the table
     */
    public Reminder getReminderByID(long id){
        Reminder reminder = new Reminder();
        // SELECT * FROM plannerTable WHERE id=<id>
        String sqlSelect = "SELECT * FROM " + PLANNER_TABLE +
                " WHERE " + ID + "=" + id;
        // Get a reference to the database for reading
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlSelect, null);

        // Traverse through each attribute and save the results as a new exam item
        while(cursor.moveToNext()) {
            String title = cursor.getString(2);
            String description = cursor.getString(3);
            String reminderDate = cursor.getString(5);
            int reminderHour = cursor.getInt(6);
            int reminderMinute = cursor.getInt(7);
            reminder = new Reminder(title, description, reminderDate, reminderHour, reminderMinute);
        }
        return reminder;
    }

    /**
     * Use an SQL update statement to change the data for a specific entry (based on ID)
     * Record must be of type Homework
     *
     * @param id int value representing the item's unique id value
     * @param homework the Homework item the user wants to replace the original with
     */
    public void updateHomeworkById(int id, Homework homework) {
        // UPDATE plannerTable SET title=<title>, description=<description>, dueDate=<dueDate>, reminderDate=<reminderDate>, reminderMonth=<reminderMonth>, reminderMinute=<reminderMinute> WHERE id=<id>
        String sqlUpdate= "UPDATE " + PLANNER_TABLE + " SET " +
                TITLE + "='" + homework.getTitle() + "', " +
                DESCRIPTION + "='" + homework.getDescription() + "', " +
                DUE_DATE + "='" + homework.getDueDate() + "', " +
                REMINDER_DATE + "='" + homework.getReminderDate() + "', " +
                REMINDER_HOUR + "='" + homework.getReminderHour() + "', " +
                REMINDER_MINUTE + "='" + homework.getReminderMinute() + "' WHERE " +
                ID + "=" + id;
        // Get a reference to the database for writing
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlUpdate);
        db.close();
    }

    /**
     * Use an SQL update statement to change the data for a specific entry (based on ID)
     * Record must be of type Exam
     *
     * @param id int value representing the item's unique id value
     * @param exam the Exam item the user wants to replace the original with
     */
    public void updateExamById(int id, Exam exam) {
        // UPDATE plannerTable SET title=<title>, description=<description>, dueDate=<dueDate>, reminderDate=<reminderDate>, reminderMonth=<reminderMonth>, reminderMinute=<reminderMinute> WHERE id=<id>
        String sqlUpdate= "UPDATE " + PLANNER_TABLE + " SET " +
                TITLE + "='" + exam.getTitle() + "', " +
                DESCRIPTION + "='" + exam.getDescription() + "', " +
                DUE_DATE + "='" + exam.getDueDate() + "', " +
                REMINDER_DATE + "='" + exam.getReminderDate() + "', " +
                REMINDER_HOUR + "='" + exam.getReminderHour() + "', " +
                REMINDER_MINUTE + "='" + exam.getReminderMinute() + "' WHERE " +
                ID + "=" + id;
        // Get a reference to the database for writing
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlUpdate);
        db.close();
    }

    /**
     * Use an SQL update statement to change the data for a specific entry (based on ID)
     * Record must be of type Reminder
     *
     * @param id int value representing the item's unique id value
     * @param reminder the Reminder item the user wants to replace the original with
     */
    public void updateReminderById(int id, Reminder reminder) {
        // UPDATE plannerTable SET title=<title>, description=<description>, reminderDate=<reminderDate>, reminderMonth=<reminderMonth>, reminderMinute=<reminderMinute> WHERE id=<id>
        String sqlUpdate= "UPDATE " + PLANNER_TABLE + " SET " +
                TITLE + "='" + reminder.getTitle() + "', " +
                DESCRIPTION + "='" + reminder.getDescription() + "', " +
                REMINDER_DATE + "='" + reminder.getReminderDate() + "', " +
                REMINDER_HOUR + "='" + reminder.getReminderHour() + "', " +
                REMINDER_MINUTE + "='" + reminder.getReminderMinute() + "' WHERE " +
                ID + "=" + id;
        // Get a reference to the database for writing
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlUpdate);
        db.close();
    }

    /**
     * Use an SQL delete statement to delete a record with a specific entry (based on ID)
     *
     * @param id int value representing the item's unique id value
     */
    public void deleteOneItem(int id) {
        // DELETE FROM plannerTable WHERE id=<id>
        String sqlDelete = "DELETE FROM " + PLANNER_TABLE + " WHERE " + ID + "=" + id;
        // Get a reference to the database for writing
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlDelete);
        db.close();
    }
}