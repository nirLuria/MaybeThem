package com.maybethem.maybethem.swipe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.maybethem.maybethem.DataBaseHelper;
import com.maybethem.maybethem.friends.Men;

/**
 * Created by nirlu on 14/09/2017.
 */

public class SwipeAdapter extends FragmentStatePagerAdapter
{
    DataBaseHelper myDb;

    public SwipeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        Fragment fragment = new FriendFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("count",position+1);
        fragment.setArguments(bundle);

        return fragment;

    }

    @Override
    public int getCount()
    {

        return 4;
    }
}
