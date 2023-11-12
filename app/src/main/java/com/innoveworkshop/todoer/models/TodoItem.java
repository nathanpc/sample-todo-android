package com.innoveworkshop.todoer.models;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TodoItem {
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
