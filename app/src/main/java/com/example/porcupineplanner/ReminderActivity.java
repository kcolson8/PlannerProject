package com.example.porcupineplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class ReminderActivity extends AppCompatActivity {
    EditText reminderTitleEditText;
    EditText reminderDescriptionEditText;
    TextView reminderDateConfirmTextView;
    Button reminderSaveButton;
    int id;
    private DatePickerDialog.OnDateSetListener reminderDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        Intent intent = getIntent();
        if(intent != null){
            id = intent.getIntExtra("id",0);
            String title = intent.getStringExtra("title");
            String description = intent.getStringExtra("description");
            String reminderDate = intent.getStringExtra("reminderDate");
            int reminderHour = intent.getIntExtra("reminderHour", 0);
            int reminderMinute = intent.getIntExtra("reminderMinute", 0);

            EditText reminderTitleEditText = findViewById(R.id.reminderTitleEditText);
            EditText reminderDescriptionEditText = findViewById(R.id.reminderDescriptionEditText);
            TextView reminderDateConfirmTextView = findViewById(R.id.reminderDateConfirmTextView);
            TimePicker timePicker = findViewById(R.id.reminderTimePicker);
            Button reminderSaveButton = findViewById(R.id.reminderSaveButton);

            reminderTitleEditText.setText(title);
            reminderDescriptionEditText.setText(description);
            reminderDateConfirmTextView.setText(reminderDate);
            timePicker.setHour(reminderHour);
            timePicker.setMinute(reminderMinute);
        }

        //Most of the code below is from this video:
        //https://www.youtube.com/watch?v=hwe1abDO2Ag
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
                        ReminderActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        reminderDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        reminderDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                m++;
                reminderDateConfirmTextView.setText(m + "/" + d + "/" + y);
            }
        };

        reminderTitleEditText = findViewById(R.id.reminderTitleEditText);
        reminderDescriptionEditText = findViewById(R.id.reminderDescriptionEditText);
        final TimePicker reminderTimePicker = findViewById(R.id.reminderTimePicker);
        reminderSaveButton = findViewById(R.id.reminderSaveButton);
        reminderSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userSetHour = reminderTimePicker.getHour();
                int userSetMinute = reminderTimePicker.getMinute() - 1;
                Intent intent = new Intent();
                intent.putExtra("id", id);
                intent.putExtra("title", reminderTitleEditText.getText().toString());
                intent.putExtra("description", reminderDescriptionEditText.getText().toString());
                intent.putExtra("reminderDate", reminderDateConfirmTextView.getText());
                intent.putExtra("reminderHour", userSetHour);
                intent.putExtra("reminderMinute", userSetMinute);
                setResult(Activity.RESULT_OK, intent);
                ReminderActivity.this.finish();
            }
        });
    }
}