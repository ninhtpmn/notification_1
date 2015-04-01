package com.example.ninh.notification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

/**
 * Created by ninh on 04/04/2015.
 */
public class BeforeStart extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.before_start);
    }

    public void setTime(View v)
    {
        Intent i = new Intent(this, ShowTimeAlarm.class);
        startActivity(i);
    }

    public void setLocation(View v)
    {
        Intent i = new Intent(this, ShowLocationAlarm.class);
        startActivity(i);
    }
}
