package com.example.porcupineplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class HomeworkActivity extends AppCompatActivity {
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    Date currentTime = Calendar.getInstance().getTime();
    TextView dueDateConfirmTextView;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);

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