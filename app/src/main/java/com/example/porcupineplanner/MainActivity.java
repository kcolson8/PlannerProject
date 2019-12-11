/**
 * This program computes the logic for working with all planner items and notifications. It uses two menus:
 *      the main menu for adding planner items and the CAM menu deleting planner items. It also displays
 *      planner items and allows for editing upon selecting a planner item. Database storage is conducted
 *      in this activity as well.
 * CPSC 312-01, Fall 2019
 * Final Project - Porcupine Planner
 * Sources to Cite:
 *      Working with notifications with the help of these tutorial/discussion boards:
 *          https://developer.android.com/training/notify-user/build-notification
 *          https://stackoverflow.com/questions/36902667/how-to-schedule-notification-in-android
 *      Porcupine clip art on app icon and notification icon: <div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
 *
 * @author Kellie Colson and Elizabeth Larson
 * @version v1.0 12/11/19
 */

package com.example.porcupineplanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
    // Fields:
    //      NEW_HOMEWORK_REQUEST_CODE - only passed when creating a Homework planner item
    //      EDIT_HOMEWORK_REQUEST_CODE - only passed when editing a Homework planner item
    //      NEW_EXAM_REQUEST_CODE - only passed when creating an Exam planner item
    //      EDIT_EXAM_REQUEST_CODE - only passed when editing an Exam planner item
    //      NEW_REMINDER_REQUEST_CODE - only passed when creating a Reminder planner item
    //      EDIT_REMINDER_REQUEST_CODE - only passed when editing a Reminder planner item
    //      listView - ListView used to display planner items
    //      cursorAdaptor - connects the ListView to the app for the user's display
    //      CHANNEL_ID - identifier for the notification channel
    //      calendar - Calendar object for date storage
    //      noteTitle - the title of the note being displayed in the notification
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

    /**
     * Creates a notification channel for the notification to display
     * See sources
     */
    private void createNotificationChannel() {
        // Create a channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Uses a manager to register the channel
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * Schedule the notification to be displayed to the user
     * See sources
     *
     * @param context the Context in which the notification is being displayed
     * @param delay a long representation of how much time (in millis) there is between the scheduled reminder time and the current time
     * @param notificationId identifier for the notification
     */
    public void scheduleNotification(Context context, long delay, int notificationId) {
        // The notification will alert that user that they have an unfinished task
        // Notification is represented by a small porcupine clipart
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.porcupine)
                .setContentTitle("Reminder: You have an unfinished task")
                .setContentText(noteTitle)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Use an intent to build the notification and then build it
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent activity = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(activity);
        Notification notification = builder.build();
        Intent notificationIntent = new Intent(context, MyNotificationPublisher.class);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        // Calculate the delay between reminder creation and alert
        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    /**
     * Sets up the logic for working with the main menu, the CAM menu, and items in the planner's ListView
     *
     * @param savedInstanceState Bundle object that has the ability to restore the app to a previous state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();

        // Use the DatabaseOpenHelper object to display all stored planner notes
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

        // Click listener for when an item in the ListView is clicked to view/edit
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // Handles cases where one planner item is selected
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemType = openHelper.getItemType(position + 1);
                switch (itemType) {
                    case "homework":
                        // If a Homework item is selected...
                        Intent intent = new Intent(MainActivity.this, HomeworkActivity.class);
                        Homework selectedHomework = openHelper.getHomeworkByID(position + 1);
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
                        // If an Exam item is selected...
                        Intent examIntent = new Intent(MainActivity.this, ExamActivity.class);
                        Exam selectedExam = openHelper.getExamByID(position + 1);
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
                        // If a Reminder item is selected...
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

        // When the user long clicks on a note, they enter CAM
        // The CAM menu pops up and they can choose which planner items they want to delete
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            // Update the <number of items selected> selected banner on the menu as the user selects items in CAM
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                int numChecked = listView.getCheckedItemCount();
                actionMode.setTitle(numChecked + " selected");
            }

            // Inflate the CAM menu
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                // Get a reference to the MenuInflater
                MenuInflater menuInflater = getMenuInflater();
                menuInflater.inflate(R.menu.cam_menu, menu);
                return true;
            }
            // Not used in this program
            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            // Executes when user clicks a cam menu item
            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.deleteMenuItem:
                        // If they select the trash can on the CAM menu...
                        SparseBooleanArray selectedItems = listView.getCheckedItemPositions();
                        for(int i = 0; i < selectedItems.size(); i++){
                            int itemId = (int) listView.getItemIdAtPosition(selectedItems.keyAt(i));
                            openHelper.deleteOneItem(itemId);
                        }
                        cursorAdapter = new SimpleCursorAdapter(
                                MainActivity.this,
                                android.R.layout.simple_list_item_activated_1,
                                openHelper.getAllItemsCursor(),
                                new String[] {DatabaseOpenHelper.TITLE}, // First column in database
                                new int[] {android.R.id.text1}, // ID of text view to put data into
                                0
                        );
                        listView.setAdapter(cursorAdapter);
                        cursorAdapter.notifyDataSetChanged();

                        actionMode.finish(); // Exit CAM
                        return true;
                }
                return false;
            }

            // Not used in this program
            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
            }
        });
    }

    /**
     * Inflates the main menu for the user to interact with
     * This allows the user to add new Homework, Exam, or Reminder items
     *
     * @param menu MenuItem that the user can interact with
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Get a reference to the MenuInflater
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Override a callback that executes whenever an options menu action is clicked
     *
     * @param item MenuItem that the user can interact with
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.addHomeworkButton:
                // If they select the pencil on the menu...
                Intent intent = new Intent(MainActivity.this, HomeworkActivity.class);
                startActivityForResult(intent, NEW_HOMEWORK_REQUEST_CODE);
                return true;
            case R.id.addExamButton:
                // If they select the page on the menu...
                Intent examIntent = new Intent(MainActivity.this, ExamActivity.class);
                startActivityForResult(examIntent, NEW_EXAM_REQUEST_CODE);
                return true;
            case R.id.addReminderButton:
                // If they select the reminder bell on the menu...
                Intent intentReminder = new Intent(MainActivity.this, ReminderActivity.class);
                startActivityForResult(intentReminder, NEW_REMINDER_REQUEST_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Sets up the logic for working with the main menu, the CAM menu, and items in the planner's ListView
     *
     * @param requestCode used to determine what the database should do
     * @param resultCode used to ensure that the intent passeage was successful
     * @param data intent data from HomeworkActivity, ExamActivity, or ReminderActivity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // Retrieve data from the intent passed from HomeworkActivity, ExamActivity, or ReminderActivity
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
            }
            String reminderDate = data.getStringExtra("reminderDate");
            int reminderHour = data.getIntExtra("reminderHour", 0);
            int reminderMinute = data.getIntExtra("reminderMinute", 0);

            // Create a DatabaseOpenHelper object to interact with the database
            // Performs database operations based on intent request code
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

            // Use the database cursor to set up the planner ListView
            cursorAdapter = new SimpleCursorAdapter(
                    this,
                    android.R.layout.simple_list_item_activated_1,
                    openHelper.getAllItemsCursor(),
                    new String[] {DatabaseOpenHelper.TITLE},
                    new int[] {android.R.id.text1},
                    0
            );
            listView.setAdapter(cursorAdapter);

            // Format: [month, date, year]
            String[] parsedReminderDate = reminderDate.split("/");

            // Retrieve the current day stats
            int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            int currentMinute = calendar.get(Calendar.MINUTE);
            int currentSecond = calendar.get(Calendar.SECOND);
            int currentYear = calendar.get(Calendar.YEAR);
            int currentMonth = calendar.get(Calendar.MONTH);
            int currentDay = calendar.get(Calendar.DATE);

            // Retrieve the reminder day stats
            int finalReminderYear = Integer.parseInt(parsedReminderDate[2]);
            int finalReminderMonth = Integer.parseInt(parsedReminderDate[0]) - 1;
            int finalReminderDay = Integer.parseInt(parsedReminderDate[1]);

            // Creates two GregorianCalendars for the current date and the reminder date
            Date reminderCalendarDate = new GregorianCalendar(finalReminderYear, finalReminderMonth,finalReminderDay, reminderHour, reminderMinute, 0).getTime();
            Date currentCalendarDate = new GregorianCalendar(currentYear, currentMonth, currentDay, currentHour, currentMinute, currentSecond).getTime();

            // Delay between current time and reminder time and
            long timeDayDifference = reminderCalendarDate.getTime() - currentCalendarDate.getTime();

            // Schedule the notification based off of this delay
            noteTitle = title;
            scheduleNotification(this, timeDayDifference, 1);
        }
    }
}