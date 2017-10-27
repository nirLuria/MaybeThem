package com.maybethem.maybethem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by nirlu on 27/10/2017.
 */

public class ChooseGenderToAdd extends AppCompatActivity
{
    Button man, woman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.choose_gender_activity);

        //functions:

        manClickListener();
        womanClickListener();
    }


    public void manClickListener()
    {
        man= (Button)findViewById(R.id.buttonMan);
        man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.maybethem.maybethem.Details");
                startActivity(intent);
            }
        });
    }

    public void womanClickListener()
    {
        woman= (Button)findViewById(R.id.buttonWoman);
        woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.maybethem.maybethem.Details");
                startActivity(intent);
            }
        });
    }



}
