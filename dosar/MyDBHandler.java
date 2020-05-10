package com.example.dosar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "productDB.db";
    public static final String TABLE_PRODUCTS = "products";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRODUCTNAME = "productname";
    public static final String COLUMN_COLLEGE="college";
    public static final String COLUMN_MOBILE="mobile";
    public static final String COLUMN_DATE="date";
    public static final String COLUMN_EVENT="event";
    //We need to pass database information along to superclass
    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_PRODUCTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PRODUCTNAME + " TEXT NOT NULL," +COLUMN_COLLEGE+" TEXT,"+COLUMN_MOBILE+" TEXT,"+COLUMN_DATE+" TEXT,"+COLUMN_EVENT+" TEXT "+
                ");";
        db.execSQL(query);
    }
    //Lesson 51
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    //Add a new row to the database
    public void addProduct(Products product){
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTNAME, product.get_productname()!=""?product.get_productname():"NOT AVAILABLE");
        values.put(COLUMN_COLLEGE,product.get_college()!=""?product.get_college():"NOT AVAILABLE");
        values.put(COLUMN_MOBILE,product.get_mobile()!=""?product.get_mobile():"NOT AVAILABLE");
        values.put(COLUMN_DATE,product.get_date()!=""?product.get_date():"NOT AVAILABLE");
        values.put(COLUMN_EVENT,product.get_event()!=""?product.get_event():"NOT AVAILABLE");
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }


    //Delete a product from the database
    public void deleteProduct(String productName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_ID + "=\"" + productName + "\";");

    }

    // this is goint in record_TextView in the Main activity.
    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE 1";// why not leave out the WHERE  clause?
        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex(MyDBHandler.COLUMN_PRODUCTNAME)) != null) {
                dbString += recordSet.getString(recordSet.getColumnIndex(COLUMN_MOBILE));
                dbString+=recordSet.getString(recordSet.getColumnIndex(COLUMN_COLLEGE));
                dbString+=recordSet.getString(recordSet.getColumnIndex(COLUMN_EVENT));
                dbString+=recordSet.getString(recordSet.getColumnIndex(COLUMN_DATE));
                dbString+=recordSet.getString(recordSet.getColumnIndex(COLUMN_ID));
                dbString += "\n";
            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;
    }
}
