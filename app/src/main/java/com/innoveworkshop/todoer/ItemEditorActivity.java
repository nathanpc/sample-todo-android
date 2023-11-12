package com.innoveworkshop.todoer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ItemEditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_editor);

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
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupComponents() {
        // Setup the ActionBar.
        setSupportActionBar(findViewById(R.id.toolbar));
    }
}