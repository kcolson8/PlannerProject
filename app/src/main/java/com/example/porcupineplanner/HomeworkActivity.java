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


public class HomeworkActivity extends AppCompatActivity {
    TextView dueDateConfirmTextView;
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
        setContentView(R.layout.activity_homework);

        //grabs intent when editing an homework object to set fields the same as user last saved it
        Intent intent = getIntent();
        if(intent != null){
            id = intent.getIntExtra("id",0);
            Log.d("myTag", "id in homeworkactivity passing in intent: " + id);
            String title = intent.getStringExtra("title");

            //String subject = intent.getStringExtra("subject");

            String description = intent.getStringExtra("description");
            String dueDate = intent.getStringExtra("dueDate");
            String reminderDate = intent.getStringExtra("reminderDate");
            int reminderHour = intent.getIntExtra("reminderHour", 0);
            int reminderMinute = intent.getIntExtra("reminderMinute", 0);

            TextView dueDateConfirmTextView = findViewById(R.id.dueDateConfirmTextView);
            TextView reminderDateConfirmTextView = findViewById(R.id.reminderDateConfirmTextView);
            Button saveButton = findViewById(R.id.saveButton);
            EditText titleEditText = findViewById(R.id.titleEditText);
            EditText descriptionEditText = findViewById(R.id.descriptionEditText);
            TimePicker timePicker = findViewById(R.id.reminderTimePicker);

            titleEditText.setText(title);
            descriptionEditText.setText(description);
            dueDateConfirmTextView.setText(dueDate);
            reminderDateConfirmTextView.setText(reminderDate);
            timePicker.setHour(reminderHour);
            timePicker.setMinute(reminderMinute);
        }

        /*
        //Sends note content back to MainActivity when done button is pressed
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Checks if note doesn't have a title set and notifies user via Toast message
                if(titleEditText.getText().toString().equals("")){
                    Toast.makeText(NoteActivity.this, "Missing title", Toast.LENGTH_SHORT).show();
                }
                //Checks if note doesn't have note content entered and notifies user via Toast message
                else if (contentEditText.getText().toString().equals("")){
                    Toast.makeText(NoteActivity.this, "Missing note content", Toast.LENGTH_SHORT).show();
                } else { //both the note title and content have been entered
                    Intent intent = new Intent();
                    intent.putExtra("id", id);
                    Log.d(TAG, "returning id: " + id);
                    intent.putExtra("title", titleEditText.getText().toString());
                    intent.putExtra("label", labelSpinner.getSelectedItem().toString());
                    intent.putExtra("noteContent", contentEditText.getText().toString());
                    setResult(Activity.RESULT_OK, intent);
                    NoteActivity.this.finish();
                }
            }
        });
        */


        //Most of the code below is from this video:
        //https://www.youtube.com/watch?v=hwe1abDO2Ag
        dueDateConfirmTextView = findViewById(R.id.dueDateConfirmTextView);
        Button pickDueDateButton = findViewById(R.id.pickDueDateButton);
        pickDueDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_WEEK);

                DatePickerDialog dialog = new DatePickerDialog(
                        HomeworkActivity.this,
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
                dueDateConfirmTextView.setText(m + "/" + d + "/" + y);
            }
        };

        reminderDateConfirmTextView = findViewById(R.id.reminderDateConfirmTextView);
        Button pickReminderDateButton = findViewById(R.id.pickReminderDateButton);
        pickReminderDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_WEEK);

                DatePickerDialog dialog = new DatePickerDialog(
                        HomeworkActivity.this,
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

        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        final TimePicker reminderTimePicker = findViewById(R.id.reminderTimePicker);
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userSetHour = reminderTimePicker.getHour();
                int userSetMinute = reminderTimePicker.getMinute() - 1;
                Intent intent = new Intent();
                intent.putExtra("id", id);
                Log.d("myTag", "id in homeworkactivity: " + id);
                intent.putExtra("title", titleEditText.getText().toString());
                intent.putExtra("description", descriptionEditText.getText().toString());
                intent.putExtra("dueDate", dueDateConfirmTextView.getText());
                intent.putExtra("reminderDate", reminderDateConfirmTextView.getText());
                intent.putExtra("reminderHour", userSetHour);
                intent.putExtra("reminderMinute", userSetMinute);
                Log.d("myTag", "timepicker hour: " + userSetHour + " timepicker minute: " + userSetMinute);
                setResult(Activity.RESULT_OK, intent);
                HomeworkActivity.this.finish();
            }
        });
    }
}






// Old code

 /*DatePickerDialog dp=new DatePickerDialog(HomeworkActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int myYear, int myMonth, int myDay) {
                        // TODO: fix
                        dueDateConfirmTextView.setText(myMonth + "/" + myDay + "/" + myYear);
                    }
                },year,month,day);
                dp.getDatePicker().setMaxDate(cal.getTimeInMillis());
                dp.show();*/



                /*calendar = Calendar.getInstance();
                //int day = calendar.get(Calendar.DAY_OF_MONTH);
                //int month = calendar.get(Calendar.MONTH);
                //int year = calendar.get(Calendar.YEAR);

                // Min API level 24
                datePickerDialog = new DatePickerDialog(HomeworkActivity.this);
                datePickerDialog.show();
                Log.d("myTag", "myTag: " + datePickerDialog.getDatePicker());*/

        /*Button pickReminderDateButton = findViewById(R.id.pickReminderDateButton);
        pickDueDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Min API level 24
                datePickerDialog = new DatePickerDialog(HomeworkActivity.this);
                datePickerDialog.show();
            }
        });*/
    /*}
}
*/