package com.maybethem.maybethem;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


/**
 * Created by nirlu on 06/09/2017.
 */

public class Adding extends AppCompatActivity
{

    Button add_new_friend_button;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_activity);

        addNewFriendClickListener();

    }



    public void addNewFriendClickListener()
    {
        add_new_friend_button= (Button)findViewById(R.id.button_add_new_friend);
        add_new_friend_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.maybethem.maybethem.Details");
                startActivity(intent);
            }
        });
    }

}
