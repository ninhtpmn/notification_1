package com.example.ninh.notification;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by ninh on 25/03/2015.
 */
public class ShowLocationAlarm extends ActionBarActivity{


    public static final String ID= "ID";
    public static final String INTENT_FL = "android.intent.action.location";
    public static final int CREATE_NEW = 0;

    private ArrayList<ItemLocation> datas;
    private ListView listpending;
    private MyArrayAdapter adapter;
    private DBHelper mydb;
    private AlertDialog.Builder alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        mydb = new DBHelper(this);
        alertDialog = new AlertDialog.Builder(ShowLocationAlarm.this);

        listpending = (ListView)findViewById(R.id.listpending);

        listpending.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

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

                        mydb.deleteLocationAlarm(datas.get(position).getID());

                        datas = mydb.getListLocationAlarm();
                        adapter = new MyArrayAdapter(ShowLocationAlarm.this, R.layout.mylistview, datas);
                        listpending.setAdapter(adapter);

                        dialog.dismiss();

                    }
                });

                alertDialog.setPositiveButton(getString(R.string.edit), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        Intent i = new Intent(ShowLocationAlarm.this, SetLocationAlarm.class);
                        i.putExtra(getString(R.string.id), datas.get(position).getID());
                        startActivity(i);
                    }
                });


                // Showing Alert Message
                alertDialog.show();
            }
        });

    }



    public void setLocation(View v)
    {
        Intent i = new Intent(this, SetLocationAlarm.class);

        i.putExtra(ID, CREATE_NEW);

        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("LOG", "ONRESUME");
        DBHelper mydb = new DBHelper(this);


        datas = mydb.getListLocationAlarm();

        adapter = new MyArrayAdapter(this, R.layout.mylistview, datas);
        listpending.setAdapter(adapter);

        IntentFilter intentFilter = new IntentFilter(INTENT_FL);

        this.registerReceiver(LocationReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(LocationReceiver);
    }

    private final BroadcastReceiver LocationReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub

            datas = mydb.getListTimeAlarm();
            adapter = new MyArrayAdapter(ShowLocationAlarm.this, R.layout.mylistview, datas);
            listpending.setAdapter(adapter);

        }
    };
}
