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

    public NoteWidget(){
        this.CLASS_NAME = getClass().getName();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            Log.d(CLASS_NAME, "onUpdate()");
            int appWidgetId = appWidgetIds[i];

            //Create new intent to be called on widget click
            Intent intent = new Intent(context, NoteActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            //Get the layout for the widget and attach an onclick listener
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.note_widget);
            views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

            //Tell AppWidgetManager to perform update on the current widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        SQLiteHelper helper = SQLiteHelper.getInstance(context);
        helper.create();
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.note_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}

