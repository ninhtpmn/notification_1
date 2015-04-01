package com.example.ninh.notification;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by ninh on 02/04/2015.
 */
public class TimeArrayAdapter extends ArrayAdapter<ItemTime> {
    public static final String FORMAT = "dd:MM:yy  HH:mm:ss";
    public static final String COLOR = "#CFCFCF";

    private Activity context=null;
    private ArrayList<ItemTime> myArray;
    private int layoutId;
    private DBHelper mydb;

    public TimeArrayAdapter(Activity context, int layoutId, ArrayList<ItemTime> Array) {
        super(context, layoutId, Array);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.layoutId = layoutId;
        this.myArray = Array;
        mydb = new DBHelper(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater=context.getLayoutInflater();
        convertView=inflater.inflate(layoutId, null);
        convertView.setMinimumHeight(78);
        final TextView stt=(TextView) convertView.findViewById(R.id.id);
        final TextView idintent=(TextView) convertView.findViewById(R.id.idintent);
        final TextView time=(TextView) convertView.findViewById(R.id.time);


        int stt12 = position+1;
        stt.setText(String.valueOf(stt12));
        idintent.setText(String.valueOf(myArray.get(position).getidIntent()));

        DateFormat df = new SimpleDateFormat(FORMAT);

//        System.out.println("Milliseconds to Date: " + df.format(myArray.get(position).getTime()));

        time.setText(df.format(myArray.get(position).getTime()));



        if (position%2==0)
        {
            convertView.setBackgroundColor(Color.parseColor(COLOR));
        }

        mydb.close();

        return convertView;
    }
}
