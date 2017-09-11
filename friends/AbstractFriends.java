package com.maybethem.maybethem.friends;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.maybethem.maybethem.DataBaseHelper;

import java.util.ArrayList;

/**
 * Created by nirlu on 10/09/2017.
 */

public abstract class AbstractFriends extends AppCompatActivity
{
    protected TextView title;
    DataBaseHelper myDb;


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        myDb = new DataBaseHelper(this);


    }

    public ArrayList getFriends(String gender)
    {
        ArrayList arrayList = new ArrayList<>();
        Cursor res = myDb.getMen();
        if (res.getCount()==0)
        {
            System.out.println(" no friends");

            return arrayList;
        }
        else
        {
            int count =1;
            while (res.moveToNext())
            {
                if (res.getString(3).equals(gender))
                {
                    Friend f = new Friend(res.getString(0),res.getString(1),res.getString(2),res.getString(3));
                    arrayList.add(f);
                }

                /*
                System.out.println("nir " +count++);
                System.out.println(res.getString(0));
                System.out.println(res.getString(1));
                System.out.println(res.getString(2));
                System.out.println(res.getString(3));

*/

            }
        }
        return arrayList;
    }



    public void print(String gender)
    {
        ArrayList arrayList =getFriends(gender);

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
