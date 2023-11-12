package com.innoveworkshop.todoer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;

import com.innoveworkshop.todoer.models.TodoItem;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.function.ToDoubleFunction;

public class ItemEditorActivity extends AppCompatActivity {
    protected EditText titleEdit;
    protected CheckBox doneCheck;
    protected CalendarView calendar;
    protected EditText notesEdit;

    protected int listPosition;
    protected TodoItem item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_editor);

        // Get the item passed from the previous activity.
        Intent intent = getIntent();
        listPosition = intent.getIntExtra("position", -1);
        item = (TodoItem) intent.getSerializableExtra("item");

        setupComponents();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_editor, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_save_item) {
            // ActionBar "Save" button.
            commitView();
            this.item.save();

            // Setup the data to be sent back to the previous activity.
            Intent returnIntent = new Intent();
            returnIntent.putExtra("position", this.listPosition);
            returnIntent.putExtra("item", this.item);
            setResult(AppCompatActivity.RESULT_OK, returnIntent);

            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupComponents() {
        // Setup the ActionBar.
        setSupportActionBar(findViewById(R.id.toolbar));

        // Get components into properties.
        titleEdit = (EditText) findViewById(R.id.title_edit);
        doneCheck = (CheckBox) findViewById(R.id.done_check);
        calendar = (CalendarView) findViewById(R.id.calendar);
        notesEdit = (EditText) findViewById(R.id.notes_edit);

        // Populate the widgets with data from our object.
        populateView();
    }

    protected void populateView() {
        titleEdit.setText(item.getTitle());
        doneCheck.setChecked(item.isDone());
        calendar.setDate(item.getDate().getTimeInMillis());
        notesEdit.setText(item.getNotes());
    }

    protected void commitView() {
        item.setTitle(titleEdit.getText().toString());
        item.setDone(doneCheck.isChecked());
        item.setNotes(notesEdit.getText().toString());

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(this.calendar.getDate());
        item.setDate(calendar);
    }
}