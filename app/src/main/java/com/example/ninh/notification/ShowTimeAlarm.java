package com.example.ninh.notification;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by ninh on 04/04/2015.
 */
public class ShowTimeAlarm extends ActionBarActivity {

    public static final String ID = "ID";
    public static final String INTENT_FT = "android.intent.action.time";
    public static final int CREATE_NEW = 0;

    private DBHelper mydb;
    private ListView listtimealarm;
    private ArrayList<ItemTime> datas;
    private TimeArrayAdapter adapter;
    private AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_time_alarm);

        mydb = new DBHelper(this);
        listtimealarm = (ListView)findViewById(R.id.listtimealarm);
        alertDialog = new AlertDialog.Builder(ShowTimeAlarm.this);

        listtimealarm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

                        mydb.deleteTimeAlarm(datas.get(position).getID());

                        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        Intent intent = new Intent(ShowTimeAlarm.this, AlarmReceiver.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), datas.get(position).getidIntent(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        am.cancel(pendingIntent);
                        pendingIntent.cancel();

                        datas = mydb.getListTimeAlarm();
                        adapter = new TimeArrayAdapter(ShowTimeAlarm.this, R.layout.timelistview, datas);
                        listtimealarm.setAdapter(adapter);

                        dialog.dismiss();
                    }
                });

                alertDialog.setPositiveButton(getString(R.string.edit), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {

                        Intent i = new Intent(ShowTimeAlarm.this, SetTimeAlarm.class);
                        i.putExtra(ID, datas.get(position).getID());     // ID != 0, update
                        startActivity(i);

                    }
                });


                // Showing Alert Message
                alertDialog.show();
            }
        });
    }

    public void setAlarm(View v)
    {
        Intent i = new Intent(this, SetTimeAlarm.class);
        i.putExtra(ID, CREATE_NEW);   // ID = 0 CREATE NEW
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();

        datas = mydb.getListTimeAlarm();
        adapter = new TimeArrayAdapter(this, R.layout.timelistview, datas);
        listtimealarm.setAdapter(adapter);

        IntentFilter intentFilter = new IntentFilter(INTENT_FT);
        this.registerReceiver(TimeReceiver, intentFilter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(TimeReceiver);
    }

    private final BroadcastReceiver TimeReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub

            datas = mydb.getListTimeAlarm();
            adapter = new TimeArrayAdapter(ShowTimeAlarm.this, R.layout.timelistview, datas);
            listtimealarm.setAdapter(adapter);

        }
    };
}
