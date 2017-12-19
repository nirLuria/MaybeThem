package com.maybethem.maybethem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by nirlu on 27/10/2017.
 */

public class ChooseGenderToAdd extends AppCompatActivity
{
    RadioButton man, woman;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.choose_gender_activity);

         //functions:

        manClickListener();
        womanClickListener();
        back();

    }


    public void manClickListener()
    {
        man= (RadioButton)findViewById(R.id.buttonMan);
        man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.maybethem.maybethem.Details");
                man.setChecked(false);

                startActivity(intent);

              }
        });

    }

    public void womanClickListener()
    {
        woman= (RadioButton)findViewById(R.id.buttonWoman);
        woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.maybethem.maybethem.DetailsWoman");
                woman.setChecked(false);

                startActivity(intent);
            }
        });

    }


    public void back()
    {
        back= (Button)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }

}
