package com.maybethem.maybethem.friends;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.TextView;
import android.widget.Toast;

import com.maybethem.maybethem.DataBaseHelper;
import com.maybethem.maybethem.Details;
import com.maybethem.maybethem.R;
import com.maybethem.maybethem.swipe.CustomSwipeAdapter71;

import java.util.ArrayList;

/**
 * Created by nirlu on 10/09/2017.
 */

public abstract class AbstractFriends extends FragmentActivity
{
    protected TextView title;
    DataBaseHelper myDb;


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conteiner_activity);

        myDb = new DataBaseHelper(this);



    }

/*
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
*/

    public ArrayList getMyFriends(String myGender)
    {
        ArrayList arrayList = new ArrayList<>();
        Cursor res = myDb.getFriends(myGender);
        if (res.getCount()==0)
        {
            if(myGender.equals("man"))
            {
                Toast.makeText(AbstractFriends.this,"אין בנים במאגר", Toast.LENGTH_SHORT ).show();
            }
            else
            {
                Toast.makeText(AbstractFriends.this,"אין בנות במאגר", Toast.LENGTH_SHORT ).show();

            }


            return arrayList;
        }
        else
        {
            while (res.moveToNext())
            {
                String firstName=res.getString(0);
 //               String lastName=res.getString(1);
                int age=Integer.parseInt(res.getString(1));
                String phoneNumber=res.getString(2);
                String gender=res.getString(3);
                String hobbies=res.getString(4);
                String redLine=res.getString(5);
                byte[] image=res.getBlob(6);
                String hobbiesItems=res.getString(7);
                String otherHobbies=res.getString(8);


                Friend f = new Friend( firstName, age,   phoneNumber,  gender,  hobbies,  redLine, image,
                        hobbiesItems, otherHobbies);
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
