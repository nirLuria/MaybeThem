package com.maybethem.maybethem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by nirlu on 08/09/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper
{

    public static final String DATABASE_NAME= "Friends.db";

    //table of friends.
    public static final String TableFriends= "Table_Of_Friends";
    public static final String col1= "FIRST_NAME";
  //  public static final String col2= "LAST_NAME";
    public static final String col2= "AGE";
    public static final String col3= "PHONE_NUMBER";
    public static final String col4= "GENDER";
    public static final String col5= "HOBBIES";
    public static final String col6= "RED_LINE";
    public static final String col7= "IMAGE";
    public static final String col8= "HOBBIES_ITEMS";
    public static final String col9= "OTHER_HOBBIES";
    public static final String col10= "REDLINE_ITEMS";
    public static final String col11= "OTHER_REDLINE";


    public DataBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("Create table "+ TableFriends +" (FIRST_NAME TEXT , AGE INTEGER, PHONE_NUMBER TEXT PRIMARY KEY, GENDER TEXT, HOBBIES TEXT, RED_LINE TEXT,  image BLOG , " +
                "HOBBIES_ITEMS TEXT, OTHER_HOBBIES TEXT, REDLINE_ITEMS TEXT, OTHER_REDLINE TEXT) ");
        System.out.println("created clean database");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TableFriends);

        onCreate(db);
    }


    ///////////////////////////////////    WRITE    /////////////////////////////////

    public boolean insertFriend (String firstName, String age, String phoneNumber, String gender, String hobbies, String redLine, byte[] image,
                                 String hobbiesItems, String otherHobbies, String redLineItems, String otherRedLine)
    {
    //    System.out.println("hobbiesItems: "+hobbiesItems);



        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col1, firstName);
   //     contentValues.put(col2, lastName);
        contentValues.put(col2, age);
        contentValues.put(col3, phoneNumber);
        if (gender.equals("man"))
        {
            contentValues.put(col4, "man");
        }
        else
        {
            contentValues.put(col4, "woman");
        }
        contentValues.put(col5, hobbies);
        contentValues.put(col6, redLine);
        contentValues.put(col7, image);
        contentValues.put(col8, hobbiesItems);
        contentValues.put(col9, otherHobbies);
        contentValues.put(col10, redLineItems);
        contentValues.put(col11, otherRedLine);


        long result = db.insert(TableFriends, null, contentValues);
        if (result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }


    public boolean deleteFriend(String phoneNumber)
    {
        SQLiteDatabase db = this.getWritableDatabase();


        db.execSQL("DELETE FROM " + TableFriends + "  where PHONE_NUMBER='" + phoneNumber + "'; " );

        return true;
    }

    ///////////////////////////////////    READ    /////////////////////////////////


    public Cursor getFriends(String gender)
    {
        SQLiteDatabase db = this.getWritableDatabase();
      //  String query = ( "select * from "+TableFriends+" where GENDER='" + gender  + "'; " );
        String query = ( "select * from "+TableFriends+" where GENDER='" + gender  + "' order by FIRST_NAME; " );

        Cursor res = db.rawQuery(query, null);

        return res;
    }


    public int getFriendsCount(String gender)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = ( "select * from "+TableFriends+" where GENDER='" + gender  + "'; " );
        Cursor res = db.rawQuery(query, null);

        return res.getCount();
    }


    public Cursor getPhones()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select PHONE_NUMBER from " + TableFriends, null);
        return res;
    }

}
