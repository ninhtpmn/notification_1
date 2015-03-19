package com.example.ninh.notification;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by NINH_CNTTK55 on 3/9/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDatabase.db";
    public static final String TABLE1_NAME = "timelist";
    public static final String TABLE2_NAME = "locationlist";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_LNG = "lng";


    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table timelist " +"(id integer primary key, time double)"
        );

        db.execSQL(
                "create table locationlist " +"(id integer primary key, lat double, lng double)"
        );

    }

    public void insertTime  (Double time)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put("time", time);

            db.insert("timelist", null, contentValues);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void insertLocation  (Double lat, Double lng)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put("lat", lat);
            contentValues.put("lng", lng);

            db.insert("locationlist", null, contentValues);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void updateTime (Integer id, Double time)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("time", time);

            db.update("timellist", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void updateLocation (Integer id, Double lat, Double lng)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("lat", lat);
            contentValues.put("lng", lng);

            db.update("locationlist", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }



   /* public ArrayList getListTimeAlarm()
    {
        ArrayList array_list = new ArrayList();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("select * from timelist", null);
            res.moveToFirst();
            while (!res.isAfterLast()) {

                array_list.add(
                        res.getInt(res.getColumnIndex(COLUMN_ID))
                                + ". " + res.getString(res.getColumnIndex(COLUMN_TIME)));
                res.moveToNext();
            }

            res.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return array_list;
    }*/


    public ArrayList getListLocationAlarm()
    {
        ArrayList array_list = new ArrayList();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.query("locationlist", null, null, null, null, null, null);
            res.moveToFirst();
            while (!res.isAfterLast()) {

                array_list.add(
                        new ItemLocation(res.getInt(res.getColumnIndex(COLUMN_ID)),
                                res.getDouble(res.getColumnIndex(COLUMN_LAT)),
                                res.getDouble(res.getColumnIndex(COLUMN_LNG)))

                );
                res.moveToNext();
            }

            res.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return array_list;
    }



    public LatLng getLatLng(int id)
    {
        double lat = 0, lng = 0;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.query("locationlist", new String[]{COLUMN_LAT, COLUMN_LNG}, "id = " + id, null, null, null, null);
            res.moveToFirst();
            lat = res.getDouble(res.getColumnIndex(COLUMN_LAT));
            lng = res.getDouble(res.getColumnIndex(COLUMN_LNG));

            res.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new LatLng(lat,lng);
    }



    public void deleteTimeAlarm (Integer id)
    {

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete("timelist", "id = ? ", new String[]{Integer.toString(id)});
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void deleteLocationAlarm (Integer id)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete("locationlist", "id = ? ", new String[]{Integer.toString(id)});
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS timelist");
        db.execSQL("DROP TABLE IF EXISTS locationlist");
        onCreate(db);
    }

}
