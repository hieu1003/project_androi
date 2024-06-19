package com.example.project_androi.model;

public class ItemDay implements java.io.Serializable{
    private int day;
    private int month;
    private int year;
    private int lunarDay;
    private int lunarMonth;
    private int lunarYear;
    private int weekday;
    private boolean isEvent;

    public ItemDay(int day, int month, int year, int lunarDay, int lunarMonth, int lunarYear, int weekday, boolean isEvent) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.lunarDay = lunarDay;
        this.lunarMonth = lunarMonth;
        this.lunarYear = lunarYear;
        this.weekday = weekday;
        this.isEvent = isEvent;
    }

    public ItemDay(int day, int month, int year, int lunarDay, int lunarMonth, int lunarYear, int weekday) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.lunarDay = lunarDay;
        this.lunarMonth = lunarMonth;
        this.lunarYear = lunarYear;
        this.weekday = weekday;
    }

    public boolean isEvent() {
        if (isEvent) return true;
        return false;
    }

    public void setEvent(boolean event) {
        isEvent = event;
    }

    public int getWeekday() {

        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public ItemDay() {
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

    public int getLunarDay() {
        return lunarDay;
    }

    public void setLunarDay(int lunarDay) {
        this.lunarDay = lunarDay;
    }

    public int getLunarMonth() {
        return lunarMonth;
    }

    public void setLunarMonth(int lunarMonth) {
        this.lunarMonth = lunarMonth;
    }

    public int getLunarYear() {
        return lunarYear;
    }

    public void setLunarYear(int lunarYear) {
        this.lunarYear = lunarYear;
    }


}
