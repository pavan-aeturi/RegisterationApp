package com.example.dosar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
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
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Manage extends AppCompatActivity {
    MyEventHandler e;
    EditText event_name;
    EditText event_context;
    Button add;
    Button delete;
    Spinner s;
    EditText username;
    EditText password;
    EditText delete_username;
    EditText confirm_password;
    Login l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        l=Login.getUserData();
        delete_username=findViewById(R.id.delete_user_name);
        event_name=(EditText)findViewById(R.id.add_event_name);
        event_context=(EditText)findViewById(R.id.context);
        add=(Button)findViewById(R.id.addEvent);
        s=(Spinner)findViewById(R.id.spinnerDeleteEvent);
        delete=(Button)findViewById(R.id.delete_event_button);
        username=(EditText)findViewById(R.id.add_username);
        password=(EditText)findViewById(R.id.add_password);
        confirm_password=(EditText)findViewById(R.id.confirm_password);
        e=Register.getRegister().eventHandler;
        List<Event> events=new ArrayList<>();
        events= e.databaseToArray();
        ArrayAdapter<Event> adapter=new ArrayAdapter<Event>(this,android.R.layout.simple_spinner_item,events);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                event_name.setText("");
                event_context.setText("");
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String event=((Event)parent.getSelectedItem()).getEvent();
                String Context=((Event)parent.getSelectedItem()).getEventContext();
                String toast=event+" : "+Context;
                Toast.makeText(view.getContext(),toast,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void onAddButtonClicked(View v) {
        closeKeyboard();
       String event;
       String event_context_string;
       if(event_name.getText()!=null)
       {
        event=event_name.getText().toString();
        event_context_string=event_context.getText().toString();
           if(!event.equals(""))
           {
             String query="SELECT * FROM "+ e.TABLE_EVENT +" WHERE "+e.COLUMN_EVENT+"=\""+event+"\";";
               Cursor recordSet=e.getWritableDatabase().rawQuery(query,null);
               if(!recordSet.moveToFirst())
               {e.addProduct(event,event_context_string);
                ArrayAdapter<Event> adapter = new ArrayAdapter<Event>(this, android.R.layout.simple_spinner_item, e.databaseToArray());
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                s.setAdapter(adapter);
                Toast.makeText(this,"added Successfully",Toast.LENGTH_SHORT).show();
               }
               else
               {
                   e.getWritableDatabase().close();
                   Toast.makeText(this,"event already exists",Toast.LENGTH_SHORT).show();
               }
               event_name.setText("");
               event_context.setText("");
           }
           else
               Toast.makeText(this,"Enter Event Name",Toast.LENGTH_SHORT).show();
       }
       else
           Toast.makeText(this,"Enter Event",Toast.LENGTH_SHORT).show();
    }
    public void onDeleteUserButtonClicked(View v)
    {
        if(getIntent().getExtras().getString("username").equals("admin")) {
            //l.userHandler.deleteProduct(delete_username.getText().toString());
            closeKeyboard();
            final View view = v;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle("Title");
            builder.setMessage("Are you sure you want to user with username:" + delete_username.getText().toString()+" if existed");
            builder.setPositiveButton("Confirm",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            l.userHandler.deleteProduct(delete_username.getText().toString());
                            delete_username.setText("");
                            Toast.makeText(view.getContext(), "deregistration success", Toast.LENGTH_SHORT).show();
                        }
                    });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            if (!delete_username.getText().toString().equals("admin")) {
                AlertDialog dialog = builder.create();
                dialog.show();
            } else
                Toast.makeText(this, "admin cannot be deleted", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this,"only admin can ADD or DELETE user",Toast.LENGTH_SHORT).show();
    }

    public void onDelButtonClicked(View v){

        if(s.getSelectedItem()!=null) {
            String event=s.getSelectedItem().toString();
            String Context=((Event)s.getSelectedItem()).getEventContext();
            e.deleteProduct(event, Context);
            ArrayAdapter<Event> adapter = new ArrayAdapter<Event>(this, android.R.layout.simple_spinner_item, e.databaseToArray());
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s.setAdapter(adapter);
            Toast.makeText(this, "deleted successfully", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this,"no events to delete",Toast.LENGTH_SHORT).show();
    }
    public void onAddUserButtonClicked(View v)
    {
        if(getIntent().getExtras().getString("username").equals("admin")) {
            closeKeyboard();
        if(!username.getText().toString().equals(""))
        {
            if(!password.getText().toString().equals(""))
            {
                if(password.getText().toString().equals(confirm_password.getText().toString()))
                {
                    l.userHandler.addProduct(username.getText().toString(),password.getText().toString());
                    username.setText("");
                    password.setText("");
                    confirm_password.setText("");
                    Toast.makeText(this,"added Successfully",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(this,"match passwords to proceed",Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this,"Please Enter Password",Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this,"Enter Username",Toast.LENGTH_SHORT).show();
    }
        else
                Toast.makeText(this,"only admin can ADD or DELETE user",Toast.LENGTH_SHORT).show();
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
