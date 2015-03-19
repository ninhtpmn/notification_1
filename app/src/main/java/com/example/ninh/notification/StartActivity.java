package com.example.ninh.notification;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by ninh on 25/03/2015.
 */
public class StartActivity extends ActionBarActivity{

    ArrayList<ItemLocation> arraydata;
    ListView listpending;
    MyArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        final DBHelper mydb = new DBHelper(this);

        listpending = (ListView)findViewById(R.id.listpending);

        listpending.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(StartActivity.this);

                // Setting Dialog Title
                alertDialog.setTitle(getString(R.string.options));

                // Setting Dialog Message
                alertDialog.setMessage(getString(R.string.message_options));

                alertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


                alertDialog.setNeutralButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        mydb.deleteLocationAlarm(arraydata.get(position).getID());

                        arraydata = mydb.getListLocationAlarm();
                        adapter = new MyArrayAdapter(StartActivity.this, R.layout.mylistview, arraydata);
                        listpending.setAdapter(adapter);

                        dialog.dismiss();
                    }
                });

                alertDialog.setPositiveButton(getString(R.string.edit), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        Intent i = new Intent(StartActivity.this, SetLocationAlarm.class);
                        i.putExtra(getString(R.string.id), arraydata.get(position).getID());
                        startActivity(i);
                    }
                });


                // Showing Alert Message
                alertDialog.show();
            }
        });

    }

    public void setTime(View v)
    {
        Intent i = new Intent(this, SetTimeAlarm.class);
        startActivity(i);
    }

    public void setLocation(View v)
    {
        Intent i = new Intent(this, SetLocationAlarm.class);
        i.putExtra("ID", 0);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("LOG", "ONRESUME");
        DBHelper mydb = new DBHelper(this);


        arraydata = mydb.getListLocationAlarm();

        adapter = new MyArrayAdapter(this, R.layout.mylistview, arraydata);
        listpending.setAdapter(adapter);
    }
}
