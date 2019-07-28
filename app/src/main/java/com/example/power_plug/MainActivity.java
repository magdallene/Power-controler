package com.example.power_plug;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements OnDataSendToActivity {

    ImageView bg_state;
    Button btn_rl;
    TextView txt_network;
    String url=Configuration.getData();
    String my_url="http://"+url+"/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bg_state = findViewById(R.id.bg_status);
        txt_network = findViewById(R.id.txt_network);

        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isNetworkAvailable()){
                    bg_state.setImageResource(R.drawable.background);
                    txt_network.setText("");
                }else{
                    bg_state.setImageResource(R.drawable.background_on);
                    txt_network.setText(getString(R.string.Bug));
                }

                updateStatus();
                handler.postDelayed(this, 2000);
            }
        }, 5000);  //the time is in miliseconds



        btn_rl = findViewById(R.id.sw_1);

        btn_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url_rl = my_url+"room_light";
                SelectTask task = new SelectTask(url_rl);
                task.execute();
                updateStatus();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.conf){
            Intent intent_conf = new Intent(MainActivity.this, Configuration.class);
            startActivityForResult(intent_conf,1);
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void sendData(String str) {
        updateButtonStatus(str);
    }

    private void updateStatus(){
        String url_rl = my_url+"status";
        StatusTask task = new StatusTask(url_rl, this);
        task.execute();
    }

    //Function for updating Button Status
    private void updateButtonStatus(String jsonStrings){
        try {
            JSONObject json = new JSONObject(jsonStrings);

            String room_light = json.getString("rl");


            if(room_light.equals("1")){
                btn_rl.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.plug_90off);
            }else{
                btn_rl.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.plug_90on);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

    }
}