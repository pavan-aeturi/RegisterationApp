package com.example.dosar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class History extends AppCompatActivity implements View.OnClickListener{
    String[] id;
    int i;
    String[] name;
    String[] college;
    String[] mobile;
    String[] date;
    String[] event;
    MyDBHandler d;
    MyCountHandler c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(History.this,MainActivity.class);
                startActivity(i);
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((ProgressBar)findViewById(R.id.progressBar)).setVisibility(View.VISIBLE);

            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                addHeaders();
                d=Register.getRegister().dbHandler;
                c=Register.getRegister().countHandler;
                SQLiteDatabase db = d.getWritableDatabase();
                String query = "SELECT * FROM " + Register.getRegister().dbHandler.TABLE_PRODUCTS + " ORDER BY "+d.COLUMN_ID+" DESC LIMIT 100;";// why not leave out the WHERE  clause?
                //Cursor points to a location in your results
                Cursor recordSet = db.rawQuery(query, null);
                int l=Integer.parseInt(c.databaseToString())+2;
                //int l=recordSet.getCount();
                college=new String[l];mobile=new String[l];id=new String[l];event=new String[l];date=new String[l];
                name=new String[l];
                //Move to the first row in your results
                recordSet.moveToFirst();
                i=0;
                //Position after the last row means the end of the results
                while (!recordSet.isAfterLast()) {
                    // null could happen if we used our empty constructor
                    String m=recordSet.getString(recordSet.getColumnIndex(d.COLUMN_MOBILE));
                    String c=recordSet.getString(recordSet.getColumnIndex(d.COLUMN_COLLEGE));
                    String e=recordSet.getString(recordSet.getColumnIndex(d.COLUMN_EVENT));
                    String not="NOT AVAILABLE";
                    String n=recordSet.getString(recordSet.getColumnIndex(d.COLUMN_PRODUCTNAME));
                    String da=recordSet.getString(recordSet.getColumnIndex(d.COLUMN_DATE));
                    String idc=recordSet.getString(recordSet.getColumnIndex(d.COLUMN_ID));
                    mobile[i]=(m==null)?not:m;
                    college[i]=(c==null)?not:c;
                    event[i]=(e==null)?not:e;
                    date[i]=(da==null)?not:da;
                    name[i]=(n==null)?not:n;
                    id[i]=(idc==null)?not:idc;
                    // name[i]=recordSet.getString(recordSet.getColumnIndex(COLUMN_PRODUCTNAME));
                    // System.out.println(id[i]);
                    recordSet.moveToNext();
                    i=i+1;
                }
                addData();
                db.close();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((ProgressBar)findViewById(R.id.progressBar)).setVisibility(View.GONE);
                    }
                });
            }
        }).start();
//        TextView t=(TextView) findViewById(R.id.textView);
//        t.setText(Register.getRegister().dbHandler.databaseToString());
}
    private TextView getTextView(int id, String title, int color, int typeface, int bgColor) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgColor);
        tv.setLayoutParams(getLayoutParams());
        tv.setOnClickListener(this);
        return tv;
    }

    @NonNull
    private LayoutParams getLayoutParams() {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        params.setMargins(2, 0, 0, 2);
        return params;
    }

    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
    }

    /**
     * This function add the headers to the table
     **/
    public void addHeaders() {
        TableLayout tl = findViewById(R.id.tableInvoice);
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(getLayoutParams());
        tr.addView(getTextView(0, "ID", Color.WHITE, Typeface.BOLD, Color.BLUE));
        tr.addView(getTextView(0, "NAME", Color.WHITE, Typeface.BOLD, Color.BLUE));
        tr.addView(getTextView(0, "COLLEGE", Color.WHITE, Typeface.BOLD, Color.BLUE));
        tr.addView(getTextView(0, "MOBILE", Color.WHITE, Typeface.BOLD, Color.BLUE));
        tr.addView(getTextView(0, "EVENT", Color.WHITE, Typeface.BOLD, Color.BLUE));
        tr.addView(getTextView(0, "DATE", Color.WHITE, Typeface.BOLD, Color.BLUE));
        tl.addView(tr, getTblLayoutParams());
    }

    /**
     * This function add the data to the table
     **/
    public void addData() {
        int numCompanies = i;
        TableLayout tl = findViewById(R.id.tableInvoice);
        for (int j = 0; j < numCompanies; j++) {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(getLayoutParams());
            tr.addView(getTextView(j + 1, id[j], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAlternate)));
            tr.addView(getTextView(j + numCompanies,name[j], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorTable)));
            tr.addView(getTextView(j + 2*numCompanies, college[j], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAlternate)));
            tr.addView(getTextView(j + 3*numCompanies, mobile[j], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorTable)));
            tr.addView(getTextView(j + 4*numCompanies,event[j], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAlternate)));
            tr.addView(getTextView(j + 5*numCompanies, date[j], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorTable)));
            tl.addView(tr, getTblLayoutParams());
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        TextView tv = findViewById(id);
        if (null != tv) {
            Log.i("onClick", "Clicked on row : " + id);
            Toast.makeText(this, "Clicked on row : " + id + ", Text :" + tv.getText(), Toast.LENGTH_SHORT).show();
        }
    }
}



