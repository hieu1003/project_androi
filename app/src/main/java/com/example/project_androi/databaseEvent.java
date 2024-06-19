package phcom.edu.lichvannien;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import com.example.project_androi.model.ItemDay;
import com.example.project_androi.model.event;

public class databaseEvent extends SQLiteOpenHelper {


    public databaseEvent(@Nullable Context context) {
        super(context, "LichVanNien", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql1 = "CREATE TABLE itemDay(day INTEGER, month INTEGER, year INTEGER, lunarDay INTEGER, lunarMonth INTEGER, lunarYear INTEGER, weekday INTEGER, isEvent INTEGER)";
        String sql = "CREATE TABLE event(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, day INTEGER, month INTEGER, year INTEGER, time TEXT, note TEXT)";
        db.execSQL(sql);
        db.execSQL(sql1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS event";
        String sql1 = "DROP TABLE IF EXISTS itemDay";
        db.execSQL(sql);
        db.execSQL(sql1);
        onCreate(db);
    }

    //ínert
    public void insertEv(event ev, onInsertSuccess onInsertSuccess) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO event VALUES(null,?,?,?,?,?,?)";
        db.execSQL(sql, new String[]{ev.getTitle(), String.valueOf(ev.getDay()), String.valueOf(ev.getMonth()), String.valueOf(ev.getYear()), ev.getTime(), ev.getNote()});
        onInsertSuccess.onSuccess();
    }

    //update
    public void updateEv(event ev, onUpdateSuccess onUpdateSuccess) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "UPDATE event SET title = ?, day = ?, month = ?, year = ?, time = ?, note = ? WHERE id = ?";
        db.execSQL(sql, new String[]{ev.getTitle(), String.valueOf(ev.getDay()), String.valueOf(ev.getMonth()), String.valueOf(ev.getYear()), ev.getTime(), ev.getNote(), String.valueOf(ev.getId())});
        onUpdateSuccess.onSuccess();
    }

    //delete
    public void deleteEv(event ev, onDeleteSuccess onDeleteSuccess) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM event WHERE id = ?";
        db.execSQL(sql, new String[]{String.valueOf(ev.getId())});
        onDeleteSuccess.onSuccess();
    }

    //getall
    public void getAllEv(onGetAllSuccess onGetAllSuccess) {
        List<event> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM event";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            event ev = new event();
            ev.setId(cursor.getString(0));
            ev.setTitle(cursor.getString(1));
            ev.setDay(cursor.getInt(2));
            ev.setMonth(cursor.getInt(3));
            ev.setYear(cursor.getInt(4));
            ev.setTime(cursor.getString(5));
            ev.setNote(cursor.getString(6));
            list.add(ev);
        }
        onGetAllSuccess.onSuccess(list);
    }

    //insert itemDay
    public void insertItemDay(ItemDay itemDay, onInsertSuccess onInsertSuccess) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO itemDay VALUES(?,?,?,?,?,?,?,?)";
        db.execSQL(sql, new String[]{String.valueOf(itemDay.getDay()), String.valueOf(itemDay.getMonth()), String.valueOf(itemDay.getYear()), String.valueOf(itemDay.getLunarDay()), String.valueOf(itemDay.getLunarMonth()), String.valueOf(itemDay.getLunarYear()), String.valueOf(itemDay.getWeekday()), String.valueOf(itemDay.isEvent())});
        onInsertSuccess.onSuccess();
    }
    //get itemDay
    public void getItemDay(int month ,onGetItemAllSuccess onGetAllSuccess) {
        List<ItemDay> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM itemDay WHERE month = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(month)});
        while (cursor.moveToNext()) {
            ItemDay itemDay = new ItemDay();
            itemDay.setDay(cursor.getInt(0));
            itemDay.setMonth(cursor.getInt(1));
            itemDay.setYear(cursor.getInt(2));
            itemDay.setLunarDay(cursor.getInt(3));
            itemDay.setLunarMonth(cursor.getInt(4));
            itemDay.setLunarYear(cursor.getInt(5));
            itemDay.setWeekday(cursor.getInt(6));
            itemDay.setEvent(cursor.getInt(7) == 1);
            list.add(itemDay);
        }
        onGetAllSuccess.onSuccess(list);
    }

    public interface onInsertSuccess {
        void onSuccess();
    }

    public interface onUpdateSuccess {
        void onSuccess();
    }

    public interface onDeleteSuccess {
        void onSuccess();
    }

    public interface onGetAllSuccess {
        void onSuccess(List<event> list);
    }

    public interface onGetItemAllSuccess {
        void onSuccess(List<ItemDay> list);
    }

}
