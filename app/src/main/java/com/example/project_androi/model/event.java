package com.example.project_androi.model;

public class event implements java.io.Serializable{
    private String id;
    private String title;
    private int day;
    private int month;
    private int year;
    private String time;
    private String note;

    public event() {
    }

    public event(String title, int day, int month, int year, String time, String note) {
        this.title = title;
        this.day = day;
        this.month = month;
        this.year = year;
        this.time = time;
        this.note = note;
    }

    public event(String id, String title, int day, int month, int year, String time, String note) {
        this.id = id;
        this.title = title;
        this.day = day;
        this.month = month;
        this.year = year;
        this.time = time;
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
