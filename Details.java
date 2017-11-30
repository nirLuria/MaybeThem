package com.maybethem.maybethem;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.maybethem.maybethem.friends.Men;
import com.maybethem.maybethem.friends.Women;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.io.FileDescriptor.out;

/**
 * Created by nirlu on 06/09/2017.
 */

public class Details extends AppCompatActivity
{


    Button submit;
    Button contact;
    EditText ageET, firstNameET, lastNameET, phoneNumberET;
    public static RadioGroup radioGroup;
    public static RadioButton radio_choose;
    DataBaseHelper myDb;
    String gender;
    String oldPhoneNumber;

    public final static int PICK_CONTACT = 1;
    public final static int imageHeight = 80;
    public final static int imageWidth = 140;

    int count=0;


    //hobbies variables.
    EditText inputHobbies;
    Button hobbiesBtn;
    String[] hobbiesGeneralList;
    ArrayList<Integer> mHobbiesUserCHoose = new ArrayList<>();
    boolean[] hobbiesCheckedItems;
    boolean otherHobbiesIsChecked=false;
    String otherHobbies, hobbiesItems;
    final int HOBBIES_OTHER_INDEX=6;


    //red line variables.
    EditText inputRedLine;
    Button redLineBtn;
    String[] redLineGeneralList;
    ArrayList<Integer> mRedLineUserChoose = new ArrayList<>();
    boolean[] redLineCheckedItems;
    boolean otherRedLineIsChecked=false;
    String otherRedLine, redLineItems;
    final int RED_LINE_OTHER_INDEX=7;
    String redLineInnerText;


