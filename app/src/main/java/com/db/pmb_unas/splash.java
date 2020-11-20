package com.db.pmb_unas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class splash extends AppCompatActivity {
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String uusername_key_new = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent gogetstarted = new Intent(splash.this, MainActivity.class);
                startActivity(gogetstarted);
                finish();
            }
        }, 1000);

        getUsernameLocal();
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        uusername_key_new = sharedPreferences.getString(username_key, "");
        if(uusername_key_new.isEmpty()){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Intent gogetstarted = new Intent(splash.this, get_started.class);
                    //startActivity(gogetstarted);
                    finish();
                }
            }, 1000);
        }
        else{
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Intent gogetstarted = new Intent(splash.this, MainActivity.class);
                    //startActivity(gogetstarted);
                    finish();
                }
            }, 1000);
        }
    }
}
