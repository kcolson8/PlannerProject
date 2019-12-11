/**
 * This program computes the logic for editing a new Exam item or old Exam item. It uses intents to
 *      retrieve and pass information to the MainActivity. The layout also updates as the user works
 *      with the EditTexts, TextFields, TimePicker, and DatePickerDialogs. the Save! button returns the
 *      user to MainActivity to view their planner.
 * CPSC 312-01, Fall 2019
 * Final Project - Porcupine Planner
 * Sources to Cite:
 *      DatePickerDialogs were written with the help of this video: https://www.youtube.com/watch?v=hwe1abDO2Ag
 *
 * @author Kellie Colson and Elizabeth Larson
 * @version v1.0 12/11/19
 */

package com.example.porcupineplanner;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;

public class ExamActivity extends AppCompatActivity {
    // Fields:
    //      examDateConfirmTextView - reference to the TextView that stores the due date
    //      reminderDateConfirmTextView - reference to the TextView that stores the reminder date
    //      saveButton  - reference to the Button that initiates data storage/planner item saving
    //      titleEditText - reference to the EditText that stores the title
    //      descriptionEditText - reference to the EditText that stores the title
    //      id - integer representing a planner's unique id
    //      mDateSetListener - listener that works with the due date DatePickerDialog
    //      mDateSetListener2 - listener that works with the reminder date DatePickerDialog
    TextView examDateConfirmTextView;
    TextView reminderDateConfirmTextView;
    Button saveButton;
    EditText titleEditText;
    EditText descriptionEditText;
    int id;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatePickerDialog.OnDateSetListener mDateSetListener2;

    /**
     * Sets up the logic for editing old Exam item
     *
     * @param savedInstanceState Bundle object that has the ability to restore the app to a previous state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        // Grabs intent when editing an Exam object to set fields the same as user last saved it
        Intent intent = getIntent();
        if(intent != null){
            // Retrieve intent data from MainActivity
            id = intent.getIntExtra("id",0);
            String title = intent.getStringExtra("title");
            String description = intent.getStringExtra("description");
            String examDate = intent.getStringExtra("dueDate");
            String reminderDate = intent.getStringExtra("reminderDate");
            int reminderHour = intent.getIntExtra("reminderHour", 0);
            int reminderMinute = intent.getIntExtra("reminderMinute", 0);

            // Get a reference to layout objects
            TextView dueDateConfirmTextView = findViewById(R.id.examDateConfirmTextView);
            TextView reminderDateConfirmTextView = findViewById(R.id.examReminderDateConfirmTextView);
            EditText titleEditText = findViewById(R.id.examTitleEditText);
            EditText descriptionEditText = findViewById(R.id.examDescriptionEditText);
            TimePicker timePicker = findViewById(R.id.examReminderTimePicker);

            // Set all layout objects to intent-given values
            titleEditText.setText(title);
            descriptionEditText.setText(description);
            dueDateConfirmTextView.setText(examDate);
            reminderDateConfirmTextView.setText(reminderDate);
            timePicker.setHour(reminderHour);
            timePicker.setMinute(reminderMinute);
        }

        // See sources
        examDateConfirmTextView = findViewById(R.id.examDateConfirmTextView);
        Button pickDateButton = findViewById(R.id.examPickDateButton);
        pickDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_WEEK);

                // Create a new dialog for the user to work with
                DatePickerDialog dialog = new DatePickerDialog(
                        ExamActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        // See sources
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            // Set the TextView to the user's chosen due date
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                m++;
                examDateConfirmTextView.setText(m + "/" + d + "/" + y);
            }
        };

        // See sources
        reminderDateConfirmTextView = findViewById(R.id.examReminderDateConfirmTextView);
        Button pickReminderDateButton = findViewById(R.id.examPickReminderDateButton);
        pickReminderDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_WEEK);

                // Create a new dialog for the user to work with
                DatePickerDialog dialog = new DatePickerDialog(
                        ExamActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener2,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        // See sources
        mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            // Set the TextView to the user's chosen reminder date
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                m++;
                reminderDateConfirmTextView.setText(m + "/" + d + "/" + y);
            }
        };

        // Passing an intent to MainActivity with the new Exam information
        titleEditText = findViewById(R.id.examTitleEditText);
        descriptionEditText = findViewById(R.id.examDescriptionEditText);
        final TimePicker reminderTimePicker = findViewById(R.id.examReminderTimePicker);
        saveButton = findViewById(R.id.examSaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            // Gets ready to pass an intent back to MainActivity
            @Override
            public void onClick(View view) {
                int userSetHour = reminderTimePicker.getHour();
                int userSetMinute = reminderTimePicker.getMinute() - 1;
                Intent intent = new Intent();
                intent.putExtra("id", id);
                intent.putExtra("title", titleEditText.getText().toString());
                intent.putExtra("description", descriptionEditText.getText().toString());
                intent.putExtra("dueDate", examDateConfirmTextView.getText());
                intent.putExtra("reminderDate", reminderDateConfirmTextView.getText());
                intent.putExtra("reminderHour", userSetHour);
                intent.putExtra("reminderMinute", userSetMinute);
                setResult(Activity.RESULT_OK, intent);
                ExamActivity.this.finish();
            }
        });
    }
}