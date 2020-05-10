package com.example.dosar;

import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.jar.Attributes;

public class Search extends AppCompatActivity implements View.OnClickListener {
    String[] id;
    int i;
    String[] name;
    String[] college;
    String[] mobile;
    String[] date;
    String[] event;
    MyDBHandler dbHandler;
    EditText NameProvided;
    boolean firstSearch;
    TableLayout t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firstSearch=false;
        t=(TableLayout)findViewById(R.id.tableInvoice);
        i=0;
        dbHandler = Register.getRegister().dbHandler;
        NameProvided = (EditText) findViewById(R.id.id);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        NameProvided.setText("");
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void searchButtonClicked(View v) {
        closeKeyboard();
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        String query = "SELECT * FROM "+ dbHandler.TABLE_PRODUCTS+" WHERE "+ dbHandler.COLUMN_PRODUCTNAME + " LIKE "+"'%"+ NameProvided.getText().toString()+"%'" + ";";// why not leave out the WHERE  clause?
        if(!firstSearch)
         {addHeaders();firstSearch=true;}
        else
        {
            cleanTable(t);
        }
        i=0;
        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //int l=recordSet.getCount();
        college = new String[300];
        mobile = new String[300];
        id = new String[300];
        event = new String[300];
        date = new String[300];
        name = new String[300];
        //Move to the first row in your results
        if(recordSet.moveToFirst())
        {//Position after the last row means the end of the results
            while (!recordSet.isAfterLast()){
            // null could happen if we used our empty constructor
            String m = recordSet.getString(recordSet.getColumnIndex(dbHandler.COLUMN_MOBILE));
            String c = recordSet.getString(recordSet.getColumnIndex(dbHandler.COLUMN_COLLEGE));
            String e = recordSet.getString(recordSet.getColumnIndex(dbHandler.COLUMN_EVENT));
            String not = "NOT AVAILABLE";
            String n = recordSet.getString(recordSet.getColumnIndex(dbHandler.COLUMN_PRODUCTNAME));
            String da = recordSet.getString(recordSet.getColumnIndex(dbHandler.COLUMN_DATE));
            String idc = recordSet.getString(recordSet.getColumnIndex(dbHandler.COLUMN_ID));
            mobile[i] = (m == null) ? not : m;
            college[i] = (c == null) ? not : c;
            event[i] = (e == null) ? not : e;
            date[i] = (da == null) ? not : da;
            name[i] = (n == null) ? not : n;
            id[i] = (idc == null) ? not : idc;
            // name[i]=recordSet.getString(recordSet.getColumnIndex(COLUMN_PRODUCTNAME));
            // System.out.println(id[i]);
            recordSet.moveToNext();
            i = i + 1;
        }
        addData();
        Toast.makeText(this,"search successfull",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,"NO reults",Toast.LENGTH_LONG).show();
        db.close();
        NameProvided.setText("");

    }
    private void cleanTable(TableLayout table) {

        int childCount = table.getChildCount();

        // Remove all rows except the first one
        if (childCount > 1) {
            table.removeViews(1, childCount - 1);
        }
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
    private TableRow.LayoutParams getLayoutParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(2, 0, 0, 2);
        return params;
    }

    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
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
            tr.addView(getTextView(j + 2 * numCompanies, college[j], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAlternate)));
            tr.addView(getTextView(j + 3 * numCompanies, mobile[j], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorTable)));
            tr.addView(getTextView(j + 4 * numCompanies, event[j], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAlternate)));
            tr.addView(getTextView(j + 5 * numCompanies, date[j], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorTable)));
            tl.addView(tr, getTblLayoutParams());
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        closeKeyboard();
        TextView tv = findViewById(id);
        if (null != tv) {
            Log.i("onClick", "Clicked on row : " + id);
            Toast.makeText(this, "Clicked on row : " + id + ", Text :" + tv.getText(), Toast.LENGTH_SHORT).show();
        }
    }

    public void closeKeyboard()
    {
        View v=this.getCurrentFocus();
        if(v!=null)
        {
            InputMethodManager im=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(v.getWindowToken(),0);

        }
    }
}
