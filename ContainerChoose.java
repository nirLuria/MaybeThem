package com.maybethem.maybethem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by nirlu on 10/09/2017.
 */

public class ContainerChoose extends AppCompatActivity
{

    Button go_to_conteiner_men, go_to_conteiner_women;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conteiner_choose_activity);

        goToConteinerMenClickListener();
        goToConteinerWomenClickListener();
    }



    public void goToConteinerMenClickListener()
    {
        go_to_conteiner_men= (Button)findViewById(R.id.conteiner_men);
        go_to_conteiner_men.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.maybethem.maybethem.Men");
                startActivity(intent);
            }
        });
    }

    public void goToConteinerWomenClickListener()
    {
        go_to_conteiner_women= (Button)findViewById(R.id.conteiner_women);
        go_to_conteiner_women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.maybethem.maybethem.Women");
                startActivity(intent);
            }
        });
    }



}