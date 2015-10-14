package com.notewidgets.appforest.notewidgets.model;

import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.notewidgets.appforest.notewidgets.helpers.SQLiteHelper;

/**
 * Created by Greg Christopherson on 10/1/2015.
 */
public class Note {

    private Long _id = null;
    private Long date_created;
    private Integer appWidgetId;
    private String note_title;
    private String note_body;
    private SQLiteHelper helper;

    private static String CLASS_NAME;

    public Note(SQLiteHelper helper){
        this.CLASS_NAME = getClass().getName();
        this.helper = helper;
    }

    public static void createTable(SQLiteDatabase database){
        Log.d(CLASS_NAME, "createTable()");
        String sql = "CREATE TABLE IF NOT EXISTS notes "
                +"(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +"app_widget_id INTEGER NOT NULL, "
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

    public void saveNote(Integer appWidgetId, String title, String body){

        SQLiteDatabase database = helper.getWritableDatabase();
        String sql;

        this.note_title = title;
        this.note_body = body;

        if(getNote(appWidgetId) == null){
            this.date_created = System.currentTimeMillis();
            Log.d(CLASS_NAME, "Creating new row");
            sql = "INSERT INTO notes (app_widget_id, date_created, note_title, note_body) VALUES ("
                    +this.appWidgetId + ", "
                    +this.date_created + ", '"
                    +this.note_title + "', '"
                    +this.note_body + "');";
        } else {
            Log.d(CLASS_NAME, "updating row");
            sql = "UPDATE notes SET "
                    +"note_title = '"+this.note_title + "', "
                    +"note_body = '"+this.note_body + "' "
                    +"WHERE app_widget_id = " + this.appWidgetId
                    +";";
        }
        Log.d(CLASS_NAME, "Date created: " + this.date_created.toString());

        database.execSQL(sql);
    }

    public Note getNote(Integer appWidgetId){
        this.appWidgetId = appWidgetId;
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM notes WHERE app_widget_id = " + appWidgetId + ";", null);
        cursor.moveToFirst();

        try {
            this.date_created = cursor.getLong(cursor.getColumnIndex("date_created"));
            this.note_title = cursor.getString(cursor.getColumnIndex("note_title"));
            this.note_body = cursor.getString(cursor.getColumnIndex("note_body"));
        } catch (CursorIndexOutOfBoundsException x){
            Log.d(CLASS_NAME, "Tried to find a note that wasn't there.");
            cursor.close();
            return null;
        }
        cursor.close();
        return this;
    }

    @Override
    public String toString(){
        return note_body;
    }

    public Long get_id() {
        return _id;
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

    public Integer getAppWidgetId() {
        return appWidgetId;
    }

    public void setAppWidgetId(Integer appWidgetId) {
        this.appWidgetId = appWidgetId;
    }

}
