package com.maybethem.maybethem.pair;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.maybethem.maybethem.DataBaseHelper;
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

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pair_activity);


        myDb = new DataBaseHelper(this);
        manText=(TextView) findViewById(R.id.manText);
        womanText=(TextView) findViewById(R.id.womanText);


        generatePair();
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
        }
        else if (womanCount>0)
        {
            System.out.println("there are no men");
        }
        else
        {
            System.out.println("there are no friends at your database");
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
        String lastName=res.getString(1);
        int age=Integer.parseInt(res.getString(2));
        String phoneNumber=res.getString(3);
        String gender=res.getString(4);
        String hobbies=res.getString(5);
        String redLine=res.getString(6);
        byte[] image=res.getBlob(7);


        Friend f = new Friend( firstName,  lastName, age,   phoneNumber,  gender,  hobbies,  redLine, image);

        return f;
    }
}
