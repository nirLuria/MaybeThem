package com.maybethem.maybethem.pair;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.maybethem.maybethem.DataBaseHelper;
import com.maybethem.maybethem.Details;
import com.maybethem.maybethem.R;
import com.maybethem.maybethem.friends.Friend;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by nirlu on 16/10/2017.
 */

public class Pair extends AppCompatActivity
{

    DataBaseHelper myDb;
    TextView manText, womanText;
    Button refresh_button;


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pair_activity);


        myDb = new DataBaseHelper(this);
        manText=(TextView) findViewById(R.id.manText);
        womanText=(TextView) findViewById(R.id.womanText);


        generatePair();
        refreshActivity();
    }


    public void generatePair()
    {
        //count of friends.
        int manCount=getCountOf("man");
        int womanCount=getCountOf("woman");

        if (manCount>0 && womanCount>0)
        {
            //generate random friends.
            Random randomGenerator = new Random();
            int manIndex= randomGenerator.nextInt(manCount);
            int womanIndex= randomGenerator.nextInt(womanCount);


            Friend boy = getFriend("man", manIndex);
            Friend girl = getFriend("woman", womanIndex);

            manText.setText(boy.getFirstName());
            womanText.setText(girl.getFirstName());

            System.out.println("man : "+boy.getFirstName()+", woman : "+girl.getFirstName());

        }

        else if (manCount>0)
        {
            System.out.println("there are no women");
            Toast.makeText(Pair.this,"there are no women", Toast.LENGTH_SHORT ).show();

            manText.setText("");
            womanText.setText("");
        }
        else if (womanCount>0)
        {
            System.out.println("there are no men");
            Toast.makeText(Pair.this,"there are no men", Toast.LENGTH_SHORT ).show();
            manText.setText("");
            womanText.setText("");
        }
        else
        {
            System.out.println("there are no friends at your database");
            Toast.makeText(Pair.this,"there are no friends at your database", Toast.LENGTH_SHORT ).show();
            manText.setText("");
            womanText.setText("");
        }

    }


    public int getCountOf(String myGender)
    {
        int count=0;

        Cursor res = myDb.getFriends(myGender);
        if (res.getCount()==0)
        {
            return count;
        }
        else
        {
            while (res.moveToNext())
            {
                ++count;
            }
        }
        return count;
    }



    public Friend getFriend(String myGender, int index)
    {
        Cursor res = myDb.getFriends(myGender);

        for (int i=0; i<=index;i++)
        {
            res.moveToNext();
        }

        String firstName=res.getString(0);
     //   String lastName=res.getString(1);
        int age=Integer.parseInt(res.getString(1));
        String phoneNumber=res.getString(2);
        String gender=res.getString(3);
        String hobbies=res.getString(4);
        String redLine=res.getString(5);
        byte[] image=res.getBlob(6);
        String hobbiesItems=res.getString(7);


        Friend f = new Friend( firstName, age,   phoneNumber,  gender,  hobbies,  redLine, image, hobbiesItems );

        return f;
    }


    public void refreshActivity()
    {

        refresh_button= (Button)findViewById(R.id.refreshBtn);
        refresh_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                startActivity(intent);
                finish();
            }
        });
    }

}
