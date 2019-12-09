// notifications:
// https://developer.android.com/training/notify-user/build-notification

package com.example.porcupineplanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    static final int NEW_HOMEWORK_REQUEST_CODE = 1;
    static final int EDIT_HOMEWORK_REQUEST_CODE = 2;
    static final int NEW_EXAM_REQUEST_CODE = 3;
    static final int EDIT_EXAM_REQUEST_CODE = 4;
    static final int NEW_REMINDER_REQUEST_CODE = 5;
    static final int EDIT_REMINDER_REQUEST_CODE = 6;
    ListView listView;
    SimpleCursorAdapter cursorAdapter;
    final String CHANNEL_ID = "channel id";
    Calendar calendar = Calendar.getInstance();
    String noteTitle = "";

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void scheduleNotification(Context context, long delay, int notificationId) {//delay is after how much time(in millis) from current time you want to schedule the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                //<div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
                .setSmallIcon(R.drawable.porcupine)
                .setContentTitle("Reminder: You have an unfinished task")
                .setContentText(noteTitle)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent activity = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(activity);

        Notification notification = builder.build();

        Intent notificationIntent = new Intent(context, MyNotificationPublisher.class);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();

        listView = findViewById(R.id.listView);
        final DatabaseOpenHelper openHelper = new DatabaseOpenHelper(this);
        cursorAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_activated_1,
                openHelper.getAllItemsCursor(),
                new String[] {DatabaseOpenHelper.TITLE},
                new int[] {android.R.id.text1},
                0
        );
        listView.setAdapter(cursorAdapter);

        //click listener for when an item in the listView is clicked to view/edit
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemType = openHelper.getItemType(position + 1);
                switch (itemType) {
                    case "homework":
                        //if homework item is selected...
                        Intent intent = new Intent(MainActivity.this, HomeworkActivity.class);
                        Homework selectedHomework = openHelper.getHomeworkByID(position + 1);
                        Log.d("myTag", "selectedHomework title: " + selectedHomework.getTitle());
                        intent.putExtra("id", position + 1);
                        intent.putExtra("title", selectedHomework.getTitle());
                        intent.putExtra("description", selectedHomework.getDescription());
                        intent.putExtra("dueDate", selectedHomework.getDueDate());
                        intent.putExtra("reminderDate", selectedHomework.getReminderDate());
                        intent.putExtra("reminderHour", selectedHomework.getReminderHour());
                        intent.putExtra("reminderMinute", selectedHomework.getReminderMinute());

                        startActivityForResult(intent, EDIT_HOMEWORK_REQUEST_CODE);
                        break;
                    case "exam":
                        Intent examIntent = new Intent(MainActivity.this, ExamActivity.class);
                        Exam selectedExam = openHelper.getExamByID(position + 1);
                        Log.d("myTag", "selectedExam title: " + selectedExam.getTitle());
                        examIntent.putExtra("id", position + 1);
                        examIntent.putExtra("title", selectedExam.getTitle());
                        examIntent.putExtra("description", selectedExam.getDescription());
                        examIntent.putExtra("dueDate", selectedExam.getDueDate());
                        examIntent.putExtra("reminderDate", selectedExam.getReminderDate());
                        examIntent.putExtra("reminderHour", selectedExam.getReminderHour());
                        examIntent.putExtra("reminderMinute", selectedExam.getReminderMinute());

                        startActivityForResult(examIntent, EDIT_EXAM_REQUEST_CODE);
                        break;
                    case "reminder":
                        Intent intentReminder = new Intent(MainActivity.this, ReminderActivity.class);
                        Reminder selectedReminder = openHelper.getReminderByID(position + 1);
                        intentReminder.putExtra("id", position + 1);
                        intentReminder.putExtra("title", selectedReminder.getTitle());
                        intentReminder.putExtra("description", selectedReminder.getDescription());
                        intentReminder.putExtra("reminderDate", selectedReminder.getReminderDate());
                        intentReminder.putExtra("reminderHour", selectedReminder.getReminderHour());
                        intentReminder.putExtra("reminderMinute", selectedReminder.getReminderMinute());

                        startActivityForResult(intentReminder, EDIT_REMINDER_REQUEST_CODE);
                        break;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // get a reference to the MenuInflater
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // override a callback that executes whenever an options menu action is clicked

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.addHomeworkButton:
                Intent intent = new Intent(MainActivity.this, HomeworkActivity.class);
                startActivityForResult(intent, NEW_HOMEWORK_REQUEST_CODE);
                return true;
            case R.id.addExamButton:
                Intent examIntent = new Intent(MainActivity.this, ExamActivity.class);
                startActivityForResult(examIntent, NEW_EXAM_REQUEST_CODE);
                return true;
            case R.id.addReminderButton:
                Intent intentReminder = new Intent(MainActivity.this, ReminderActivity.class);
                startActivityForResult(intentReminder, NEW_REMINDER_REQUEST_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            int id = data.getIntExtra("id", 0);
            String title = data.getStringExtra("title");
            String description = data.getStringExtra("description");
            // Reminder items don't have a due date/store null here in the db
            // To avoid any problems, we used a try catch block
            String dueDate;
            try {
                dueDate = data.getStringExtra("dueDate");
            } catch (Exception e) {
                dueDate = "";
                Log.d("myTag", "        REMINDER ITEM!!!");
            }
            String reminderDate = data.getStringExtra("reminderDate");
            int reminderHour = data.getIntExtra("reminderHour", 0);
            int reminderMinute = data.getIntExtra("reminderMinute", 0);

            DatabaseOpenHelper openHelper = new DatabaseOpenHelper(this);
            if(requestCode == NEW_HOMEWORK_REQUEST_CODE) {
                openHelper.insertHomeworkItem(new Homework(title, description, dueDate, reminderDate, reminderHour, reminderMinute));
            } else if(requestCode == EDIT_HOMEWORK_REQUEST_CODE){
                openHelper.updateHomeworkById(id, new Homework(title, description, dueDate, reminderDate, reminderHour, reminderMinute));
            } else if(requestCode == NEW_EXAM_REQUEST_CODE){
                openHelper.insertExamItem(new Exam(title, description, dueDate, reminderDate, reminderHour, reminderMinute));
            } else if(requestCode == EDIT_EXAM_REQUEST_CODE){
                openHelper.updateExamById(id,new Exam(title, description, dueDate, reminderDate, reminderHour, reminderMinute));
            } else if(requestCode == NEW_REMINDER_REQUEST_CODE) {
                openHelper.insertReminderItem(new Reminder(title, description, reminderDate, reminderHour, reminderMinute));
            } else if(requestCode == EDIT_REMINDER_REQUEST_CODE) {
                openHelper.updateReminderById(id, new Reminder(title, description, reminderDate, reminderHour, reminderMinute));
            }

            cursorAdapter = new SimpleCursorAdapter(
                    this,
                    android.R.layout.simple_list_item_activated_1,
                    openHelper.getAllItemsCursor(),
                    new String[] {DatabaseOpenHelper.TITLE},
                    new int[] {android.R.id.text1},
                    0
            );
            listView.setAdapter(cursorAdapter);

            String[] parsedReminderDate = reminderDate.split("/"); //format: [month, date, year]

            int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            int currentMinute = calendar.get(Calendar.MINUTE);
            int currentSecond = calendar.get(Calendar.SECOND);
            int currentYear = calendar.get(Calendar.YEAR);
            int currentMonth = calendar.get(Calendar.MONTH);
            int currentDay = calendar.get(Calendar.DATE);

            int finalReminderYear = Integer.parseInt(parsedReminderDate[2]);
            int finalReminderMonth = Integer.parseInt(parsedReminderDate[0]) - 1;
            int finalReminderDay = Integer.parseInt(parsedReminderDate[1]);

            Date reminderCalendarDate = new GregorianCalendar(finalReminderYear, finalReminderMonth,finalReminderDay, reminderHour, reminderMinute, 0).getTime();
            Log.d("myTag", "reminder date: " + reminderCalendarDate.toString());

            Date currentCalendarDate = new GregorianCalendar(currentYear, currentMonth, currentDay, currentHour, currentMinute, currentSecond).getTime();
            Log.d("myTag", "current date: " + currentCalendarDate.toString());

            long timeDayDifference = reminderCalendarDate.getTime() - currentCalendarDate.getTime(); //should become delay;
            Log.d("myTag", "time difference in milliseconds: " + timeDayDifference);

            noteTitle = title;
            scheduleNotification(this, timeDayDifference, 1);
        }
    }
}