package com.maybethem.maybethem.friends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.maybethem.maybethem.R;
import com.maybethem.maybethem.friends.AbstractFriends;
import com.maybethem.maybethem.swipe.CustomSwipeAdapter71;

/**
 * Created by nirlu on 10/09/2017.
 */

public class Women extends AbstractFriends
{
    ViewPager viewPager;
    CustomSwipeAdapter71 adapter;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        int position= intent.getIntExtra("position", 0);
        if (position<0)
        {
            position=0;
        }

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        adapter = new CustomSwipeAdapter71(this, myDb, "woman");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);


        print("woman");

    }


}
