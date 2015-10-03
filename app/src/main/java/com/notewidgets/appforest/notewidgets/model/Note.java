package com.notewidgets.appforest.notewidgets.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.notewidgets.appforest.notewidgets.helpers.SQLiteHelper;

/**
 * Created by Greg Christopherson on 10/1/2015.
 */
public class Note {

    private Long _id = null;
    private Long date_created;
    private String note_title;
    private String note_body;

    private static String CLASS_NAME;

    public Note(){
        this.CLASS_NAME = getClass().getName();
    }

    public static void createTable(SQLiteDatabase database){
        Log.d(CLASS_NAME, "createTable()");
        String sql = "CREATE TABLE IF NOT EXISTS notes "
                +"(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +"date_created INTEGER NOT NULL, "
                +"note_title TEXT, "
                +"note_body TEXT);";
        database.execSQL(sql);
    }

    public static void dropTable(SQLiteDatabase database){
        Log.d(CLASS_NAME, "dropTable()");
        String sql = "DROP TABLE IF EXISTS notes;";
        database.execSQL(sql);
    }

    public void saveNote(SQLiteHelper helper, String title, String body){

        SQLiteDatabase database = helper.getWritableDatabase();

        this.date_created = System.currentTimeMillis();
        this.note_title = "'"+title+"'";
        this.note_body = "'"+body+"'";

        String sql = "INSERT INTO notes (date_created, note_title, note_body) VALUES ("
                +this.date_created + ", "
                +this.note_title + ", "
                +this.note_body + ");";
        database.execSQL(sql);

        //set _id to generated Primary Key
        Cursor cursor = database.rawQuery("SELECT * FROM notes;", null);
        cursor.moveToLast();
        _id = cursor.getLong(cursor.getColumnIndex("_id"));
        cursor.close();
    }

    @Override
    public String toString(){
        return note_body;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public Long getDate_created() {
        return date_created;
    }

    public void setDate_created(long date_created) {
        this.date_created = date_created;
    }

    public String getNote_body() {
        return note_body;
    }

    public void setNote_body(String note_body) {
        this.note_body = note_body;
    }

    public String getNote_title() {
        return note_title;
    }

    public void setNote_title(String note_title) {
        this.note_title = note_title;
    }

}
