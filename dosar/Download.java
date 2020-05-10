package com.example.dosar;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.CornerPathEffect;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Environment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Download extends AppCompatActivity {
    EditText start;
    private  final String FILE_NAME="printData.txt";
    EditText end;
    MyDBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downlad);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        start=(EditText)findViewById(R.id.startId);
        end=(EditText)findViewById(R.id.endId);
        dbHandler=Register.getRegister().dbHandler;
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.setText("");
                end.setText("");
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
        }
        //if(Build.VERSION.SDK_INT>=BU)
    }
    @Override
    public void onRequestPermissionsResult(int requsetCode, @NonNull String[] permisssions,@NonNull int[] grantResults)
    {
        switch(requsetCode)
        {
            case 1000: {
                if (!(grantResults[0] == PackageManager.PERMISSION_GRANTED))
                    Toast.makeText(this, "Permissions not Granted", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onDownloadButtonClicked(View v)
    {   closeKeyboard();
        if(!start.getText().toString().equals("") && !end.getText().toString().equals(""))
        {
            int s=Integer.parseInt(start.getText().toString());
            int e=Integer.parseInt(end.getText().toString());
            int first,last;
            if(s>e)
                {first=e;last=s;}
            else
                {first=s;last=e;}
            String query="SELECT * FROM "+dbHandler.TABLE_PRODUCTS +" WHERE "+dbHandler.COLUMN_ID +"<="+ last+" AND "+dbHandler.COLUMN_ID+">="+first+";";
            SQLiteDatabase db=dbHandler.getWritableDatabase();
            Cursor recordSet=db.rawQuery(query,null);
            recordSet.moveToFirst();
            int t=-1,k=0;
            File file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),FILE_NAME);
            //File dir = new File("//InternalStorage//Download//");

            //File file = new File(dir, FILE_NAME);

            String toWrite="";
            FileOutputStream fos=null;
            String space="";
            String enter="";
             for(int i=0;i<40;i++)
             {enter+="\n";}
            while(!recordSet.isAfterLast())
            {
                toWrite += "QK20" + recordSet.getString(recordSet.getColumnIndex(dbHandler.COLUMN_ID)) + recordSet.getString(recordSet.getColumnIndex(dbHandler.COLUMN_EVENT)) + "G";
                int length=("QK20" + recordSet.getString(recordSet.getColumnIndex(dbHandler.COLUMN_ID)) + recordSet.getString(recordSet.getColumnIndex(dbHandler.COLUMN_EVENT)) + "G").length();
                recordSet.moveToNext();
                if(!recordSet.isAfterLast())
                {  for(int i=0;i<50-length;i++)
                      toWrite+=" ";
                   toWrite += "QK20" + recordSet.getString(recordSet.getColumnIndex(dbHandler.COLUMN_ID)) + recordSet.getString(recordSet.getColumnIndex(dbHandler.COLUMN_EVENT)) + "G";
                }
                recordSet.moveToPrevious();
                toWrite+="\n";

                toWrite +=recordSet.getString(recordSet.getColumnIndex(dbHandler.COLUMN_PRODUCTNAME));
                length=recordSet.getString(recordSet.getColumnIndex(dbHandler.COLUMN_PRODUCTNAME)).length();
                recordSet.moveToNext();
                if(!recordSet.isAfterLast())
                {
                    for(int i=0;i<50-length;i++)
                        toWrite+=" ";
                    toWrite += recordSet.getString(recordSet.getColumnIndex(dbHandler.COLUMN_PRODUCTNAME));
                }
                recordSet.moveToPrevious();
                toWrite+="\n";

                toWrite +=recordSet.getString(recordSet.getColumnIndex(dbHandler.COLUMN_COLLEGE));
                length=recordSet.getString(recordSet.getColumnIndex(dbHandler.COLUMN_COLLEGE)).length();
                recordSet.moveToNext();
                if(!recordSet.isAfterLast())
                {   for(int i=0;i<50-length;i++)
                        toWrite+=" ";
                    toWrite += recordSet.getString(recordSet.getColumnIndex(dbHandler.COLUMN_COLLEGE));
                }
                else
                    break;
                toWrite+=enter;
                recordSet.moveToNext();
                //Toast.makeText(this,toWrite,Toast.LENGTH_LONG).show();
            }
            try {
                //file.createNewFile();
                //fos=openFileOutput(FILE_NAME,MODE_PRIVATE);
                fos=new FileOutputStream(file);
                fos.write(toWrite.getBytes());
                fos.close();
                //DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                //downloadManager.addCompletedDownload(file.getName(), file.getName(), true, "text/plain",file.getAbsolutePath(),file.length(),true);
                Toast.makeText(this,"saved to"+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/PrintData.txt",Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException ex) {
                Toast.makeText(this,"File Not Found!",Toast.LENGTH_SHORT).show();
                ex.printStackTrace();
            }
            catch (IOException exc)
            {
                Toast.makeText(this,"Error saving!",Toast.LENGTH_SHORT).show();
                exc.printStackTrace();
            }
        }
        else
            Toast.makeText(this,"enter details to download",Toast.LENGTH_SHORT).show();
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
