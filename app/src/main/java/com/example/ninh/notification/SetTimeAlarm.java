package com.example.ninh.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SetTimeAlarm extends ActionBarActivity {

    public static final String FORMAT = "HH-mm-ss dd-MM-yyyy";

    private EditText year, month, day, hour, minute, second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_time_alarm);

        year = (EditText) findViewById(R.id.year);
        month = (EditText) findViewById(R.id.month);
        day = (EditText) findViewById(R.id.day);

        hour = (EditText) findViewById(R.id.hour);
        minute = (EditText) findViewById(R.id.minute);
        second = (EditText) findViewById(R.id.second);

    }

    public void setAlarm(View v) throws ParseException {

        int alarm_year = Integer.parseInt(year.getText().toString());
        int alarm_month = Integer.parseInt(month.getText().toString());
        int alarm_day = Integer.parseInt(day.getText().toString());

        int alarm_hour = Integer.parseInt(hour.getText().toString());
        int alarm_minute = Integer.parseInt(minute.getText().toString());
        int alarm_second = Integer.parseInt(second.getText().toString());

        Date date = new SimpleDateFormat(FORMAT).parse(alarm_hour+"-"+alarm_minute+"-"+alarm_second+" "+alarm_day+"-"+alarm_month+"-"+alarm_year);
        System.out.println(date.getTime());

        long time= date.getTime();

        Log.i("Calender", "\n"+time );
        Intent intentAlarm= new Intent(this, AlarmReceiver.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time , PendingIntent.getBroadcast(this, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
        Toast.makeText(this, getString(R.string.set_alarm_successful), Toast.LENGTH_LONG).show();

        Log.i("SMSSEND", "DK,FL11111s");
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
