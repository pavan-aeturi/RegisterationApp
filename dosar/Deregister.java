package com.example.dosar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Deregister extends AppCompatActivity implements View.OnClickListener{
    MyDBHandler d;
    Button b;
    EditText t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deregister);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        d=Register.getRegister().dbHandler;
        b=(Button) findViewById(R.id.deregister);
        t=(EditText)findViewById(R.id.id_deregister);
        b.setOnClickListener(this);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                t.setText("");
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        closeKeyboard();
        final View view=v;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Title");
        builder.setMessage("Are you sure you want to delete data of person with id:"+t.getText().toString());
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        d.deleteProduct(t.getText().toString());
                        t.setText("");
                        Toast.makeText(view.getContext(),"deregistration success",Toast.LENGTH_SHORT).show();
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                t.setText("");
            }
        });
        if(t.getText().toString()!=null|| t.getText().toString()!="") {
           try {
               int i=Integer.parseInt(t.getText().toString());
               AlertDialog dialog = builder.create();
               dialog.show();
           }
           catch (Exception e)
           {
               Toast.makeText(this,"Enter Valid Integer ID",Toast.LENGTH_SHORT).show();
           }

        }
        else
        {
            Toast.makeText(this,"Enter ID",Toast.LENGTH_LONG).show();
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
