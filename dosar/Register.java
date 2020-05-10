package com.example.dosar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity {
    EditText name;
    static Register register;
    EditText college;
    EditText mobile;
    TextView v;
    MyDBHandler dbHandler;
    MyCountHandler countHandler;
    MyEventHandler eventHandler;
    static Spinner s;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        register=this;
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        name=(EditText)findViewById(R.id.Name);
        college=(EditText)findViewById(R.id.College);
        mobile=(EditText)findViewById(R.id.Mobile);
        dbHandler=new MyDBHandler(this,null,null,1);
        countHandler=new MyCountHandler(this,null,null,1);
        eventHandler=new MyEventHandler(this,null,null,1);
        count=Integer.parseInt(countHandler.databaseToString());
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
            name.setText("");
            college.setText("");
            mobile.setText("");
            }
        });
        v=(TextView)findViewById(R.id.ID);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        s=(Spinner)findViewById(R.id.spinner);
        List<Event> events=new ArrayList<>();
        events= eventHandler.databaseToArray();
        ArrayAdapter<Event> adapter=new ArrayAdapter<Event>(this,android.R.layout.simple_spinner_item,events);
          adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       s.setAdapter(adapter);
       try {
           v.setText("ID: QK20" + (count) + s.getSelectedItem().toString() + "G");
       }
       catch(Exception e){
           v.setText("ID: QK20" + (count) +"NOEVENTS" + "G");
       }
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try{
                    hello();
                }
                catch (Exception e)
                {

                    v.setText("ID: QK20" + (count) +"NOEVENTS" + "G");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void hello()
    {

        try {
            v.setText("ID: QK20" + (count) + s.getSelectedItem().toString() + "G");
            Toast.makeText(this,((Event)s.getSelectedItem()).getEvent()+" : "+((Event)s.getSelectedItem()).getEventContext(),Toast.LENGTH_SHORT).show();
        }
        catch(Exception e){
            v.setText("ID: QK20" + (count) +"NOEVENTS" + "G");
        }
    }
    public static Register getRegister(){
        return register;
    }
    public void addButtonClicked(View vi){
        closeKeyboard();
        if(!name.getText().toString().equals("")) {
            if(mobile.getText().toString().length()==10 || mobile.getText().toString().equals(""))
            {
                try {
                    dbHandler.addProduct(new Products(name.getText().toString(),college.getText().toString(),mobile.getText().toString(),s.getSelectedItem().toString()));

                    countHandler.addProduct(count+1);
                    name.setText("");
                    count+=1;

                    try {
                        v.setText("ID: QK20" + (count) + s.getSelectedItem().toString() + "G");
                    }
                    catch(Exception e){
                        v.setText("ID: QK20" + (count) +"NOEVENTS" + "G");
                    }
                    Toast.makeText(this, "added Successfully", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(this, "Add Events to register", Toast.LENGTH_SHORT).show();
                }

            }
            else
                Toast.makeText(this,"Enter valid Mobile Number",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,"Enter name",Toast.LENGTH_SHORT).show();
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