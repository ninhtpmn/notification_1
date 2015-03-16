package com.example.ninh.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by ninh on 16/03/2015.
 */
public class AlarmReceiver extends BroadcastReceiver{
    NotificationManager NM;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Log.i("SMSSEND", "DK,FL");

        String title = "Notification";
        String subject = "Chuc mung ban da trung giai";
        String body = "Chuc ban may man lan sau";
        NM=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify=new Notification(android.R.drawable.stat_notify_more,title,System.currentTimeMillis());
        PendingIntent pending=PendingIntent.getActivity(context,0, new Intent(),0);
        notify.setLatestEventInfo(context,subject,body,pending);
        NM.notify(0, notify);
    }


}
