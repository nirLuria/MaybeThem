package com.maybethem.maybethem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

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
                Intent intent = new Intent("com.maybethem.maybethem.friends.Men");
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
                Intent intent = new Intent("com.maybethem.maybethem.friends.Women");
                startActivity(intent);
            }
        });
    }



}