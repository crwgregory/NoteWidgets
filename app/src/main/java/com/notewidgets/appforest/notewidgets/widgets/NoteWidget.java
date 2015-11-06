package com.notewidgets.appforest.notewidgets.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.notewidgets.appforest.notewidgets.R;
import com.notewidgets.appforest.notewidgets.activities.NoteActivity;
import com.notewidgets.appforest.notewidgets.helpers.SQLiteHelper;
import com.notewidgets.appforest.notewidgets.helpers.WidgetHelper;
import com.notewidgets.appforest.notewidgets.model.Note;

/**
 * Implementation of App Widget functionality.
 */
public class NoteWidget extends AppWidgetProvider {

    private static String CLASS_NAME;
    private Note note;
    private NoteActivity noteActivity = new NoteActivity();

    public NoteWidget(){
        this.CLASS_NAME = getClass().getName();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them


        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            Log.d(CLASS_NAME, "onUpdate() widgetId: " + appWidgetIds[i]);
            if(note == null){
                onEnabled(context);
            }
            note = note.getNote(appWidgetIds[i]);
            if(note != null){
                WidgetHelper.updateWidget(context, appWidgetIds[i], note.getNote_title(), note.getNote_body());
            }
        }
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        Log.d(CLASS_NAME, "onEnabled()");
        SQLiteHelper helper = SQLiteHelper.getInstance(context);
        helper.create();
        note = new Note(helper);
    }

    @Override
    public void onDisabled(Context context) {
        Log.d(CLASS_NAME, "onDisabled()");
        // Enter relevant functionality for when the last widget is disabled
    }
}

