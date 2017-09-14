package com.maybethem.maybethem.friends;

import android.os.Bundle;
import android.widget.TextView;

import com.maybethem.maybethem.R;
import com.maybethem.maybethem.friends.AbstractFriends;

/**
 * Created by nirlu on 10/09/2017.
 */

public class Women extends AbstractFriends
{
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        title=(TextView) findViewById(R.id.title);
        title.setText("girls");

        print("woman");

    }


}
