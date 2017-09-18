package com.maybethem.maybethem.swipe;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maybethem.maybethem.DataBaseHelper;
import com.maybethem.maybethem.R;
import com.maybethem.maybethem.friends.Friend;

import java.util.ArrayList;

/**
 * Created by nirlu on 17/09/2017.
 */

public class CustomSwipeAdapter71 extends PagerAdapter
{

    private int[] image_resources= {R.drawable.sample1, R.drawable.sample2, R.drawable.sample3, R.drawable.sample4, R.drawable.sample5};
    private Context ctx;
    private LayoutInflater layoutInflater;
    DataBaseHelper myDb;
    String gender;


    public CustomSwipeAdapter71(Context ctx, DataBaseHelper myDb, String gender)
    {
        this.ctx=ctx;
        this.myDb=myDb;
        this.gender=gender;
    }



    @Override
    public int getCount()
    {
        return myDb.getFriendsCount(gender);



        //return 7;
        //return image_resources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==(LinearLayout)object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView =  layoutInflater.inflate(R.layout.swipe_layout, container, false);
        ImageView imageView = (ImageView)itemView.findViewById(R.id.imageView);
        TextView textViewFirstName = (TextView)itemView.findViewById(R.id.first_name);
        TextView textViewAge = (TextView)itemView.findViewById(R.id.age);
        TextView textViewNumber = (TextView)itemView.findViewById(R.id.number);
        TextView textViewHobbies = (TextView)itemView.findViewById(R.id.hobbies);
        TextView textViewRedLine = (TextView)itemView.findViewById(R.id.red_line);


        imageView.setImageResource(image_resources[position/5]);

        ArrayList arrayOfFriends = getMyFriends(gender);
        Friend friend = (Friend) arrayOfFriends.get(position);
        textViewFirstName.setText( friend.getFirstName()+" "+friend.getLastName() );
        textViewAge.setText("גיל: " +friend.getAge());
        textViewNumber.setText("מס' פלאפון: " +friend.getPhoneNumber());
        textViewHobbies.setText("תחביבים: " +friend.getHobbies());
        textViewRedLine.setText("קו אדום מבחינתו: " +friend.getRedLine());


        System.out.println("friend: "+friend.getFirstName()+", "+friend.getLastName()+", "+ friend.getAge());


        //  textView.setText("Image: "+position);

        container.addView(itemView);


        return itemView;
    }




    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((LinearLayout)object);
    }



    public ArrayList getMyFriends(String myGender)
    {
        ArrayList<Friend> arrayList = new ArrayList<Friend>();
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
                int age=Integer.parseInt(res.getString(2));
                String phoneNumber=res.getString(3);
                String gender=res.getString(4);
                String hobbies=res.getString(5);
                String redLine=res.getString(6);

                Friend f = new Friend( firstName,  lastName, age,  phoneNumber,  gender,  hobbies,  redLine);
                arrayList.add(f);


            }
        }
        return arrayList;
    }


}
