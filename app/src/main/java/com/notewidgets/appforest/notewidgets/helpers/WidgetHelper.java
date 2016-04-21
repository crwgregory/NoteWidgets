package com.notewidgets.appforest.notewidgets.helpers;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.notewidgets.appforest.notewidgets.R;
import com.notewidgets.appforest.notewidgets.activities.NoteActivity;

/**
 * Created by Greg Christopherson on 11/6/2015.
 */
public class WidgetHelper {

    private static String CLASS_NAME;

    public WidgetHelper() {
        CLASS_NAME = getClass().getName();
    }

    public static void updateWidget(Context context, Integer widgetId, String title, String body) {
        Log.d(CLASS_NAME, "updateWidget()");
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.note_widget);
        views.setTextViewText(R.id.widget_title_text, title);
        views.setTextViewText(R.id.widget_body_text, body);
        setPendingIntent(context, views, widgetId);
        appWidgetManager.updateAppWidget(widgetId, views);
    }

    private static void setPendingIntent(Context context, RemoteViews views, Integer widgetId) {
        Log.d(CLASS_NAME, "setPendingIntent()");
        //Refer to App Widget Documentation "Using the AppWidgetProvider Class"for more information
        //https://developer.android.com/guide/topics/appwidgets/index.html#AppWidgetProvider
        Intent intent = new Intent(context.getApplicationContext(), NoteActivity.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), widgetId, intent, 0);
        views.setOnClickPendingIntent(R.id.note_widget, pendingIntent);
    }
}
