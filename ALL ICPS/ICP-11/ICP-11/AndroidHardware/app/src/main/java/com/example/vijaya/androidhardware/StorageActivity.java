package com.example.vijaya.androidhardware;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class StorageActivity extends AppCompatActivity {
    EditText value_hold;
    EditText value_hold2;
    String NAMEFILE = "MyAppStorage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        value_hold = (EditText) findViewById(R.id.id_txt_mycontent);
        value_hold2 = (EditText) findViewById(R.id.id_txt_display);
    }

    public void saveTofile(View v) throws IOException {
        // ICP Task4: Write the code to save the text
        String lineText = value_hold.getText().toString();
        FileOutputStream fileout = null;
        try{
            fileout = openFileOutput(NAMEFILE, MODE_APPEND);
            fileout.write(lineText.getBytes());
            value_hold.getText().clear();
            Toast.makeText(this,"save to" + getFilesDir()+"/" + NAMEFILE, Toast.LENGTH_LONG).show();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (fileout!=null){

                fileout.close();
            }
        }
    }

    public void retrieveFromFile(View v) throws IOException {
        // ICP Task4: Write the code to display the above saved text
        FileInputStream infile = null;
        try {
            infile = openFileInput(NAMEFILE);
            InputStreamReader instream = new InputStreamReader(infile);
            BufferedReader bufferreader = new BufferedReader(instream);
            StringBuilder sbuilder = new StringBuilder();
            String txt;
            while (((txt = bufferreader.readLine())!=null)){
                sbuilder.append(txt).append("\n");
            }
            value_hold2.setText(sbuilder.toString());
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(infile !=null){
                try{
                    infile.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
