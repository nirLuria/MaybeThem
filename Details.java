package com.maybethem.maybethem;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.maybethem.maybethem.friends.Friend;

/**
 * Created by nirlu on 06/09/2017.
 */

public class Details extends AppCompatActivity {

    Button submit;
    EditText ageET, firstNameET, lastNameET, phoneNumberET;
    private static RadioGroup radioGroup;
    private static RadioButton radio_choose;
    DataBaseHelper myDb;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_of_new_friend_activity);

        firstNameET = (EditText)findViewById(R.id.add_first_name);
        lastNameET = (EditText)findViewById(R.id.add_last_name);
        ageET = (EditText)findViewById(R.id.add_age);
        phoneNumberET = (EditText)findViewById(R.id.add_phone);
        myDb = new DataBaseHelper(this);


        //     addNewFriendClickListener();

        submit();


    }


    public void submit()
    {

        submit= (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                radioGroup= (RadioGroup) findViewById(R.id.radioGroup);
                int selected_id=radioGroup.getCheckedRadioButtonId();
                radio_choose = (RadioButton) findViewById(selected_id);
                String firstName=firstNameET.getText().toString();
                String lastName=lastNameET.getText().toString();
                String age=ageET.getText().toString();
                String phoneNumber=phoneNumberET.getText().toString();

                if (!checkName(firstName))
                {
                    printFNameError();
                }
                else if (!checkName(lastName))
                {
                    printLNameError();
                }
                else if (!checkGender())
                {
                    printGenderError();
                }
                else if (!checkAge(age))
                {
                    printAgeError();
                }
                else if (!checkNumber(phoneNumber))
                {
                    printPhoneError();
                }
                else
                {
                    String gender=radio_choose.getText().toString();
                    boolean isInserted = myDb.insertFriend(firstName, lastName, phoneNumber, gender);
                    if (isInserted==true)
                    {
                        Toast.makeText(Details.this, gender+", "+firstName+", "+lastName+", "+phoneNumber+" added successfully", Toast.LENGTH_SHORT ).show();
                        firstNameET.setText("");
                        lastNameET.setText("");
                        ageET.setText("");
                        phoneNumberET.setText("");
                        radio_choose.setChecked(false);
                    }
                    else
                    {
                        Toast.makeText(Details.this,"error in adding friend", Toast.LENGTH_SHORT ).show();
                    }
                }


            }
        });

    }


    public boolean checkName(String firstName)
    {

        if (firstName==null)
            return false;
        if (firstName.length()==0)
            return false;

        return true;
    }

    public boolean checkGender()
    {
        if (radio_choose==null)
            return false;

        return true;
    }

    public boolean checkAge(String age)
    {
        if (age.equals(""))
            return false;
        if (!age.matches("[0-9]+"))
            return false;
        if (Integer.parseInt(age)>120)
            return false;
        return true;
    }

    public boolean checkNumber(String number)
    {
        if (number.equals(""))
            return false;
        if (!number.matches("[0-9]+"))
            return false;
        if (number.length()<9)
            return false;
        if (number.charAt(0)!='0')
            return false;


        //check that the new phone number is unique.
        Cursor res = myDb.getPhones();
        while (res.moveToNext())
        {
            if (number.equals(res.getString(0)))
            {
                Toast.makeText(Details.this,"This number is already exists in the system.", Toast.LENGTH_SHORT ).show();
                return false;
            }
        }

        return true;
    }

    public void printFNameError()
    {
        Toast.makeText(Details.this, "אנא הזן שם פרטי", Toast.LENGTH_SHORT ).show();

    }

    public void printLNameError()
    {
        Toast.makeText(Details.this, "אנא הזן שם משפחה", Toast.LENGTH_SHORT ).show();

    }

    public void printGenderError()
    {
        Toast.makeText(Details.this, "אנא בחר מין", Toast.LENGTH_SHORT ).show();

    }

    public void printAgeError()
    {
        Toast.makeText(Details.this, "אנא הזן גיל תקין", Toast.LENGTH_SHORT ).show();
    }

    public void printPhoneError()
    {
        Toast.makeText(Details.this, "אנא הזן מס' פלאפון תקין", Toast.LENGTH_SHORT ).show();
    }



}
