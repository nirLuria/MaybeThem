package com.maybethem.maybethem.swipe;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.maybethem.maybethem.DataBaseHelper;
import com.maybethem.maybethem.Details;
import com.maybethem.maybethem.DetailsWoman;
import com.maybethem.maybethem.R;
import com.maybethem.maybethem.friends.AbstractFriends;
import com.maybethem.maybethem.friends.Friend;
import com.maybethem.maybethem.friends.Men;
import com.maybethem.maybethem.friends.Women;
import com.maybethem.maybethem.pair.Pair;

import java.util.ArrayList;

/**
 * Created by nirlu on 17/09/2017.
 */

public class CustomSwipeAdapter71 extends PagerAdapter
{

    private Context context;
    private LayoutInflater layoutInflater;
    DataBaseHelper myDb;
    String gender;
    Button deleteFriend, editFriend;

    //image size.
    final int Height=550;
    final int Width=550;


    public CustomSwipeAdapter71(Context context, DataBaseHelper myDb, String gender)
    {
        this.context=context;
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
        return view==(RelativeLayout)object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView =  layoutInflater.inflate(R.layout.swipe_layout, container, false);
        ImageView imageView = (ImageView)itemView.findViewById(R.id.imageView);
        TextView textViewFirstName = (TextView)itemView.findViewById(R.id.first_name);
        TextView textViewAge = (TextView)itemView.findViewById(R.id.age);
        TextView textViewNumber = (TextView)itemView.findViewById(R.id.number);

        TextView textViewHobbies = (TextView)itemView.findViewById(R.id.hobbies);
        TextView textViewRedLine = (TextView)itemView.findViewById(R.id.red_line);

        deleteFriend = (Button) itemView.findViewById(R.id.deleteFriend);
        editFriend = (Button) itemView.findViewById(R.id.editFriend);






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
        imageView.getLayoutParams().height = Height;
        imageView.getLayoutParams().width = Width;
        ///


   //     System.out.println("friend: "+friend.getFirstName()+", "+friend.getLastName()+", "+ friend.getAge());

        container.addView(itemView);




        deleteFriend(friend, position);
        editFriend(friend, position);




        return itemView;
    }




    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((RelativeLayout)object);
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
                String hobbiesItems=res.getString(7);
                String otherHobbies=res.getString(8);
                String redLineItems=res.getString(9);
                String otherRedLine=res.getString(10);

                Friend f = new Friend( firstName, age,  phoneNumber,  gender,  hobbies,  redLine, image,
                        hobbiesItems, otherHobbies, redLineItems, otherRedLine);
                arrayList.add(f);


            }
        }


        /*
        Cursor res2 = myDb.test(myGender);
        System.out.println("This is test-------------------------");
        while (res2.moveToNext())
        {
            System.out.println("name is: "+res2.getString(0));

        }
        System.out.println("end of test-------------------------");

*/


        return arrayList;

    }


    public void deleteFriend(final Friend friend, final int position)
    {
        deleteFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                AlertDialog.Builder alert_builder = new AlertDialog.Builder(context);
                alert_builder.setMessage("האם אתה בטוח שברצונך למחוק את "+friend.getFirstName()+"?")
                        .setCancelable(false)
                        .setNegativeButton("ממש לא", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .setPositiveButton("בטח", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                if (myDb.deleteFriend(friend.getPhoneNumber()))
                                {
                                    System.out.println("i deleted you "+ friend.getPhoneNumber());
                                    Intent intent;
                                    if (gender.equals("man"))
                                    {
                                        intent = new Intent(context,Men.class);

                                    }
                                    else
                                    {
                                        intent = new Intent(context,Women.class);

                                    }


                                    intent.putExtra("position", position-1);
                                    System.out.println("This is  position  "+ position);

                                    context.startActivity(intent);
                                    ((Activity)context).finish();


                                }
                                else
                                {
                                    System.out.println("i can't delete "+ friend.getPhoneNumber());
                                }
                            }
                        });
                AlertDialog alert = alert_builder.create();
                alert.setTitle("");
                alert.show();

            }
        });
    }



    public void editFriend(final Friend friend, final int position)
    {


        editFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent intent ;

                if (gender.equals("man"))
                {
                    intent =new Intent(context,Details.class);
                }
                else
                {
                    intent =new Intent(context,DetailsWoman.class);

                }

                intent.putExtra("edit", "true");
                intent.putExtra("fullName", friend.getFirstName());
                intent.putExtra("phoneNumber", friend.getPhoneNumber());
                intent.putExtra("age", friend.getAge());
                intent.putExtra("gender", friend.getGender());
                intent.putExtra("hobbies", friend.getHobbies());
                intent.putExtra("redLine", friend.getRedLine());
                intent.putExtra("hobbiesItems", friend.getHobbiesItems());
                intent.putExtra("otherHobbies", friend.getOtherHobbies());
                intent.putExtra("redLineItems", friend.getRedLineItems());
                intent.putExtra("otherRedLine", friend.getOtherRedLine());


                intent.putExtra("image", friend.getImage());



                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
    }






}
