package com.maybethem.maybethem;

import android.os.Bundle;
import android.widget.TextView;

import com.maybethem.maybethem.friends.AbstractFriends;

/**
 * Created by nirlu on 10/09/2017.
 */

public class Women extends AbstractFriends
{
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conteiner_activity);

        title=(TextView) findViewById(R.id.title);
        title.setText("girls");

        print("woman");

    }


}
