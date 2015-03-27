package com.example.ninh.notification;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Created by ninh on 26/03/2015.
 */
public class MyService extends Service{

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ServiceThread st= new ServiceThread(this);
        st.start();
        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
