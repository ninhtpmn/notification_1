package com.example.ninh.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ninh on 26/03/2015.
 */
public class ServiceThread extends Thread{

    public static final String TITLE= "title";
    public static final String SUBJECT = "subject";
    public static final String BODY = "body";

    private GetLocation gps;
    private Context context;
    private DBHelper mydb;
    private int id;
    public ServiceThread(MyService myService) {
        this.context = myService;
        mydb = new DBHelper(context);
    }

    public void run() {

        Looper.prepare();

        gps = new GetLocation(context);

        while(!interrupted()) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            try {

                    gps.getLocation();

                if(gps.canGetLocation()) {
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    Log.i("LATLON", latitude + ";" + longitude);

                    if(isTagetLocation(latitude, longitude))
                    {
                        String title = context.getString(R.string.title);
                        String subject = context.getString(R.string.subject);
                        String body = context.getString(R.string.body);
                        NotificationManager NM=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                        Notification notify=new Notification(android.R.drawable.stat_notify_more,title,System.currentTimeMillis());

                        Intent notificationIntent = new Intent(context, Popup.class);

                        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP  | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                        notificationIntent.putExtra(TITLE,title);
                        notificationIntent.putExtra(SUBJECT, subject);
                        notificationIntent.putExtra(BODY,body);

                        PendingIntent pending=PendingIntent.getActivity(context,0, notificationIntent,
                                PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_UPDATE_CURRENT);

                        notify.setLatestEventInfo(context,subject,body,pending);
                        notify.flags |= Notification.FLAG_AUTO_CANCEL;
                        NM.notify(0, notify);

                        context.stopService(new Intent(context, MyService.class));

                        mydb.deleteLocationAlarm(id);

                        this.interrupt();

                    }
                }

            } catch (Throwable t) {
                Log.i("TASK121", "Throwable caughtkkkkkkkkkkkkkkkkkkkkk: "+ t.getMessage(), t);
            }

        }

    }

    private boolean isTagetLocation(double latitude, double longitude) {

        ArrayList<ItemLocation> list = mydb.getListLocationAlarm();

        for(int i=0; i<list.size(); i++) {
            Log.i("SHPRE_LOC", list.get(i).getLat() + ";" + list.get(i).getLng());
            if ((Math.abs(list.get(i).getLat() -  latitude) < 0.001) && (Math.abs(list.get(i).getLng() -  longitude) < 0.001))
            {
                id = list.get(i).getID();
                return true;
            }
        }
        return false;
    }


}
