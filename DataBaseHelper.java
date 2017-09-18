package com.maybethem.maybethem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nirlu on 08/09/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper
{

    public static final String DATABASE_NAME= "Friends.db";

    //table of friends.
    public static final String TableFriends= "Table_Of_Friends";
    public static final String col1= "FIRST_NAME";
    public static final String col2= "LAST_NAME";
    public static final String col3= "AGE";
    public static final String col4= "PHONE_NUMBER";
    public static final String col5= "GENDER";
    public static final String col6= "HOBBIES";
    public static final String col7= "RED_LINE";




    public DataBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("Create table "+ TableFriends +" (FIRST_NAME TEXT , LAST_NAME TEXT, AGE INTEGER, PHONE_NUMBER TEXT PRIMARY KEY, GENDER TEXT, HOBBIES TEXT, RED_LINE TEXT ) ");
        System.out.println("created clean database");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TableFriends);

        onCreate(db);
    }


    ///////////////////////////////////    WRITE    /////////////////////////////////

    public boolean insertFriend (String firstName, String lastName, String age, String phoneNumber, String gender, String hobbies, String redLine)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col1, firstName);
        contentValues.put(col2, lastName);
        contentValues.put(col3, age);
        contentValues.put(col4, phoneNumber);
        if (gender.equals("בן"))
        {
            contentValues.put(col5, "man");
        }
        else
        {
            contentValues.put(col5, "woman");
        }
        contentValues.put(col6, hobbies);
        contentValues.put(col7, redLine);



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


    ///////////////////////////////////    READ    /////////////////////////////////


    public Cursor getFriends(String gender)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = ( "select * from "+TableFriends+" where GENDER='" + gender  + "'; " );
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
