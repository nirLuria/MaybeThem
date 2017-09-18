package com.maybethem.maybethem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    Button new_friend_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_main);

        //functions:
        addFriendClickListener();
        myContainerClickListener();


    }


    public void addFriendClickListener()
    {
        new_friend_button= (Button)findViewById(R.id.button_add_friends);
        new_friend_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.maybethem.maybethem.Details");
                startActivity(intent);
            }
        });
    }

    public void myContainerClickListener()
    {
        new_friend_button= (Button)findViewById(R.id.button_my_container);
        new_friend_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.maybethem.maybethem.ContainerChoose");
                startActivity(intent);
            }
        });
    }




}
