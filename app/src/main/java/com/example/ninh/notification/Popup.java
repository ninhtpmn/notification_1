package com.example.ninh.notification;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by ninh on 19/03/2015.
 */
public class Popup extends ActionBarActivity{

    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_SUBJECT = "subject";
    public static final String EXTRA_BODY = "body";

    TextView subject, body;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;

        this.getWindow().setAttributes(params);

        subject = (TextView)findViewById(R.id.subject);
        body = (TextView)findViewById(R.id.body);

        Bundle extras = getIntent().getExtras();
        String title_intent = extras.getString(EXTRA_TITLE);
        String subject_intent = extras.getString(EXTRA_SUBJECT);
        String body_intent = extras.getString(EXTRA_BODY);

        getSupportActionBar().setTitle(title_intent);

        subject.setText(subject_intent);
        body.setText(body_intent);


    }
}
