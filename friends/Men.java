package com.maybethem.maybethem.friends;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.maybethem.maybethem.DataBaseHelper;
import com.maybethem.maybethem.R;
import com.maybethem.maybethem.friends.AbstractFriends;
import com.maybethem.maybethem.swipe.SwipeAdapter;

import java.util.ArrayList;

/**
 * Created by nirlu on 10/09/2017.
 */

public class Men extends AbstractFriends
{

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        title=(TextView) findViewById(R.id.title);
        title.setText("boys");

        print("man");

    //    System.out.println("count "+getMyFriendsCount("man"));



    }



}
