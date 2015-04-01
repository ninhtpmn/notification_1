package com.example.ninh.notification;

/**
 * Created by NINH_CNTTK55 on 3/10/2015.
 */

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyArrayAdapter extends ArrayAdapter<ItemLocation>{

    private Activity context=null;
    private ArrayList<ItemLocation> myArray;
    private int layoutId;
    private DBHelper mydb;

    public MyArrayAdapter(Activity context, int layoutId, ArrayList<ItemLocation> Array) {
        super(context, layoutId, Array);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.layoutId = layoutId;
        this.myArray = Array;
        mydb = new DBHelper(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(layoutId, null);
            convertView.setMinimumHeight(78);
        }

        final TextView stt=(TextView) convertView.findViewById(R.id.id);
        final TextView lat=(TextView) convertView.findViewById(R.id.lat);
        final TextView lng=(TextView) convertView.findViewById(R.id.lng);


        int stt12 = position+1;
        stt.setText(String.valueOf(stt12));
        lat.setText(String.valueOf(myArray.get(position).getLat()));
        lng.setText(String.valueOf(myArray.get(position).getLng()));



        if (position%2==0)
        {
            convertView.setBackgroundColor(Color.parseColor("#CFCFCF"));
        }

        mydb.close();

        return convertView;
    }

}
