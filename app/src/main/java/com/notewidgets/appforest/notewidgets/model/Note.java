package com.notewidgets.appforest.notewidgets.model;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Greg Christopherson on 10/1/2015.
 */
public class Note {

    private long _id;
    private long date_created;
    private String note;



    private static String CLASS_NAME;

    public Note(){
        this.CLASS_NAME = getClass().getName();
    }

    public static void createTable(SQLiteDatabase database){
        Log.d(CLASS_NAME, "createTable()");
        String sql = "CREATE TABLE IF NOT EXISTS notes "
                +"(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +"date_created INTEGER NOT NULL, "
                +"note TEXT);";
        database.execSQL(sql);
    }

    public static void dropTable(SQLiteDatabase database){
        Log.d(CLASS_NAME, "dropTable()");
        String sql = "DROP TABLE IF EXISTS notes;";
        database.execSQL(sql);
    }

    @Override
    public String toString(){
        return note;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public long getDate_created() {
        return date_created;
    }

    public void setDate_created(long date_created) {
        this.date_created = date_created;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
