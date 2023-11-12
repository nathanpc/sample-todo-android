package com.innoveworkshop.todoer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.innoveworkshop.todoer.adapters.TodoItemRowAdapter;
import com.innoveworkshop.todoer.models.TodoItem;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
    protected RecyclerView itemsListView;
    protected TodoItemRowAdapter itemRowAdapter;

    protected ArrayList<TodoItem> itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the items from the web server.
        itemsList = TodoItem.List();

        setupComponents();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_new_item) {
            // ActionBar "Add" button.
            Intent intent = new Intent(MainActivity.this, ItemEditorActivity.class);

            intent.putExtra("item", new TodoItem());

            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupComponents() {
        // Setup the ActionBar.
        setSupportActionBar(findViewById(R.id.toolbar));

        // Set up row adapter with our items list.
        itemRowAdapter = new TodoItemRowAdapter(this, itemsList);

        // Set up the items recycler view.
        itemsListView = (RecyclerView) findViewById(R.id.todo_list);
        itemsListView.setLayoutManager(new LinearLayoutManager(this));
        itemsListView.setAdapter(itemRowAdapter);
    }
}