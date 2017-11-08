package com.maybethem.maybethem.swipe;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.maybethem.maybethem.DataBaseHelper;
import com.maybethem.maybethem.Details;
import com.maybethem.maybethem.R;
import com.maybethem.maybethem.friends.Friend;
import com.maybethem.maybethem.pair.Pair;

import java.util.ArrayList;

/**
 * Created by nirlu on 17/09/2017.
 */

public class CustomSwipeAdapter71 extends PagerAdapter
{

    private Context ctx;
    private LayoutInflater layoutInflater;
    DataBaseHelper myDb;
    String gender;
    Button deleteFriend;

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







        ArrayList arrayOfFriends = getMyFriends(gender);
        Friend friend = (Friend) arrayOfFriends.get(position);
        textViewFirstName.setText( friend.getFirstName() );
        textViewAge.setText("גיל: " +friend.getAge());
        textViewNumber.setText("מס' פלאפון: " +friend.getPhoneNumber());
        textViewHobbies.setText("תחביבים: " +friend.getHobbies());
        textViewRedLine.setText("קו אדום מבחינתו: " +friend.getRedLine());


        byte[] foodImage = friend.getImage();
        friend.printImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
        imageView.setImageBitmap(bitmap);
        imageView.getLayoutParams().height = 180;
        imageView.getLayoutParams().width = 240;
        ///


   //     System.out.println("friend: "+friend.getFirstName()+", "+friend.getLastName()+", "+ friend.getAge());

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
     //           String lastName=res.getString(1);
                int age=Integer.parseInt(res.getString(1));
                String phoneNumber=res.getString(2);
                String gender=res.getString(3);
                String hobbies=res.getString(4);
                String redLine=res.getString(5);
                byte[] image = res.getBlob(6);

                Friend f = new Friend( firstName, age,  phoneNumber,  gender,  hobbies,  redLine, image);
                arrayList.add(f);


            }
        }
        return arrayList;
    }


    public void deleteFriend()
    {
        deleteFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
             //   Toast.makeText(CustomSwipeAdapter71.this,"there are no women", Toast.LENGTH_SHORT ).show();
            }
        });
    }

}
