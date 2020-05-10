package com.example.dosar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyEventHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "event.db";
    public static final String TABLE_EVENT = "count";
    public static final String COLUMN_EVENT="event";
    public static final String COLUMN_EVENT_CONTEXT="context";
    public static final String COLUMN_ID = "_id";;
    //We need to pass database information along to superclass
    public MyEventHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_EVENT + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +COLUMN_EVENT+" TEXT NOT NULL ,"+COLUMN_EVENT_CONTEXT+" TEXT"+");";
        db.execSQL(query);
    }
    //Lesson 51
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT);
        onCreate(db);
    }

    //Add a new row to the database
    public void addProduct(String event,String context){
        ContentValues values = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();
        values.put(COLUMN_EVENT,event);
        values.put(COLUMN_EVENT_CONTEXT,context);
        db.insert(TABLE_EVENT, null, values);
        db.close();
    }


    //Delete a product from the database
    public void deleteProduct(String event,String context){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_EVENT + " WHERE " + COLUMN_EVENT + "=\"" + event + "\""+" AND "+COLUMN_EVENT_CONTEXT+"=\"" + context + "\""+";");
    }

    // this is goint in record_TextView in the Main activity.
    public List< Event> databaseToArray(){
        String dbString = "0";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_EVENT + " WHERE 1";// why not leave out the WHERE  clause?
        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();
        List<Event> event=new ArrayList<>();
        String event_name="";
        String event_context="";
        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex(MyDBHandler.COLUMN_EVENT)) != null) {
                event_name=recordSet.getString(recordSet.getColumnIndex(COLUMN_EVENT));
                event_context=recordSet.getString(recordSet.getColumnIndex(COLUMN_EVENT_CONTEXT));
            }
            recordSet.moveToNext();
            event.add(new Event(event_name,event_context));
        }
        db.close();
        return event;
    }
}
