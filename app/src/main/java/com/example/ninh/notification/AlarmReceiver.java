package com.example.ninh.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by ninh on 16/03/2015.
 */
public class AlarmReceiver extends BroadcastReceiver{

    public static final String TITLE= "title";
    public static final String SUBJECT = "subject";
    public static final String BODY = "body";
    public static final String ID = "ID";
    public static final String SECOND = "second";
    public static final String INTENT_FT = "android.intent.action.time";
    private MediaPlayer mp;
    private NotificationManager NM;
    @Override
    public void onReceive(Context context, Intent data) {
        // TODO Auto-generated method stub
        Log.i("SMSSEND", "DK,FL"+data.getIntExtra(SECOND,0));

        DBHelper mydb = new DBHelper(context);
        mp = MediaPlayer.create(context, R.raw.jingle_bells_sms);
        int idIntent = data.getIntExtra(ID,0);
        int second = data.getIntExtra(SECOND,0);
        //        // set alarm to start service

        if(second!=-1) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTimeInMillis(System.currentTimeMillis());
//
            Calendar cal = new GregorianCalendar();
//        cal.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR));
//        cal.set(Calendar.HOUR_OF_DAY,  calendar.get(Calendar.HOUR_OF_DAY));
//
//        // start service after an hour
//        cal.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
            cal.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + second);
//        cal.set(Calendar.MILLISECOND, calendar.get(Calendar.MILLISECOND)+100);
//        cal.set(Calendar.DATE, calendar.get(Calendar.DATE));
//        cal.set(Calendar.MONTH, calendar.get(Calendar.MONTH));

            // set alarm to start service again after receiving broadcast
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, AlarmReceiver.class);
            intent.putExtra(ID, idIntent);
            intent.putExtra(SECOND, second);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, idIntent, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            am.cancel(pendingIntent);
//        pendingIntent.cancel();

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
            } else {
                am.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
            }
        }

        else {
            mydb.deleteTimeAlarm(mydb.getIDTimeAl(idIntent));

            Intent i = new Intent(INTENT_FT);
            context.sendBroadcast(i);
        }

        String title = context.getString(R.string.title);
        String subject = context.getString(R.string.subject);
        String body = context.getString(R.string.body);
        NM=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify=new Notification(android.R.drawable.stat_notify_more,title,System.currentTimeMillis());

        Intent notificationIntent = new Intent(context, Popup.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP  | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        notificationIntent.putExtra(TITLE,title);
        notificationIntent.putExtra(SUBJECT, subject);
        notificationIntent.putExtra(BODY,body);

        PendingIntent pending=PendingIntent.getActivity(context,idIntent, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_UPDATE_CURRENT);

        notify.setLatestEventInfo(context,subject,body,pending);
        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        NM.notify(idIntent, notify);

        mp.start();

    }


}
