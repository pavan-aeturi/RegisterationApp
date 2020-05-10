package com.example.dosar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyCountHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "count.db";
    public static final String TABLE_PRODUCTS_COUNT = "count";
    public static final String COLUMN_ID = "_id";;
    //We need to pass database information along to superclass
    public MyCountHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_PRODUCTS_COUNT + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" +");";
        db.execSQL(query);
    }
    //Lesson 51
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS_COUNT);
        onCreate(db);
    }

    //Add a new row to the database
    public void addProduct(int i){
        ContentValues values = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();
        values.put(COLUMN_ID,i);
        deleteProduct();
        db.insert(TABLE_PRODUCTS_COUNT, null, values);
        db.close();
    }


    //Delete a product from the database
    public void deleteProduct(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRODUCTS_COUNT );

    }

    // this is goint in record_TextView in the Main activity.
    public String databaseToString(){
        String dbString = "1";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS_COUNT + " WHERE 1";// why not leave out the WHERE  clause?
        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();
        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex(MyDBHandler.COLUMN_ID)) != null) {
                dbString=recordSet.getString(recordSet.getColumnIndex(COLUMN_ID));
            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;
    }
}
