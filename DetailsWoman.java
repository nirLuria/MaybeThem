package com.maybethem.maybethem;

import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by nirlu on 02/11/2017.
 */

public class DetailsWoman extends Details
{
    TextView title;


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_of_new_friend_activity);



        title = (TextView)findViewById(R.id.title);
        title.setText("הוסף חברה");

        gender="woman";


        initialize();
        redLineOnClickListener();
        hobbiesOnClickListener();
        submit();

    }


}
