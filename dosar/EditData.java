package com.example.dosar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

public class EditData extends AppCompatActivity {
    LinearLayout edit;
    EditText name;
    EditText id;
    String id_edit;
    EditText college;
    EditText phone;
    Spinner s;
    MyDBHandler db;
    List<Event> events;
    MyEventHandler eventHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        id=(EditText) findViewById(R.id.id_edit);
        name=(EditText)findViewById(R.id.edit_name);
        college=(EditText)findViewById(R.id.edit_college);
        phone=(EditText)findViewById(R.id.edit_mobile);
        s=(Spinner)findViewById(R.id.edit_spinner);
        events=new ArrayList<>();
        ArrayAdapter<Event> adapter=new ArrayAdapter<Event>(this,android.R.layout.simple_spinner_item,events);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        edit=(LinearLayout)findViewById(R.id.edit_layout);
        db=Register.getRegister().dbHandler;
        eventHandler=Register.getRegister().eventHandler;
        events= eventHandler.databaseToArray();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            name.setText("");
            college.setText("");
            phone.setText("");
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),((Event)s.getSelectedItem()).getEvent()+" :"+((Event)s.getSelectedItem()).getEventContext(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void saveButtonClicked(View v){
        closeKeyboard();
        String save_name=name.getText().toString();
        String save_college=college.getText().toString();
        String save_mobile=phone.getText().toString();
        String save_event=s.getSelectedItem().toString();
        SQLiteDatabase DB=db.getWritableDatabase();
        String query="UPDATE "+db.TABLE_PRODUCTS+" SET "+db.COLUMN_PRODUCTNAME +" = '"+save_name+"' ,"+
                                                         db.COLUMN_COLLEGE+" = '"+save_college+"' ,"+
                                                         db.COLUMN_MOBILE+" = '"+save_mobile+"' ,"+
                                                         db.COLUMN_EVENT+" = '"+save_event+"' "+"WHERE "+
                db.COLUMN_ID+" = '"+id_edit+"';";
        DB.execSQL(query);
        Toast.makeText(this,"updated succesfully",Toast.LENGTH_SHORT).show();
        edit.setVisibility(View.INVISIBLE);
        //((Button)findViewById(R.id.edit_button)).setVisibility(View.VISIBLE);
    }
    public void editButtonClicked(View v){
        closeKeyboard();
        //((Button)findViewById(R.id.edit_button)).setVisibility(View.INVISIBLE);
        SQLiteDatabase DB=db.getWritableDatabase();
        String ID=id.getText().toString();
        if(!ID.equals("")) {
            Integer j = Integer.parseInt(ID);
            String query="SELECT * FROM "+db.TABLE_PRODUCTS+" WHERE "+ db.COLUMN_ID +" =\""+ j +"\"";
            Cursor recordSet=DB.rawQuery(query,null);
            recordSet.moveToFirst();
            if(!recordSet.isAfterLast())
            {
                String data=recordSet.getString(recordSet.getColumnIndex(db.COLUMN_PRODUCTNAME));
                name.setText(data);
                data=recordSet.getString(recordSet.getColumnIndex(db.COLUMN_COLLEGE));
                if(data!=null)
                    college.setText(data);
                data=recordSet.getString(recordSet.getColumnIndex(db.COLUMN_MOBILE));
                phone.setText(data);
                data=recordSet.getString(recordSet.getColumnIndex(db.COLUMN_EVENT));
                int k=0;
                for(int i=0;i<events.size();i++) {
                    if ((events.get(i).getEvent().toUpperCase()).equals(data)) {
                        k=i;
                        break;
                    }
                    if(i==events.size()-1)
                        events.add(new Event(data,"THIS WAS DELETED"));
                }
                ArrayAdapter<Event> adapter=new ArrayAdapter<Event>(this,android.R.layout.simple_spinner_item,events);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                s.setAdapter(adapter);
                s.setSelection(k);
                edit.setVisibility(View.VISIBLE);
                id_edit=ID;
            }
            else
            {
                Toast.makeText(this,"ID :"+j+" Not Found",Toast.LENGTH_SHORT).show();
                edit.setVisibility(View.INVISIBLE);
            }

        }
        else
        {
            Toast.makeText(this,"Enter ID",Toast.LENGTH_SHORT).show();
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
