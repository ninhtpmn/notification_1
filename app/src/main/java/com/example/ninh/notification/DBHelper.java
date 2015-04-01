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

    public static final String TIMELIST_TABLE = "timelist";
    public static final String LOCATION_TABLE = "locationlist";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_IDINTENT = "idintent";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_REPEAT = "repeat";
    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_LNG = "lng";
    public static final String CREATE_TIMELIST_TABLE = "create table timelist " +"(id integer primary key, idintent integer, time double, repeat double)";
    public static final String CREATE_LOCATIONLIST_TABLE = "create table locationlist " +"(id integer primary key, lat double, lng double)";
    public static final String DROP_TIMELIST = "DROP TABLE IF EXISTS timelist";
    public static final String DROP_LOCATIONLIST = "DROP TABLE IF EXISTS locationlist";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TIMELIST_TABLE);
        db.execSQL(CREATE_LOCATIONLIST_TABLE);
    }

    public void insertTime  (int idIntent, Double time, Double repeat)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(COLUMN_IDINTENT, idIntent);
            contentValues.put(COLUMN_TIME, time);
            contentValues.put(COLUMN_REPEAT, repeat);
            db.insert(TIMELIST_TABLE, null, contentValues);


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

            contentValues.put(COLUMN_LAT, lat);
            contentValues.put(COLUMN_LNG, lng);

            db.insert(LOCATION_TABLE, null, contentValues);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void updateTime (Integer id, Integer idIntent, Double time, Double repeat)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(COLUMN_IDINTENT, idIntent);
            contentValues.put(COLUMN_TIME, time);
            contentValues.put(COLUMN_REPEAT, repeat);
            db.update(TIMELIST_TABLE, contentValues, "id = ? ", new String[]{Integer.toString(id)});

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

            contentValues.put(COLUMN_LAT, lat);
            contentValues.put(COLUMN_LNG, lng);

            db.update(LOCATION_TABLE, contentValues, "id = ? ", new String[]{Integer.toString(id)});

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }




     public ArrayList getListTimeAlarm()
    {
        ArrayList array_list = new ArrayList();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.query(TIMELIST_TABLE, null, null, null, null, null, null);
            res.moveToFirst();
            while (!res.isAfterLast()) {

                array_list.add(
                        new ItemTime(res.getInt(res.getColumnIndex(COLUMN_ID)),
                                res.getInt(res.getColumnIndex(COLUMN_IDINTENT)),
                                res.getDouble(res.getColumnIndex(COLUMN_TIME)),
                                res.getDouble(res.getColumnIndex(COLUMN_REPEAT))
                        )
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

    public ItemTime getItemTime(int id)
    {
        int ID = 0, idIntent = 0;
        double time = 0;
        double repeat = 0;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.query(TIMELIST_TABLE, null, "id = " + id, null, null, null, null);
            res.moveToFirst();
            ID= res.getInt(res.getColumnIndex(COLUMN_ID));
            idIntent = res.getInt(res.getColumnIndex(COLUMN_IDINTENT));
            time = res.getDouble(res.getColumnIndex(COLUMN_TIME));
            repeat = res.getDouble(res.getColumnIndex(COLUMN_REPEAT));

            res.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ItemTime(ID,idIntent,time,repeat);
    }

    public ArrayList getListidIntentTimeAlarm()
    {
        ArrayList array_list = new ArrayList();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.query(TIMELIST_TABLE, new String[]{COLUMN_IDINTENT}, null, null, null, null, null);
            res.moveToFirst();
            while (!res.isAfterLast()) {

                array_list.add( res.getInt(res.getColumnIndex(COLUMN_IDINTENT)));
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



    public ArrayList getListLocationAlarm()
    {
        ArrayList array_list = new ArrayList();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.query(LOCATION_TABLE, null, null, null, null, null, null);

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
            Cursor res = db.query(LOCATION_TABLE, new String[]{COLUMN_LAT, COLUMN_LNG}, "id = " + id, null, null, null, null);

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


    public Integer getIDTimeAl(int idIntent) {
        int idi = -1;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.query(TIMELIST_TABLE, new String[]{COLUMN_ID}, "idintent = " + idIntent, null, null, null, null);
            res.moveToFirst();
            idi = res.getInt(res.getColumnIndex(COLUMN_ID));

            res.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return idi;
    }

    public void deleteTimeAlarm (Integer id)
    {

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TIMELIST_TABLE, "id = ? ", new String[]{Integer.toString(id)});

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
            db.delete(LOCATION_TABLE, "id = ? ", new String[]{Integer.toString(id)});

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DROP_TIMELIST);
        db.execSQL(DROP_LOCATIONLIST);
        onCreate(db);
    }


}
