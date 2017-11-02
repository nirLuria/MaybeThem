package com.maybethem.maybethem;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    Button new_friend_button;
    Button pair;
    private static int SPLASH_TIME_OUT=1000;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.welcome_screen_activity);


        //welcome screen.
        //
        new Handler().postDelayed(new Runnable()
        {
            public void run()
            {
                Intent intent = new Intent(MainActivity.this, Main.class);
                startActivity(intent);
                finish();


            }
        },SPLASH_TIME_OUT);


    }






}
