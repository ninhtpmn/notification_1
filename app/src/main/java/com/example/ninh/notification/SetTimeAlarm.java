package com.example.ninh.notification;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class SetTimeAlarm extends ActionBarActivity {

    public static final String FORMAT = "HH-mm-ss dd-MM-yyyy";
    public EditText year, month, day, hour, minute, second, repeat;
    public static final String ID = "ID";
    public static final String SECOND = "second";

    private DBHelper mydb;
    private int id;
    private int alarm_year, alarm_month, alarm_day, alarm_hour, alarm_minute, alarm_second;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_time_alarm);

        mydb = new DBHelper(this);

        year = (EditText) findViewById(R.id.year);
        month = (EditText) findViewById(R.id.month);
        day = (EditText) findViewById(R.id.day);

        hour = (EditText) findViewById(R.id.hour);
        minute = (EditText) findViewById(R.id.minute);
        second = (EditText) findViewById(R.id.second);

        repeat = (EditText)findViewById(R.id.repeat);

        Bundle extras = getIntent().getExtras();
        id = extras.getInt("ID");


        if(id!=0)  //show old details for update
        {
                getSupportActionBar().setTitle(getString(R.string.update_time_alarm));
                ItemTime item = mydb.getItemTime(id);

                double time = item.getTime();
                int rep = (int)item.getRepeat();

                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis((long)time);

                day.setText(""+cal.get(Calendar.DAY_OF_MONTH));
                month.setText(""+(cal.get(Calendar.MONTH)+1));
                year.setText(""+cal.get(Calendar.YEAR));

                hour.setText(""+cal.get(Calendar.HOUR_OF_DAY));
                minute.setText(""+cal.get(Calendar.MINUTE));
                second.setText(""+cal.get(Calendar.SECOND));


                if(rep == -1) repeat.setHint(getString(R.string.no));
              else
                repeat.setText(""+rep);
        }


    }


    public void setAlarm(View v) {

        int alarm_repeat = 0; // =-1: no repeat
        if(repeat.getText().toString().equals("")) alarm_repeat = -1;
        else {
            try {
                alarm_repeat = Integer.parseInt(repeat.getText().toString());
                if (alarm_repeat < 1)
                    Toast.makeText(this, getString(R.string.repeat_p), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this, getString(R.string.repeat_must_number), Toast.LENGTH_LONG).show();
            }
        }
        Date date = null;
        try {
            alarm_year = Integer.parseInt(year.getText().toString());
            alarm_month = Integer.parseInt(month.getText().toString());
            alarm_day = Integer.parseInt(day.getText().toString());

            alarm_hour = Integer.parseInt(hour.getText().toString());
            alarm_minute = Integer.parseInt(minute.getText().toString());
            alarm_second = Integer.parseInt(second.getText().toString());

            SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
            sdf.setLenient(false);
            date = sdf.parse(alarm_hour+"-"+alarm_minute+"-"+alarm_second+" "+alarm_day+"-"+alarm_month+"-"+alarm_year);
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.wrong_datetime), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        if(date!=null && alarm_repeat!=0) {
            System.out.println(date.getTime());

            long time = date.getTime();

            Log.i("Calender", "\n" + time);
            Intent intentAlarm = new Intent(this, AlarmReceiver.class);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            intentAlarm.putExtra(SECOND, alarm_repeat);


            int idIntent;

            if (id == 0) {    // if create new
                idIntent = setIDPendingIntent();
                AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(SetTimeAlarm.this, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), idIntent, intent, 0);
                am.cancel(pendingIntent);
                pendingIntent.cancel();
                mydb.insertTime(idIntent, (double) time, (double) alarm_repeat);
            } else {         // if update
                idIntent = mydb.getItemTime(id).getidIntent();
                AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(SetTimeAlarm.this, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), idIntent, intent, 0);
                am.cancel(pendingIntent);
                pendingIntent.cancel();

                mydb.updateTime(id, idIntent, (double) time, (double) alarm_repeat);
            }
            intentAlarm.putExtra(ID, idIntent);

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                alarmManager.set(AlarmManager.RTC_WAKEUP, time, PendingIntent.getBroadcast(this, idIntent, intentAlarm, 0));
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, PendingIntent.getBroadcast(this, idIntent, intentAlarm, 0));
            }

            Toast.makeText(this, getString(R.string.set_alarm_successful), Toast.LENGTH_LONG).show();

            Log.i("SMSSEND", "DK,FL11111s");

            onBackPressed();
        }
    }

    private int setIDPendingIntent() {
        ArrayList arrID = mydb.getListidIntentTimeAlarm();
        if(arrID.isEmpty()) return 0;
        int re = -1;
        for(int i=0; i<arrID.size()+1; i++)
        {
            if(!arrID.contains(i))
            {
                re = i;
                break;
            }
        }
        return re;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