    //image variables.
    ImageView imageView;
    Button btnChoose;
    public static final int PICK_IMAGE = 100;
    Uri imageUri;
    byte[] image;



    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_of_new_friend_activity);

        gender="man";
        redLineInnerText="הוא לא יצא עם בחורה ";
        initialize();
        setAnnonimousImage();
        redLineOnClickListener();
        hobbiesOnClickListener();
        callContactsList();
        submit();


        edit();

    //    printTest();
    }



    public void printTest()
    {

        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run()
            {
                System.out.println("mHobbiesUserCHoose  : ");
                for(int i=0; i<mHobbiesUserCHoose.size(); i++)
                {
                    System.out.print(mHobbiesUserCHoose.get(i)+",");
                }
                System.out.println("");
            }
        }, 0, 3, TimeUnit.SECONDS);

    }


    public void initialize()
    {



        firstNameET = (EditText)findViewById(R.id.add_first_name);
       // lastNameET = (EditText)findViewById(R.id.add_last_name);
        ageET = (EditText)findViewById(R.id.add_age);
        phoneNumberET = (EditText)findViewById(R.id.add_phone);
        myDb = new DataBaseHelper(this);
        otherHobbies="";


        contact= (Button)findViewById(R.id.contactListSearch);

        hobbiesBtn = (Button)findViewById(R.id.btnHobbies);
        redLineBtn = (Button)findViewById(R.id.btnRedLine);

        btnChoose=(Button)findViewById(R.id.btnChoose);
        imageView=(ImageView)findViewById(R.id.imageView);

        //hide the image.
      //  imageView.setScaleX(1);
      //  imageView.setScaleY(1);


        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        image=null;
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
//                String lastName=lastNameET.getText().toString();
                String age=ageET.getText().toString();
                String phoneNumber=phoneNumberET.getText().toString();
                String hobbies="", redLine="";

                if (!checkName(firstName))
                {
                    printFNameError();
                }

                /*
                else if (!checkName(lastName))
                {
                    printLNameError();
                }

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
                        otherHobbies="";
                    }


                    if (otherRedLineIsChecked)
                    {
                        redLine=otherRedLine+", "+redLineItems;
                    }
                    else
                    {
                        redLine=redLineItems;
                        otherRedLine="";

                    }

                    //set image for db.
                 //   if (image==null)
                    {
                        image=(imageViewToBite(imageView));
                    }


                    Intent intent = getIntent();
                    String edit= intent.getStringExtra("edit");
                    if ((edit!=null)&&(edit.equals("true")))
                    {
                        //remove
                        myDb.deleteFriend(oldPhoneNumber);

                        //add again.


                        boolean isInserted = myDb.insertFriend(firstName, age,  phoneNumber, gender, hobbies, redLine, image,
                                hobbiesItems, otherHobbies, redLineItems, otherRedLine);
                        if (isInserted==true)
                        {
                            Toast.makeText(Details.this, gender+", "+firstName+", "+phoneNumber+" changed successfully", Toast.LENGTH_SHORT ).show();
                            finish();

                            Intent refresh;
                            if (gender.equals("man"))
                            {
                                refresh = new Intent("com.maybethem.maybethem.friends.Men");
                            }
                            else
                            {
                                refresh = new Intent("com.maybethem.maybethem.friends.Women");
                            }
                            startActivity(refresh);
                        }
                        else
                        {
                            Toast.makeText(Details.this,"קרתה בעיה בהוספת חבר חדש", Toast.LENGTH_SHORT ).show();
                        }
                    }
                    else
                    {
                        boolean isInserted = myDb.insertFriend(firstName, age,  phoneNumber, gender, hobbies, redLine, image,
                                hobbiesItems, otherHobbies, redLineItems, otherRedLine);
                        if (isInserted==true)
                        {
                            Toast.makeText(Details.this, gender+", "+firstName+", "+phoneNumber+" added successfully", Toast.LENGTH_SHORT ).show();
                            firstNameET.setText("");
                            //      lastNameET.setText("");
                            ageET.setText("");
                            phoneNumberET.setText("");
                            //     radio_choose.setChecked(false);

                            //imageView.setImageResource(R.mipmap.ic_launcher);
                            setAnnonimousImage();
                        }
                        else
                        {
                            Toast.makeText(Details.this,"קרתה בעיה בהוספת חבר חדש", Toast.LENGTH_SHORT ).show();
                        }
                    }


                    clearAllHobbies();
                    clearAllRedLines();
                }
            }
        });

    }


    ///////////////////////////////////////     hobbies functions  ///////////////////////////////////////
    public void hobbiesOnClickListener()
    {

        hobbiesGeneralList= getResources().getStringArray(R.array.hobbies_item);
        hobbiesCheckedItems = new boolean[hobbiesGeneralList.length];
        hobbiesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Details.this);
                mBuilder.setTitle("תחומי עניין:");
                mBuilder.setMultiChoiceItems(hobbiesGeneralList, hobbiesCheckedItems, new DialogInterface.OnMultiChoiceClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked)
                    {
                        if (isChecked)
                        {
                            if (!mHobbiesUserCHoose.contains(position))
                            {



                                mHobbiesUserCHoose.add(position);


                            }
                            //handle "else".
                            if (position==HOBBIES_OTHER_INDEX)
                            {
                                addOtherHobbies();
                                otherHobbiesIsChecked=true;
                                //Toast.makeText(Details.this,"אחר...", Toast.LENGTH_SHORT ).show();
                            }
                        }
                        else if (mHobbiesUserCHoose.contains(position))
                        {



                            mHobbiesUserCHoose.remove(new Integer(position));



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

                        for (int i= 0; i<mHobbiesUserCHoose.size(); i++)
                        {
                            if (!hobbiesGeneralList[mHobbiesUserCHoose.get(i)].equals("אחר"))
                            {
                                item = item + hobbiesGeneralList[mHobbiesUserCHoose.get(i)];
                                if (i!= mHobbiesUserCHoose.size()-1)
                                {
                                    item = item+", ";
                                }
                            }

                        }

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
                            mHobbiesUserCHoose.clear();
                            hobbiesItems="";

                        }
                    }
                });

                AlertDialog mDAialog = mBuilder.create();
                mDAialog.show();
            }
        });
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

    public void clearAllHobbies()
    {
        for(int i=0; i< hobbiesCheckedItems.length; i++)
        {
            hobbiesCheckedItems[i] = false;
            mHobbiesUserCHoose.clear();
            hobbiesItems="";
        }
        otherHobbies="";
    }

    ///////////////////////////////////////     red line functions  ///////////////////////////////////////
    public void redLineOnClickListener()
    {

        redLineGeneralList= getResources().getStringArray(R.array.red_line_item);
        redLineCheckedItems = new boolean[redLineGeneralList.length];
        redLineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Details.this);
                mBuilder.setTitle(redLineInnerText);
                mBuilder.setMultiChoiceItems(redLineGeneralList, redLineCheckedItems, new DialogInterface.OnMultiChoiceClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked)
                    {
                        if (isChecked)
                        {
                            if (!mRedLineUserChoose.contains(position))
                            {
                                mRedLineUserChoose.add(position);
                            }
                            //handle "else".
                            if (position==RED_LINE_OTHER_INDEX)
                            {
                                addOtherRedLines();
                                otherRedLineIsChecked=true;
                                //Toast.makeText(Details.this,"אחר...", Toast.LENGTH_SHORT ).show();
                            }
                        }
                        else if (mRedLineUserChoose.contains(position))
                        {
                            mRedLineUserChoose.remove(new Integer(position));
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

                        for (int i= 0; i<mRedLineUserChoose.size(); i++)
                        {
                            if (!redLineGeneralList[mRedLineUserChoose.get(i)].equals("אחר"))
                            {
                                item = item + redLineGeneralList[mRedLineUserChoose.get(i)];
                                if (i!= mRedLineUserChoose.size()-1)
                                {
                                    item = item+", ";
                                }
                            }

                        }
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
                            mRedLineUserChoose.clear();
                            redLineItems="";

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
            mRedLineUserChoose.clear();
            redLineItems="";
        }
        otherRedLine="";
    }

    ///////////////////////////////////////     edit functions  ///////////////////////////////////////
    public void edit()
    {
        Intent intent = getIntent();
        String edit= intent.getStringExtra("edit");
        if ((edit!=null)&&(edit.equals("true")))
        {
            firstNameET.setText(intent.getStringExtra("fullName"));
            phoneNumberET.setText(intent.getStringExtra("phoneNumber"));
            oldPhoneNumber=intent.getStringExtra("phoneNumber");
            ageET.setText(""+intent.getIntExtra("age",0));


            inputHobbies= new EditText(this);
            inputHobbies.setText(intent.getStringExtra("hobbies"));

            inputRedLine= new EditText(this);
            inputRedLine.setText(intent.getStringExtra("redLine"));


            //hobbies.
            hobbiesItems=intent.getStringExtra("hobbiesItems");
            if ((hobbiesItems!=null))
            {
                markHobbiesCheckedItems(hobbiesItems);
            }

            otherHobbies=intent.getStringExtra("otherHobbies");
            if (!intent.getStringExtra("otherHobbies").equals(""))
            {
                otherHobbiesIsChecked=true;
                hobbiesCheckedItems[HOBBIES_OTHER_INDEX]=true;
                mHobbiesUserCHoose.add(HOBBIES_OTHER_INDEX);

            }

            //red line.
            redLineItems=intent.getStringExtra("redLineItems");
            if (redLineItems!=null)
            {
                markRedLineCheckedItems(redLineItems);
            }

            otherRedLine=intent.getStringExtra("otherRedLine");
            if (!intent.getStringExtra("otherRedLine").equals(""))
            {
                otherRedLineIsChecked=true;
                redLineCheckedItems[RED_LINE_OTHER_INDEX]=true;
                mRedLineUserChoose.add(RED_LINE_OTHER_INDEX);

            }


            //image.
            image=intent.getByteArrayExtra("image");
            setFriendImage(image);
        }
    }


    public void markHobbiesCheckedItems(String hobbiesItems)
    {

        for (int i=0; i<hobbiesGeneralList.length; i++)
        {
            if (hobbiesItems.contains(hobbiesGeneralList[i]))
            {
                hobbiesCheckedItems[i]=true;
                if (!mHobbiesUserCHoose.contains(i))
                {
                    mHobbiesUserCHoose.add(i);
                }
            }
        }
    }

    public void markRedLineCheckedItems(String redLineItems)
    {
        for (int i=0; i<redLineGeneralList.length; i++)
        {
            if (redLineItems.contains(redLineGeneralList[i]))
            {
                redLineCheckedItems[i]=true;
                if (!mRedLineUserChoose.contains(i))
                {
                    mRedLineUserChoose.add(i);
                }
            }
        }
    }


    ///////////////////////////////////////     check input functions  ///////////////////////////////////////
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
        if (!number.matches("[0-9+]+"))         //only numbers and '+'.
            return false;
        if (number.length()<9)
            return false;
    //    if (number.charAt(0)!='0')
      //      return false;

        Intent intent = getIntent();
        String edit= intent.getStringExtra("edit");
        if ((edit!=null)&&(edit.equals("true")))
        {
            //do nothing.
        }
        else
        {
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
        }

        return true;
    }


    ///////////////////////////////////////     print error functions  ///////////////////////////////////////
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


    ///////////////////////////////////////     image functions  ///////////////////////////////////////
    public void setAnnonimousImage()
    {
        String uri = "@drawable/annonimous_male";
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        imageView.setImageDrawable(res);
        imageView.getLayoutParams().height = imageHeight;
        imageView.getLayoutParams().width = imageWidth;
    }

    public void setFriendImage(Uri imageUri)
    {
        imageView.setImageURI(imageUri);
        imageView.getLayoutParams().height = imageHeight;
        imageView.getLayoutParams().width = imageWidth;
    }

    public void setFriendImage(byte[] imageByte)
    {
        //convert byte[] to bitmap.
        Bitmap b = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);

        imageView.setImageBitmap(b);
        imageView.getLayoutParams().height = imageHeight;
        imageView.getLayoutParams().width = imageWidth;
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

        /*
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
*/
        byte[] byteArray = stream.toByteArray();
        return  byteArray;
    }

    ///////////////////////////////////////     call Contacts List functions  ///////////////////////////////////////
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
                    String image = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.PHOTO_URI));

                    if (Integer.parseInt(c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                    {
                        String friendNumber = getContactNumber(friendName);
                        Toast.makeText(Details.this,"friendNumber: "+friendNumber+", friendName: "+friendName, Toast.LENGTH_SHORT ).show();
                        firstNameET.setText(friendName);
                        phoneNumberET.setText(friendNumber);

                        //set image.
                        if (image!=null)
                        {
                            setFriendImage(Uri.parse(image));
                        }
                        else
                        {
                            //  setFriendImage("no photo");
                        }
                    }
                }
            }
        }

        //for image processing.
        else if(resultCode==RESULT_OK && requestCode==PICK_IMAGE)
        {
            imageUri = data.getData();
            setFriendImage(imageUri);

        }
    }

    //3.Find contact based on name.
    public String getContactNumber(String name)
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
}