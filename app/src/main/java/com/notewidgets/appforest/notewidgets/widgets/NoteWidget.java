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

/**
 * Implementation of App Widget functionality.
 */
public class NoteWidget extends AppWidgetProvider {

    private static String CLASS_NAME;
    private static final String PK = "primary_key_identifier";

    public NoteWidget(){
        this.CLASS_NAME = getClass().getName();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            Log.d(CLASS_NAME, "onUpdate()");
            Log.d(CLASS_NAME, "size of appWidgetIds: " + N);
            Log.d(CLASS_NAME, "app id: " + i);
            int appWidgetId = appWidgetIds[i];



            //Create new intent to be called on widget click
            Intent intent = new Intent(context, NoteActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            //Get the layout for the widget and attach an onclick listener
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.note_widget);
            views.setOnClickPendingIntent(R.id.widget_body_text, pendingIntent);

            //Tell AppWidgetManager to perform update on the current widget
            appWidgetManager.updateAppWidget(appWidgetId, views);

        }
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        Log.d(CLASS_NAME, "onEnabled()");
        SQLiteHelper helper = SQLiteHelper.getInstance(context);
        helper.create();
    }

    @Override
    public void onDisabled(Context context) {
        Log.d(CLASS_NAME, "onDisabled()");
        // Enter relevant functionality for when the last widget is disabled
    }

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Log.d(CLASS_NAME, "updateAppWidget()");

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.note_widget);
        views.setTextViewText(R.id.widget_body_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}

