package com.example.porcupineplanner;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class DatabaseOpenHelper extends SQLiteOpenHelper {
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

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION_NUMBER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

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
        sqLiteDatabase.execSQL(sqlCreate);
    }

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

    public void insertExamItem(Exam exam) {
        // INSERT INTO plannerTable VALUES (null, 'exam', <title>, <description>, <dueDate>, <reminderDate>, <reminderMinute>, <reminderHour>)
        String sqlInsert = "INSERT INTO " + PLANNER_TABLE + " VALUES (null, 'exam', ''" +
                exam.getTitle() + "', '" + exam.getDescription() + "', '" +
                exam.getDueDate() + "', '" + exam.getReminderDate() + "', '" +
                exam.getReminderHour() + "', '" + exam.getReminderMinute() + "')";
        // Get a reference to the database for writing
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlInsert);
        // Close the writable database
        db.close();
    }

    public void insertReminderItem(Reminder reminder) {
        // INSERT INTO plannerTable VALUES (null, 'reminder', <title>, <description>, null, <reminderDate>, <reminderMinute>, <reminderHour>)
        // dueDate is set to null, because this field is not needed for a simple reminder
        String sqlInsert = "INSERT INTO " + PLANNER_TABLE + " VALUES (null, 'reminder', ''" +
                reminder.getTitle() + "', '" + reminder.getDescription() + "', null, " +
                reminder.getReminderDate() + "', '" + reminder.getReminderHour() + "', '" +
                reminder.getReminderMinute() + "')";
        // Get a reference to the database for writing
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlInsert);
        // Close the writable database
        db.close();
    }

    public Cursor getAllItemsCursor(){
        // SELECT * FROM plannerTable
        String sqlSelectHomework = "SELECT * FROM " + PLANNER_TABLE;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlSelectHomework, null);
        return cursor;
    }

    public String getItemType(int id){
        String itemType = "";
        // SELECT typeOfItem FROM plannerTable WHERE id=<id>
        String sqlSelect = "SELECT " + TYPE_OF_ITEM + " FROM " + PLANNER_TABLE +
                " WHERE " + ID + "=" + id;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlSelect, null);

        while(cursor.moveToNext()) {
            itemType = cursor.getString(0);
            Log.d("myTag", itemType);
        }
        return itemType;
    }

    public Homework getHomeworkByID(long id){
        Homework homework = new Homework();
        // SELECT * FROM plannerTable WHERE id=<id>
        String sqlSelect = "SELECT * FROM " + PLANNER_TABLE +
                " WHERE " + ID + "=" + id;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlSelect, null);

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

    public Exam getExamByID(long id){
        Exam exam = new Exam();
        // SELECT * FROM plannerTable WHERE id=<id>
        String sqlSelect = "SELECT * FROM " + PLANNER_TABLE +
                " WHERE " + ID + "=" + id;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlSelect, null);

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

    public Reminder getReminderByID(long id){
        Reminder reminder = new Reminder();
        // SELECT * FROM plannerTable WHERE id=<id>
        String sqlSelect = "SELECT * FROM " + PLANNER_TABLE +
                " WHERE " + ID + "=" + id;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlSelect, null);

        while(cursor.moveToNext()) {
            String title = cursor.getString(2);
            String description = cursor.getString(3);
            String dueDate = cursor.getString(4);
            String reminderDate = cursor.getString(5);
            int reminderHour = cursor.getInt(6);
            int reminderMinute = cursor.getInt(7);
            reminder = new Reminder(title, description, reminderDate, reminderHour, reminderMinute);
        }
        return reminder;
    }

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

    public void deleteOneItem(int id) {
        // DELETE FROM plannerTable WHERE id=<id>
        String sqlDelete = "DELETE FROM " + PLANNER_TABLE + " WHERE " + ID + "=" + id;
        // Get a reference to the database for writing
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlDelete);
        db.close();
    }
}