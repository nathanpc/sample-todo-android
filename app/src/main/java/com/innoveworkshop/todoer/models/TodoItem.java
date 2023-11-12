package com.innoveworkshop.todoer.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TodoItem implements Serializable {
    private int id;
    private boolean done;
    private String title;
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
     * @return List of items fetched from the web server.
     */
    public static ArrayList<TodoItem> List() {
        ArrayList<TodoItem> items = new ArrayList<TodoItem>();

        // TODO: Fetch a list of items from the web server and populate the list with them.

        // Simulate a fetch from the web server.
        items.add(new TodoItem(1, false, "First todo item", new GregorianCalendar(),
                "Some description"));
        items.add(new TodoItem(2, true, "Finished task", new GregorianCalendar(),
                "A finished task"));
        items.add(new TodoItem(3, false, "A task for the future",
                new GregorianCalendar(2023, 12, 10), ""));
        items.add(new TodoItem(4, false, "Play Mariah Carey non-stop",
                new GregorianCalendar(2023, 12, 24),
                "All I want for christmas is you..."));

        return items;
    }

    /**
     * Gets the object from the web server by its ID in the database.
     *
     * @param id ID of the item in the database.
     *
     * @return Object with data from our web server.
     */
    public static TodoItem GetById(int id) {
        // TODO: Fetch the item from the web server using its id and populate the object.

        return new TodoItem(id, false, "Some title from the server", new GregorianCalendar(),
                "Some description that we also get from the web server");
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
}
