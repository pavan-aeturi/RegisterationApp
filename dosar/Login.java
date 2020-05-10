package com.example.dosar;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText username;
    EditText password;
    MyUserHandler userHandler;
    static Login login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        login=this;
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userHandler=new MyUserHandler(this,null,null,1);
        username=(EditText)findViewById(R.id.user_name);
        password=(EditText)findViewById(R.id.password);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username.setText("");
                password.setText("");
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       int k= userHandler.addProduct("admin","tiger@#7845.");
    }

    public static Login getUserData(){
        return login;
    }
    public void authenticateButtonClicked(View v) {
        closeKeyboard();
        String query = "SELECT * FROM " + userHandler.TABLE_PRODUCTS + " WHERE " + userHandler.COLUMN_USERNAME + " = '" + username.getText().toString() + "' ";
        Cursor recordSet = userHandler.getWritableDatabase().rawQuery(query, null);
        recordSet.moveToFirst();
        if (!recordSet.isAfterLast()) {

            if (username.getText().toString().equals(recordSet.getString(recordSet.getColumnIndex(userHandler.COLUMN_USERNAME)))) {
                if (password.getText().toString().equals(recordSet.getString(recordSet.getColumnIndex(userHandler.COLUMN_PASSWORD)))) {
                    Intent i;
                    String s;
                    s = getIntent().getStringExtra("parent");
                    if(s.equals("login"))
                    {
                        i=new Intent(this,Register.class);
                       // i.putExtra("username",username.getText().toString());
                        startActivity(i);
                    }
                    else
                    {
                        i=new Intent(this,Manage.class);
                        i.putExtra("username",username.getText().toString());
                        startActivity(i);
                    }

                } else
                    Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this,"Invalid Username",Toast.LENGTH_SHORT).show();
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
