package com.notewidgets.appforest.notewidgets.activities;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.notewidgets.appforest.notewidgets.BuildConfig;
import com.notewidgets.appforest.notewidgets.R;
import com.notewidgets.appforest.notewidgets.helpers.SQLiteHelper;
import com.notewidgets.appforest.notewidgets.helpers.WidgetHelper;
import com.notewidgets.appforest.notewidgets.model.Note;

public class NoteActivity extends AppCompatActivity {

    private static String CLASS_NAME;
    private SQLiteHelper sqLiteHelper;
    private Integer mAppWidgetId;
    private String noteTitle;
    private String noteBody;

    public NoteActivity(){
        this.CLASS_NAME = getClass().getName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(CLASS_NAME, "onCreate()");
        super.onCreate(savedInstanceState);
        this.setResult(RESULT_CANCELED);
        setContentView(R.layout.activity_note);
        this.sqLiteHelper = SQLiteHelper.getInstance(getApplicationContext());
        Note note = new Note(sqLiteHelper);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null){
            this.mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

            if(this.mAppWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                Log.d(CLASS_NAME, "Valid AppWidgetID: " + this.mAppWidgetId);
                note = note.getNote(this.mAppWidgetId);
                if(note != null){
                    EditText title = (EditText) findViewById(R.id.main_edit_title_text);
                    EditText body = (EditText) findViewById(R.id.main_edit_text);
                    title.setText(note.getNote_title(), TextView.BufferType.EDITABLE);
                    body.setText(note.getNote_body(), TextView.BufferType.EDITABLE);
                }
            } else {
                //invalid app widget id, exit.
                finish();
            }
        }
    }

    public void saveNote(View view){
        Log.d(CLASS_NAME, "saveNote()");
        Log.d(CLASS_NAME, "appWidgetId: " + mAppWidgetId);
        Note note = new Note(sqLiteHelper);
        noteTitle = ((EditText) findViewById(R.id.main_edit_title_text)).getText().toString();
        noteBody = ((EditText) findViewById(R.id.main_edit_text)).getText().toString();
        Log.d(CLASS_NAME, noteBody);
        note.saveNote(mAppWidgetId, noteTitle, noteBody);
        updateWidget(mAppWidgetId, noteTitle, noteBody);
        returnResult();
        finish();
    }

    private void returnResult() {
        Log.d(CLASS_NAME, "returnResult()");
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
    }

    public void updateWidget(Integer widgetId, String title, String body) {
        WidgetHelper.updateWidget(this, widgetId, title, body);
    }

    private void setPendingIntent(RemoteViews views){
        Log.d(CLASS_NAME, "setPendingIntent()");
        Intent intent = new Intent(this.getApplicationContext(), this.getClass());
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, this.mAppWidgetId);
        PendingIntent pendingIntent = PendingIntent.getActivity(this.getApplicationContext(), this.mAppWidgetId, intent, 0);
        views.setOnClickPendingIntent(R.id.note_widget, pendingIntent);
    }

    @Override
    public void onPause(){
        Log.d(CLASS_NAME, "onPause()");
        super.onPause();
    }

    @Override
    public void onStop(){
        Log.d(CLASS_NAME, "onStop()");
        super.onStop();
    }

    @Override
     public void onDestroy(){
        Log.d(CLASS_NAME, "onDestroy()");
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void enableStrictMode() {
        if(BuildConfig.DEBUG){
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            try {
                StrictMode.setVmPolicy(new StrictMode.VmPolicy
                        .Builder()
                        .detectLeakedClosableObjects()
                        .detectLeakedSqlLiteObjects()
                        .setClassInstanceLimit(Class.forName("com.notewidgets.appforest.notewidgets.activities.NoteActivity"), 100)
                        .penaltyLog()
                        .penaltyDeath()
                        .build());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
