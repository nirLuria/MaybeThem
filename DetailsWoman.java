package com.maybethem.maybethem;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by nirlu on 02/11/2017.
 */

public class DetailsWoman extends Details
{
    TextView title;
    Button redLineTxt;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_of_new_friend_activity);


        textChanges();

        initialize();
        redLineOnClickListener();
        hobbiesOnClickListener();
        callContactsList();
        submit();

    }

    public void textChanges()
    {
        title = (TextView)findViewById(R.id.title);
        title.setText("הוסף חברה");

        gender="woman";

        redLineTxt = (Button)findViewById(R.id.btnRedLine);
        redLineTxt.setText("קו אדום מבחינתה");

        redLineInnerText="היא לא תצא עם בחור ";

    }


    public void redLineOnClickListener()
    {

        mRedLineItemSelected = (TextView)findViewById(R.id.tvRedLineItemSelected);
        redLineListItems= getResources().getStringArray(R.array.red_line_item_woman);
        redLineCheckedItems = new boolean[redLineListItems.length];
        mRedLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(DetailsWoman.this);
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


}
