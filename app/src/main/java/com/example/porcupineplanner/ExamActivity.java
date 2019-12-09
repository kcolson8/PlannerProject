package com.example.porcupineplanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;


public class ExamActivity extends AppCompatActivity {
    TextView examDateConfirmTextView;
    TextView reminderDateConfirmTextView;
    Button saveButton;
    EditText titleEditText;
    EditText descriptionEditText;
    int id;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatePickerDialog.OnDateSetListener mDateSetListener2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        //grabs intent when editing an exam object to set fields the same as user last saved it
        Intent intent = getIntent();
        if(intent != null){
            id = intent.getIntExtra("id",0);
            Log.d("myTag", "id in examActivity passing in intent: " + id);

            String title = intent.getStringExtra("title");
            String description = intent.getStringExtra("description");
            String examDate = intent.getStringExtra("dueDate");
            String reminderDate = intent.getStringExtra("reminderDate");
            int reminderHour = intent.getIntExtra("reminderHour", 0);
            int reminderMinute = intent.getIntExtra("reminderMinute", 0);

            TextView dueDateConfirmTextView = findViewById(R.id.examDateConfirmTextView);
            TextView reminderDateConfirmTextView = findViewById(R.id.examReminderDateConfirmTextView);
            EditText titleEditText = findViewById(R.id.examTitleEditText);
            EditText descriptionEditText = findViewById(R.id.examDescriptionEditText);
            TimePicker timePicker = findViewById(R.id.examReminderTimePicker);

            titleEditText.setText(title);
            descriptionEditText.setText(description);
            dueDateConfirmTextView.setText(examDate);
            reminderDateConfirmTextView.setText(reminderDate);
            timePicker.setHour(reminderHour);
            timePicker.setMinute(reminderMinute);
        }

        //Most of the code below is from this video:
        //https://www.youtube.com/watch?v=hwe1abDO2Ag
        examDateConfirmTextView = findViewById(R.id.examDateConfirmTextView);
        Button pickDateButton = findViewById(R.id.examPickDateButton);
        pickDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_WEEK);

                DatePickerDialog dialog = new DatePickerDialog(
                        ExamActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                m++;
                examDateConfirmTextView.setText(m + "/" + d + "/" + y);
            }
        };

        reminderDateConfirmTextView = findViewById(R.id.examReminderDateConfirmTextView);
        Button pickReminderDateButton = findViewById(R.id.examPickReminderDateButton);
        pickReminderDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_WEEK);

                DatePickerDialog dialog = new DatePickerDialog(
                        ExamActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener2,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                m++;
                reminderDateConfirmTextView.setText(m + "/" + d + "/" + y);
            }
        };

        titleEditText = findViewById(R.id.examTitleEditText);
        descriptionEditText = findViewById(R.id.examDescriptionEditText);
        final TimePicker reminderTimePicker = findViewById(R.id.examReminderTimePicker);
        saveButton = findViewById(R.id.examSaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userSetHour = reminderTimePicker.getHour();
                int userSetMinute = reminderTimePicker.getMinute() - 1;
                Intent intent = new Intent();
                intent.putExtra("id", id);
                Log.d("myTag", "id in examActivity: " + id);
                intent.putExtra("title", titleEditText.getText().toString());
                intent.putExtra("description", descriptionEditText.getText().toString());
                intent.putExtra("dueDate", examDateConfirmTextView.getText());
                intent.putExtra("reminderDate", reminderDateConfirmTextView.getText());
                intent.putExtra("reminderHour", userSetHour);
                intent.putExtra("reminderMinute", userSetMinute);
                Log.d("myTag", "timepicker hour: " + userSetHour + " timepicker minute: " + userSetMinute);
                setResult(Activity.RESULT_OK, intent);
                ExamActivity.this.finish();
            }
        });
    }
}

