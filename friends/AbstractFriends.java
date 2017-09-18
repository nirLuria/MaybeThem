package com.maybethem.maybethem.friends;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.maybethem.maybethem.DataBaseHelper;
import com.maybethem.maybethem.R;
import com.maybethem.maybethem.swipe.SwipeAdapter;

import java.util.ArrayList;

/**
 * Created by nirlu on 10/09/2017.
 */

public abstract class AbstractFriends extends FragmentActivity
{
    protected TextView title;
    DataBaseHelper myDb;
    ViewPager viewPager;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conteiner_activity);

        myDb = new DataBaseHelper(this);


        viewPager= (ViewPager) findViewById(R.id.viewPager);
        SwipeAdapter swipeAdapter = new SwipeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(swipeAdapter);


    }


    public int getMyFriendsCount(String myGender)
    {
        int count=0;
        Cursor res = myDb.getFriends(myGender);
        if (res.getCount()==0)
        {
            return 0;
        }
        else
        {
            while (res.moveToNext())
            {
                count++;
            }
        }
        return count;
    }


    public ArrayList getMyFriends(String myGender)
    {
        ArrayList arrayList = new ArrayList<>();
        Cursor res = myDb.getFriends(myGender);
        if (res.getCount()==0)
        {
            System.out.println(" no friends");

            return arrayList;
        }
        else
        {
            while (res.moveToNext())
            {
                String firstName=res.getString(0);
                String lastName=res.getString(1);
                String phoneNumber=res.getString(2);
                String gender=res.getString(3);
                String hobbies=res.getString(4);
                String redLine=res.getString(5);

                Friend f = new Friend( firstName,  lastName,  phoneNumber,  gender,  hobbies,  redLine);
                arrayList.add(f);


            }
        }
        return arrayList;
    }

    public void print(String gender)
    {
        ArrayList arrayList =getMyFriends(gender);

        if (arrayList==null)
        {
            System.out.println("no "+gender+" friends");
        }
        else
        {
            for (int i=0; i<arrayList.size(); i++)
            {
                Friend friend = (Friend) arrayList.get(i);
                friend.printFriend();
            }
        }
    }

}
