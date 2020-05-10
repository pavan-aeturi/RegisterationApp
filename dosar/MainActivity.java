package com.example.dosar;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    Button register;
    Button search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        MenuItem m=(MenuItem)findViewById(R.id.action_settings);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        register=(Button)findViewById(R.id.Register_button);

        if(Register.getRegister()==null)
        {Intent i=new Intent(this,Login.class);
        i.putExtra("parent","login");
            startActivity(i);}
        search=(Button)findViewById(R.id.search_button);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId())
                {
                    case R.id.nav_home:
                    {   Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_SHORT).show();
                        drawer.closeDrawers();
                        break;
                    }
                    case  R.id.nav_gallery:
                    {//Toast.makeText(getApplicationContext(),"loading..",Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(MainActivity.this,Register.class);
                        drawer.closeDrawers();
                        startActivity(i);
                        break;
                    }
                    case R.id.nav_slideshow:
                    {   //Toast.makeText(getApplicationContext(),"Register",Toast.LENGTH_LONG).show();
                        drawer.closeDrawers();
                        Intent i=new Intent(MainActivity.this, Search.class);
                        startActivity(i);
                        break;
                    }
                    case  R.id.nav_send:
                    {   // Toast.makeText(getApplicationContext(),"loading..",Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(MainActivity.this, History.class);
                        drawer.closeDrawers();
                        startActivity(i);
                       // Toast.makeText(getApplicationContext(),"Search",Toast.LENGTH_LONG).show();
                        break;
                    }
                    case R.id.nav_tools:
                    {   drawer.closeDrawers();
                            Intent i = new Intent(MainActivity.this, Deregister.class);
                            startActivity(i);
                        break;

                    }
                    case R.id.nav_edit:
                    {
                        drawer.closeDrawers();
                        Intent i=new Intent (MainActivity.this,EditData.class);
                        startActivity(i);
                        break;
                    }
                    case  R.id.nav_download:
                    {
                        drawer.closeDrawers();
                        Intent i=new Intent (MainActivity.this,Download.class);
                        startActivity(i);
                        break;
                    }
                }

                return false;
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,Search.class);
                startActivity(i);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,Register.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
        case R.id.action_settings:
        {
            manage();
            break;
        }
        }
        return super.onOptionsItemSelected(item);
    }

    public void manage()
    {
        Intent i=new Intent(MainActivity.this,Login.class);
        i.putExtra("parent","manage");
        startActivity(i);
    }
}

