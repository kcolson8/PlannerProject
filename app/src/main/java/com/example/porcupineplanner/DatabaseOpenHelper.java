package com.example.porcupineplanner;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "plannerDatabase";
    static final int DATABASE_VERSION_NUMBER = 1;
    static final String HOMEWORK_TABLE = "homeworkTable";
    static final String EXAM_TABLE = "examTable";
    static final String REMINDER_TABLE = "reminderTable";
    static final String ID = "_id";
    static final String TITLE = "title";
    static final String CLASS = "class";
    static final String DESCRIPTION = "description";
    static final String DUE_DATE = "dueDate";
    static final String REMINDER_DATE = "reminderDate";
    static final String REMINDER_TIME = "reminderTime";
    static final String EXAM_DATE = "examDate";

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION_NUMBER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // CREATE TABLE homeworkTable (_id INTEGER PRIMARY KEY AUTOINCREMENT,
        //			                   title TEXT,
        //			                   class TEXT,
        //			                   description TEXT,
        //			                   dueDate TEXT,
        //			                   reminderDate TEXT,
        //			                   reminderTime TEXT)
        String sqlCreateHomework = "CREATE TABLE " + HOMEWORK_TABLE + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TITLE + " TEXT, " +
                CLASS + " TEXT, " +
                DESCRIPTION + " TEXT, " +
                DUE_DATE + " TEXT, " +
                REMINDER_DATE + " TEXT, " +
                REMINDER_TIME + " TEXT)";
        sqLiteDatabase.execSQL(sqlCreateHomework);

        /*// CREATE TABLE examTable (_id INTEGER PRIMARY KEY AUTOINCREMENT,
        //			               title TEXT,
        //			               class TEXT,
        //			               description TEXT,
        //			               examDate TEXT,
        //			               reminderDate TEXT,
        //			               reminderTime TEXT)
        String sqlCreateExam = "CREATE TABLE " + EXAM_TABLE + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TITLE + " TEXT, " +
                CLASS + " TEXT, " +
                DESCRIPTION + " TEXT, " +
                EXAM_DATE + " TEXT, " +
                REMINDER_DATE + " TEXT, " +
                REMINDER_TIME + " TEXT)";
        sqLiteDatabase.execSQL(sqlCreateExam);

        // CREATE TABLE homeworkTable (_id INTEGER PRIMARY KEY AUTOINCREMENT,
        //			                   title TEXT,
        //			                   class TEXT,
        //			                   description TEXT,
        //			                   reminderDate TEXT,
        //			                   reminderTime TEXT)
        String sqlCreateReminder = "CREATE TABLE " + REMINDER_TABLE + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TITLE + " TEXT, " +
                CLASS + " TEXT, " +
                DESCRIPTION + " TEXT, " +
                REMINDER_DATE + " TEXT, " +
                REMINDER_TIME + " TEXT)";
        sqLiteDatabase.execSQL(sqlCreateReminder);*/
    }

    // ~~~~~~~~~ getClass is an actual sql method, define it as get class name in classes :)
    public void insertHomeworkItem(Homework homework) {
        // INSERT INTO homeworkTable VALUES (null, <title>, <class>, <description>, <dueDate>, <reminderDate>, <reminderTime>)
        String sqlInsertHomework = "INSERT INTO " + HOMEWORK_TABLE + " VALUES (null, '" +
                homework.getTitle() + "', '" +  homework.getSubject() + "', '" +
                homework.getDescription() + "', '" +  homework.getDueDate() + "', '" +
                homework.getReminderDate() + "', '" +  homework.getSubject() + "')";
        // Get a reference to the database for writing
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlInsertHomework);
        // Close the writable database
        db.close();
    }

    public Cursor getSelectAllHomeworkCursor(){
        String sqlSelectHomework = "SELECT * FROM " + HOMEWORK_TABLE;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlSelectHomework, null);
        return cursor;
    }

    // Need one of these for exam and reminder tables

   /* // ~~~~~~~~~ getClass is an actual sql method, define it as get class name in classes :)
    public void insertExamItem(Exam exam) {
        // INSERT INTO examTable VALUES (null, <title>, <class>, <description>, <examDate>, <reminderDate>, <reminderTime>)
        String sqlInsertExam = "INSERT INTO " + EXAM_TABLE + " VALUES (null, '" +
                exam.getTitle() + "', '" +  exam.getClassName() + "', '" +
                exam.getDescription() + "', '" +  exam.getExamDate() + "', '" +
                exam.getReminderDate() + "', '" +  exam.getReminderTime() + "')";
        // Get a reference to the database for writing
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlInsertExam);
        // Close the writable database
        db.close();
    }

    // ~~~~~~~~~ getClass is an actual sql method, define it as get class name in classes :)
    public void insertReminderItem(Reminder reminder) {
        // INSERT INTO reminderTable VALUES (null, <title>, <class>, <description>, <reminderDate>, <reminderTime>)
        String sqlInsertReminder = "INSERT INTO " + HOMEWORK_TABLE + " VALUES (null, '" +
                reminder.getTitle() + "', '" +  reminder.getClassName() + "', '" +
                reminder.getDescription() + "', '" +  reminder.getReminderDate() + "', '" +
                reminder.getReminderTime() + "')";
        // Get a reference to the database for writing
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlInsertReminder);
        // Close the writable database
        db.close();
    }*/

    /*public void updateHomeworkById(int id, Homework updatedHomework) {
        // UPDATE homeworkTable SET title='...', className='...', description='...', dueDate='...', reminderDate='...', reminderTime='...' WHERE id=...
        //      ... represents the passed in values
        String sqlUpdateHomework = "UPDATE " + HOMEWORK_TABLE + " SET " +
                TITLE + "='" + updatedHomework.getTitle() + "', " +
                CLASS + "='" + updatedHomework.getClassName() + "', " +
                DESCRIPTION + "='" + updatedHomework.getDescription() + "', " +
                DUE_DATE + "='" + updatedHomework.getDueDate() + "', " +
                REMINDER_DATE + "='" + updatedHomework.getReminderDate() + "', " +
                REMINDER_TIME + "='" + updatedHomework.getReminderTime() + "' WHERE " +
                ID + "=" + id;
        // Get a reference to the database for writing
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlUpdateHomework);
        db.close();
    }

    public void updateExamById(int id, Exam updatedExam) {
        // UPDATE examTable SET title='...', className='...', description='...', examDate='...', reminderDate='...', reminderTime='...' WHERE id=...
        //      ... represents the passed in values
        String sqlUpdateExam = "UPDATE " + EXAM_TABLE + " SET " +
                TITLE + "='" + updatedExam.getTitle() + "', " +
                CLASS + "='" + updatedExam.getClassName() + "', " +
                DESCRIPTION + "='" + updatedExam.getDescription() + "', " +
                EXAM_DATE + "='" + updatedExam.getExamDate() + "', " +
                REMINDER_DATE + "='" + updatedExam.getReminderDate() + "', " +
                REMINDER_TIME + "='" + updatedExam.getReminderTime() + "' WHERE " +
                ID + "=" + id;
        // Get a reference to the database for writing
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlUpdateExam);
        db.close();
    }

    public void updateHomeworkById(int id, Reminder updatedReminder) {
        // UPDATE reminderTable SET title='...', className='...', description='...', reminderDate='...', reminderTime='...' WHERE id=...
        //      ... represents the passed in values
        String sqlUpdateReminder = "UPDATE " + REMINDER_TABLE + " SET " +
                TITLE + "='" + updatedReminder.getTitle() + "', " +
                CLASS + "='" + updatedReminder.getClassName() + "', " +
                DESCRIPTION + "='" + updatedReminder.getDescription() + "', " +
                REMINDER_DATE + "='" + updatedReminder.getReminderDate() + "', " +
                REMINDER_TIME + "='" + updatedReminder.getReminderTime() + "' WHERE " +
                ID + "=" + id;
        // Get a reference to the database for writing
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlUpdateReminder);
        db.close();
    }*/

    public void deleteOneHomework(int id) {
        // DELETE FROM homeworkTable WHERE id=...
        //      ... represents the passed in id value
        String sqlDeleteHomework = "DELETE FROM " + HOMEWORK_TABLE + " WHERE " + ID + "=" + id;
        // Get a reference to the database for writing
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlDeleteHomework);
        db.close();
    }

    public void deleteOneExam(int id) {
        // DELETE FROM examTable WHERE id=...
        //      ... represents the passed in id value
        String sqlDeleteExam = "DELETE FROM " + EXAM_TABLE + " WHERE " + ID + "=" + id;
        // Get a reference to the database for writing
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlDeleteExam);
        db.close();
    }

    public void deleteOneReminder(int id) {
        // DELETE FROM reminderTable WHERE id=...
        //      ... represents the passed in id value
        String sqlDeleteReminder = "DELETE FROM " + REMINDER_TABLE + " WHERE " + ID + "=" + id;
        // Get a reference to the database for writing
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlDeleteReminder);
        db.close();
    }
}
