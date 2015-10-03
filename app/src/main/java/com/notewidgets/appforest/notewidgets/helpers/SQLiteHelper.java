package com.notewidgets.appforest.notewidgets.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.notewidgets.appforest.notewidgets.model.Note;

/**
 * Created by Greg Christopherson on 10/1/2015.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private static SQLiteHelper sqLiteHelper = null;
    private static final String DATABASE_NAME = "NoteForest.db";
    private static final int DATABASE_VERSION= 3;
    private static String CLASS_NAME;

    public static SQLiteHelper getInstance(Context context){
        if(sqLiteHelper == null){
            sqLiteHelper = new SQLiteHelper(context);
        }
        return sqLiteHelper;
    }

    protected SQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.CLASS_NAME = getClass().getName();
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        Log.d(CLASS_NAME, "onCreate()");
        Note.createTable(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        Log.d(CLASS_NAME, "onUpgrade()");
        Note.dropTable(database);
        onCreate(database);
    }

    @Override
    public void onConfigure(SQLiteDatabase database){
        database.setForeignKeyConstraintsEnabled(true);
    }

    public void create(){
        Log.d(CLASS_NAME, "create()");
        getWritableDatabase();
    }


    //Use method getWrittableDatabase()
//    public SQLiteDatabase getDatabase(){
//        return this.sqLiteDatabase;
//    }

}
