package com.example.ninh.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by ninh on 16/03/2015.
 */
public class AlarmReceiver extends BroadcastReceiver{
    NotificationManager NM;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Log.i("SMSSEND", "DK,FL");

        String title = context.getString(R.string.title);
        String subject = context.getString(R.string.subject);
        String body = context.getString(R.string.body);
        NM=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify=new Notification(android.R.drawable.stat_notify_more,title,System.currentTimeMillis());

        Intent notificationIntent = new Intent(context, Popup.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP  | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        notificationIntent.putExtra("title",title);
        notificationIntent.putExtra("subject", subject);
        notificationIntent.putExtra("body",body);

        PendingIntent pending=PendingIntent.getActivity(context,0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_UPDATE_CURRENT);

        notify.setLatestEventInfo(context,subject,body,pending);
        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        NM.notify(0, notify);


    }


}
