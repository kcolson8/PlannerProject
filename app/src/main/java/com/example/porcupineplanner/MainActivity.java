package com.example.porcupineplanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {
    static final int NEW_HOMEWORK_REQUEST_CODE = 1;
    ListView listView;
    SimpleCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                //Toast.makeText(this, "TODO: show preferences", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.addReminderButton:
                //Toast.makeText(this, "TODO: show about app", Toast.LENGTH_SHORT).show();
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
        }
    }


}
