package com.innoveworkshop.todoer.models;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.innoveworkshop.todoer.utilities.CalendarJsonAdapter;
import com.innoveworkshop.todoer.utilities.WebRequest;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TodoItem implements Serializable {
    private int id;
    private boolean done;
    private String title;
    @JsonAdapter(CalendarJsonAdapter.class)
    private Calendar date;
    private String notes;

    public TodoItem() {
        this(0, false, "", new GregorianCalendar(), "");
    }

    public TodoItem(int id, boolean done, String title, Calendar date, String notes) {
        this.id = id;
        this.done = done;
        this.title = title;
        this.date = date;
        this.notes = notes;
    }

    /**
     * Gets a list of items from the web server.
     *
     * @param response List of items fetched from the web server.
     */
    public static void List(ListResponse response) {
        ArrayList<TodoItem> items = new ArrayList<TodoItem>();

        // Fetch a list of items from the web server and populate the list with them.
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    try {
                        WebRequest req = new WebRequest(new URL(
                                WebRequest.LOCALHOST + "/list"));
                        String resp = req.performGetRequest();

                        // Get the array from the response.
                        JsonObject json = new Gson().fromJson(resp, JsonObject.class);
                        JsonArray arr = json.getAsJsonArray("items");
                        ArrayList<TodoItem> items = new ArrayList<TodoItem>();
                        for (JsonElement elem : arr) {
                            items.add(new Gson().fromJson(elem, TodoItem.class));
                        }

                        response.response(items);
                    } catch (Exception e) {
                        Toast.makeText(null, "Web request failed: " + e.toString(),
                                Toast.LENGTH_LONG).show();
                        Log.e("TodoItem", e.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    /**
     * Gets the object from the web server by its ID in the database.
     *
     * @param id ID of the item in the database.
     * @param response Object with data from our web server.
     */
    public static void GetById(int id, GetByIdResponse response) {
        // Fetch the item from the web server using its id and populate the object.
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    try {
                        WebRequest req = new WebRequest(new URL(
                                WebRequest.LOCALHOST + "/todo/" + id));
                        String resp = req.performGetRequest();

                        response.response(new Gson().fromJson(resp, TodoItem.class));
                    } catch (Exception e) {
                        Toast.makeText(null, "Web request failed: " + e.toString(),
                                Toast.LENGTH_LONG).show();
                        Log.e("TodoItem", e.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    /**
     * Saves the object information to the database.
     */
    public void save() {
        // Send the object's data to our web server and update the database there.
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    try {
                        if (id == 0) {
                            // This is a brand new object and must be a INSERT in the database.
                            WebRequest req = new WebRequest(new URL(
                                    WebRequest.LOCALHOST + "/todo/new"));
                            String response = req.performPostRequest(TodoItem.this);

                            // Get the new ID from the server's response.
                            TodoItem respItem = new Gson().fromJson(response, TodoItem.class);
                            id = respItem.getId();
                        } else {
                            // This is an update to an existing object and must use UPDATE in the database.
                            WebRequest req = new WebRequest(new URL(
                                    WebRequest.LOCALHOST + "/todo/" + id));
                            req.performPostRequest(TodoItem.this);
                        }
                    } catch (Exception e) {
                        Toast.makeText(null, "Web request failed: " + e.toString(),
                                Toast.LENGTH_LONG).show();
                        Log.e("TodoItem", e.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public int getId() {
        return id;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public interface ListResponse {
        public void response(ArrayList<TodoItem> items);
    }

    public interface GetByIdResponse {
        public void response(TodoItem item);
    }
}
