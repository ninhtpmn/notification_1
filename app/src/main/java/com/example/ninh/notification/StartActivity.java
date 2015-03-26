package com.example.ninh.notification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

/**
 * Created by ninh on 25/03/2015.
 */
public class StartActivity extends ActionBarActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
    }

    public void setTime(View v)
    {
        Intent i = new Intent(this, SetTimeAlarm.class);
        startActivity(i);
    }

    public void setLocation(View v)
    {
        Intent i = new Intent(this, SetLocationAlarm.class);
        startActivity(i);
    }
}
