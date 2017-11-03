package com.maybethem.maybethem;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.maybethem.maybethem.friends.Friend;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import static java.io.FileDescriptor.out;

/**
 * Created by nirlu on 06/09/2017.
 */

public class Details extends AppCompatActivity
{


    Button submit;
    Button contact;
    EditText ageET, firstNameET, lastNameET, phoneNumberET;
    private static RadioGroup radioGroup;
    private static RadioButton radio_choose;
    DataBaseHelper myDb;
    String gender;
    private final static int PICK_CONTACT = 1;

    //hobbies variables.
    EditText inputHobbies;
    String otherHobbies, hobbiesItems;
    boolean otherHobbiesIsChecked=false;
    final int HOBBIES_OTHER_INDEX=6;
    Button mHobbies;
    TextView mHobbiesItemSelected;
    String[] hobbiesListItems;
    ArrayList<Integer> mHobbiesUserItems = new ArrayList<>();
    boolean[] hobbiesCheckedItems;

    //red line variables.
    Button mRedLine;
    TextView mRedLineItemSelected;
    String[] redLineListItems;
    ArrayList<Integer> mRedLineUserItems = new ArrayList<>();
    boolean[] redLineCheckedItems;
    final int RED_LINE_OTHER_INDEX=7;
    boolean otherRedLineIsChecked=false;
    String otherRedLine, redLineItems;
    EditText inputRedLine;
    String redLineInnerText;


    //image variables.
    ImageView imageView;
    Button btnChoose;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;




    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_of_new_friend_activity);

        gender="man";
        redLineInnerText="הוא לא יצא עם בחורה ";
        initialize();
        redLineOnClickListener();
        hobbiesOnClickListener();
        callContactsList();
        submit();

    }




    public void initialize()
    {
        firstNameET = (EditText)findViewById(R.id.add_first_name);
        lastNameET = (EditText)findViewById(R.id.add_last_name);
        ageET = (EditText)findViewById(R.id.add_age);
        phoneNumberET = (EditText)findViewById(R.id.add_phone);
        myDb = new DataBaseHelper(this);
        otherHobbies="";


        contact= (Button)findViewById(R.id.contactListSearch);

        mHobbies = (Button)findViewById(R.id.btnHobbies);
        mRedLine = (Button)findViewById(R.id.btnRedLine);

        btnChoose=(Button)findViewById(R.id.btnChoose);
        imageView=(ImageView)findViewById(R.id.imageView);

        //hide the image.
        imageView.setScaleX(1);
        imageView.setScaleY(1);

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    public void callContactsList()
    {
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                callContactsClickListener(null);
            }
        });
    }





    //functions for getting contact information.
    //1.
    public  void callContactsClickListener(View v)
    {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }



    //2.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        super.onActivityResult(requestCode, resultCode, data);

        //for getting phone name and number.
        if (requestCode==PICK_CONTACT)
        {
            if (resultCode== ActionBarActivity.RESULT_OK)
            {
                Uri contactData = data.getData();
                Cursor c = getContentResolver().query(contactData, null, null,null,null);

                if(c.moveToFirst())
                {
                    String friendName = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                    if (Integer.parseInt(c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                    {
                        String friendNumber = getContactNumber(friendName);
                        Toast.makeText(Details.this,"friendNumber: "+friendNumber+", friendName: "+friendName, Toast.LENGTH_SHORT ).show();
                    }
                }
            }
        }

        //for image processing.
        else if(resultCode==RESULT_OK && requestCode==PICK_IMAGE)
        {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            imageView.getLayoutParams().height = 80;
            imageView.getLayoutParams().width = 140;

        }
    }



    //3.Find contact based on name.
    private String getContactNumber(String name)
    {
        String ret=null;
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                "DISPLAY_NAME = '" + name + "'", null, null);
        if (cursor.moveToFirst())
        {
            String contactId =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

            Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
            while (phones.moveToNext())
            {
                String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                ret=number;
            }
            phones.close();
        }
        cursor.close();

        return ret;
    }


    public void openGallery()
    {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }


    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK && requestCode==PICK_IMAGE)
        {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            imageView.getLayoutParams().height = 80;
            imageView.getLayoutParams().width = 140;

        }
    }
