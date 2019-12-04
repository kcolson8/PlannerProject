// notifications:
// https://developer.android.com/training/notify-user/build-notification

package com.example.porcupineplanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {
    static final int NEW_HOMEWORK_REQUEST_CODE = 1;
    ListView listView;
    SimpleCursorAdapter cursorAdapter;
    final String CHANNEL_ID = "channel id";

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
                //.setSmallIcon(R.drawable.notification_icon)
                //.setContentTitle(textTitle)
                //.setContentText(textContent)
                //<div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
                .setSmallIcon(R.drawable.porcupine)
                .setContentTitle("Hello world!")
                .setContentText("You just got a notification")
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




        /*NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // notificationId is a unique int for each notification that you must define
        int notificationId = 1;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                //.setSmallIcon(R.drawable.notification_icon)
                //.setContentTitle(textTitle)
                //.setContentText(textContent)
                //<div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
                .setSmallIcon(R.drawable.porcupine)
                .setContentTitle("Hello world!")
                .setContentText("You just got a notification")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notificationManager.notify(notificationId, builder.build());
        createNotificationChannel();*/






        listView = findViewById(R.id.listView);
        final DatabaseOpenHelper openHelper = new DatabaseOpenHelper(this);
        cursorAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_activated_1,
                openHelper.getSelectAllHomeworkCursor(),
                new String[] {DatabaseOpenHelper.TITLE},
                new int[] {android.R.id.text1},
                0
        );
        listView.setAdapter(cursorAdapter);
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
                //TODO: get this working
                return true;
            case R.id.addReminderButton:
                //TODO: get this working
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_HOMEWORK_REQUEST_CODE && resultCode == RESULT_OK) {
            String title = data.getStringExtra("title");
            String className = data.getStringExtra("class");
            String description = data.getStringExtra("description");
            String dueDate = data.getStringExtra("dueDate");
            String reminderDate = data.getStringExtra("reminderDate");
            int reminderHour = data.getIntExtra("reminderHour", 0);
            int reminderMinute = data.getIntExtra("reminderMinute", 0);

            DatabaseOpenHelper openHelper = new DatabaseOpenHelper(this);
            openHelper.insertHomeworkItem(new Homework(title, className, description, dueDate, reminderDate, reminderHour, reminderMinute));

            cursorAdapter = new SimpleCursorAdapter(
                    this,
                    android.R.layout.simple_list_item_activated_1,
                    openHelper.getSelectAllHomeworkCursor(),
                    new String[] {DatabaseOpenHelper.TITLE},
                    new int[] {android.R.id.text1},
                    0
            );
            listView.setAdapter(cursorAdapter);

            scheduleNotification(this, 1000, 1);
        }
    }
}
