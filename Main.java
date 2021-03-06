package com.maybethem.maybethem;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Main extends AppCompatActivity
{
    Button add, pair, container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);

        //functions:


        addFriendClickListener();
        myContainerClickListener();
        pairClickListener();

    }



    public void pairClickListener()
    {
        pair= (Button)findViewById(R.id.button_create_match);
        pair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.maybethem.maybethem.pair.Pair");
                startActivity(intent);
            }
        });
    }

    public void addFriendClickListener()
    {
        add= (Button)findViewById(R.id.button_add_friends);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.maybethem.maybethem.ChooseGenderToAdd");
                startActivity(intent);
            }
        });
    }

    public void myContainerClickListener()
    {
        container= (Button)findViewById(R.id.button_my_container);
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.maybethem.maybethem.ContainerChoose");
                startActivity(intent);
            }
        });
    }




}
