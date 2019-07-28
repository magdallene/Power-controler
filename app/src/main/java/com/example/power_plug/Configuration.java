package com.example.power_plug;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Configuration extends AppCompatActivity {

    private static String new_url;
    EditText ip_text;
    Button sub_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        Toolbar tool = findViewById(R.id.toolbar);
        setSupportActionBar(tool);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        ip_text = findViewById(R.id.ip_text);
        sub_btn= findViewById(R.id.sub_btn);
        //Update IP Address
        sub_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Configuration.this,MainActivity.class);
                new_url=ip_text.getEditableText().toString();
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }


    public static String getData(){
        return new_url;
    }
}