*/

    public byte[] imageViewToBite(ImageView image)
    {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);

        int size = stream.size();

        // Greater than 2 MB
        if( size> (1024 * 1024 * 2) )
        {
            System.out.println("image is too big: "+size);
        }
        else
        {
            System.out.println("image is too small: "+size);
        }

        byte[] byteArray = stream.toByteArray();
        return  byteArray;
    }


    public void addOtherHobbies()
    {
        //create alert dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("הוסף תחומי עניין נוספים: ");

        builder.setMessage("");
        inputHobbies= new EditText(this);
        inputHobbies.setText(otherHobbies);

        builder.setView(inputHobbies);

        //set negative button.
        builder.setNegativeButton("ביטול", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                inputHobbies.setText("");
                dialogInterface.dismiss();
            }
        });
        //set positive button.
        builder.setPositiveButton("אישור", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                Toast.makeText(Details.this,inputHobbies.getText().toString(), Toast.LENGTH_SHORT ).show();
                otherHobbies=inputHobbies.getText().toString();
            }
        });


        final AlertDialog alertDialog= builder.create();

        //show keyboard.
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        alertDialog.show();

    }

    public void hobbiesOnClickListener()
    {


        mHobbiesItemSelected = (TextView)findViewById(R.id.tvHobbiesItemSelected);
        hobbiesListItems= getResources().getStringArray(R.array.hobbies_item);
        hobbiesCheckedItems = new boolean[hobbiesListItems.length];
        mHobbies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Details.this);
                mBuilder.setTitle("תחומי עניין:");
                mBuilder.setMultiChoiceItems(hobbiesListItems, hobbiesCheckedItems, new DialogInterface.OnMultiChoiceClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked)
                    {
                        if (isChecked)
                        {
                            if (!mHobbiesUserItems.contains(position))
                            {
                                mHobbiesUserItems.add(position);
                            }
                            //handle "else".
                            if (position==HOBBIES_OTHER_INDEX)
                            {
                                addOtherHobbies();
                                otherHobbiesIsChecked=true;
                                //Toast.makeText(Details.this,"אחר...", Toast.LENGTH_SHORT ).show();
                            }
                        }
                        else if (mHobbiesUserItems.contains(position))
                        {
                            System.out.println("position is "+position);
                            mHobbiesUserItems.remove(new Integer(position));
                            if (position==HOBBIES_OTHER_INDEX)
                            {
                                otherHobbiesIsChecked=false;
                            }
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("אישור", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        String item = "";

                        for (int i= 0; i<mHobbiesUserItems.size(); i++)
                        {
                            if (!hobbiesListItems[mHobbiesUserItems.get(i)].equals("אחר"))
                            {
                                item = item + hobbiesListItems[mHobbiesUserItems.get(i)];
                                if (i!= mHobbiesUserItems.size()-1)
                                {
                                    item = item+", ";
                                }
                            }

                        }
                        mHobbiesItemSelected.setText(item);
                        hobbiesItems=item;

                        //close the keyboard.
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    }
                });

                mBuilder.setNegativeButton("ביטול", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                mBuilder.setNeutralButton("נקה הכל", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int i=0; i< hobbiesCheckedItems.length; i++)
                        {
                            hobbiesCheckedItems[i] = false;
                            mHobbiesUserItems.clear();
                            mHobbiesItemSelected.setText("");
                            hobbiesItems="";

                        }
                    }
                });

                AlertDialog mDAialog = mBuilder.create();
                mDAialog.show();
            }
        });
    }

    public void addOtherRedLines()
    {
        //create alert dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("הוסף קווים אדומים נוספים: ");

        builder.setMessage("");
        inputRedLine= new EditText(this);
        inputRedLine.setText(otherRedLine);

        builder.setView(inputRedLine);

        //set negative button.
        builder.setNegativeButton("ביטול", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                inputRedLine.setText("");
                dialogInterface.dismiss();
            }
        });
        //set positive button.
        builder.setPositiveButton("אישור", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                Toast.makeText(Details.this,inputRedLine.getText().toString(), Toast.LENGTH_SHORT ).show();
                otherRedLine=inputRedLine.getText().toString();
            }
        });


        final AlertDialog alertDialog= builder.create();

        //show keyboard.
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        alertDialog.show();

    }


    public void clearAllRedLines()
    {
        for(int i=0; i< redLineCheckedItems.length; i++)
        {
            redLineCheckedItems[i] = false;
            mRedLineUserItems.clear();
            mRedLineItemSelected.setText("");
            redLineItems="";
        }
        otherRedLine="";
    }


    public void clearAllHobbies()
    {
        for(int i=0; i< hobbiesCheckedItems.length; i++)
        {
            hobbiesCheckedItems[i] = false;
            mHobbiesUserItems.clear();
            mHobbiesItemSelected.setText("");
            hobbiesItems="";
        }
        otherHobbies="";
    }

    public void redLineOnClickListener()
    {

        mRedLineItemSelected = (TextView)findViewById(R.id.tvRedLineItemSelected);
        redLineListItems= getResources().getStringArray(R.array.red_line_item);
        redLineCheckedItems = new boolean[redLineListItems.length];
        mRedLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Details.this);
                mBuilder.setTitle(redLineInnerText);
                mBuilder.setMultiChoiceItems(redLineListItems, redLineCheckedItems, new DialogInterface.OnMultiChoiceClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked)
                    {
                        if (isChecked)
                        {
                            if (!mRedLineUserItems.contains(position))
                            {
                                mRedLineUserItems.add(position);
                            }
                            //handle "else".
                            if (position==RED_LINE_OTHER_INDEX)
                            {
                                addOtherRedLines();
                                otherRedLineIsChecked=true;
                                //Toast.makeText(Details.this,"אחר...", Toast.LENGTH_SHORT ).show();
                            }
                        }
                        else if (mRedLineUserItems.contains(position))
                        {
                            mRedLineUserItems.remove(new Integer(position));
                            if (position==RED_LINE_OTHER_INDEX)
                            {
                                otherRedLineIsChecked=false;
                            }
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("אישור", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        String item = "";

                        for (int i= 0; i<mRedLineUserItems.size(); i++)
                        {
                            if (!redLineListItems[mRedLineUserItems.get(i)].equals("אחר"))
                            {
                                item = item + redLineListItems[mRedLineUserItems.get(i)];
                                if (i!= mRedLineUserItems.size()-1)
                                {
                                    item = item+", ";
                                }
                            }

                        }
                        mRedLineItemSelected.setText(item);
                        redLineItems=item;

                        //close the keyboard.
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    }
                });

                mBuilder.setNegativeButton("ביטול", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                mBuilder.setNeutralButton("נקה הכל", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int i=0; i< redLineCheckedItems.length; i++)
                        {
                            redLineCheckedItems[i] = false;
                            mRedLineUserItems.clear();
                            mRedLineItemSelected.setText("");
                            redLineItems="";

                        }
                    }
                });

                AlertDialog mDAialog = mBuilder.create();
                mDAialog.show();
            }
        });
    }




    public void submit()
    {


        submit= (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

               // radioGroup= (RadioGroup) findViewById(R.id.gender_radio_group);
               // int selected_id=radioGroup.getCheckedRadioButtonId();
               // radio_choose = (RadioButton) findViewById(selected_id);
                String firstName=firstNameET.getText().toString();
                String lastName=lastNameET.getText().toString();
                String age=ageET.getText().toString();
                String phoneNumber=phoneNumberET.getText().toString();
                String hobbies="", redLine="";

                if (!checkName(firstName))
                {
                    printFNameError();
                }
                else if (!checkName(lastName))
                {
                    printLNameError();
                }
                /*
                else if (!checkGender())
                {
                    printGenderError();
                }
                */
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

                    if (otherHobbiesIsChecked)
                    {
                        hobbies=otherHobbies+", "+hobbiesItems;
                    }
                    else
                    {
                        hobbies=hobbiesItems;
                    }
                    if (otherRedLineIsChecked)
                    {
                        redLine=otherRedLine+", "+redLineItems;
                    }
                    else
                    {
                        redLine=redLineItems;
                    }

                    clearAllHobbies();
                    clearAllRedLines();

                    //String gender=radio_choose.getText().toString();

                    boolean isInserted = myDb.insertFriend(firstName, lastName, age,  phoneNumber, gender, hobbies, redLine, (imageViewToBite(imageView)));
                    if (isInserted==true)
                    {
                        Toast.makeText(Details.this, gender+", "+firstName+", "+lastName+", "+phoneNumber+" added successfully", Toast.LENGTH_SHORT ).show();
                        firstNameET.setText("");
                        lastNameET.setText("");
                        ageET.setText("");
                        phoneNumberET.setText("");
                   //     radio_choose.setChecked(false);
                        imageView.setImageResource(R.mipmap.ic_launcher);
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