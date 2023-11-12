package com.innoveworkshop.todoer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.innoveworkshop.todoer.adapters.TodoItemRowAdapter;
import com.innoveworkshop.todoer.models.TodoItem;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
    private static final int EDITOR_ACTIVITY_RETURN_ID = 1;

    protected RecyclerView itemsListView;
    protected TodoItemRowAdapter itemRowAdapter;

    protected ArrayList<TodoItem> itemsList;

    /**
     * Called when the activity is first started.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the items from the web server.
        itemsList = TodoItem.List();

        setupComponents();
    }

    /**
     * Adds our menu items to the ActionBar.
     *
     * @param menu Menu object from the system that will hold our menu items.
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Event handler when menu items are selected.
     *
     * @param item Menu item that was selected.
     *
     * @return True if we handled the event, False if Android should take care of it.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_new_item) {
            // ActionBar "Add" button.
            Intent intent = new Intent(MainActivity.this, ItemEditorActivity.class);
            intent.putExtra("position", -1);
            intent.putExtra("item", new TodoItem());

            startActivityForResult(intent, EDITOR_ACTIVITY_RETURN_ID);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Gets the result back from another acitivity.
     *
     * @param requestCode Code sent with the {@link #startActivityForResult(Intent, int)}
     * @param resultCode  Code that was returned from the other activity (usually a flag of success).
     * @param data        Data sent back from the other activity in the form of an Intent.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Must be called always and before everything.
        super.onActivityResult(requestCode, resultCode, data);

        // Check which activity returned to us.
        if (requestCode == EDITOR_ACTIVITY_RETURN_ID) {
            // Check if the activity was successful.
            if (resultCode == AppCompatActivity.RESULT_OK) {
                // Get extras returned to us.
                int position = data.getIntExtra("position", -1);
                TodoItem updatedItem = (TodoItem) data.getSerializableExtra("item");

                if (position == -1) {
                    // Add the item to the list it was created new.
                    itemsList.add(updatedItem);
                    itemRowAdapter.notifyItemInserted(itemsList.size() - 1);
                } else {
                    // Updates an existing item on the list.
                    itemsList.set(position, updatedItem);
                    itemRowAdapter.notifyItemChanged(position);
                }
            }
        }
    }

    /**
     * Sets up the components and event handlers in the activity.
     */
    private void setupComponents() {
        // Setup the ActionBar.
        setSupportActionBar(findViewById(R.id.toolbar));

        // Set up row adapter with our items list.
        itemRowAdapter = new TodoItemRowAdapter(this, itemsList);
        itemRowAdapter.setOnClickListener(new TodoItemRowAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // Place our clicked item object in the intent to send to the other activity.
                Intent intent = new Intent(MainActivity.this, ItemEditorActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("item", itemsList.get(position));

                startActivityForResult(intent, EDITOR_ACTIVITY_RETURN_ID);
            }
        });

        // Set up the items recycler view.
        itemsListView = (RecyclerView) findViewById(R.id.todo_list);
        itemsListView.setLayoutManager(new LinearLayoutManager(this));
        itemsListView.setAdapter(itemRowAdapter);
    }
}