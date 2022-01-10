package com.example.mata;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class prevent_reboot extends Service{
    final BroadcastReceiver mReceiver = new preventrebootreceiver();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        createNotificationChannel();

        Intent intent1=new Intent(prevent_reboot.this,home_screen.class);

        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent1,0);
        Notification notification= new NotificationCompat.Builder(this,"ChannelId1").setContentTitle("preventreboot").setContentText("TableView is running").setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pendingIntent).build();
        startForeground(1,notification);
        //starting of service;
        final IntentFilter filter = new IntentFilter(Intent.ACTION_USER_PRESENT);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(mReceiver, filter);
        return START_STICKY;


    }

    private void createNotificationChannel() {
        //check the version
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel= new NotificationChannel(
                    "ChannelId1","foreground notification", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
    }

    public class LocalBinder extends Binder {
        prevent_reboot getService() {
            return prevent_reboot.this;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        unregisterReceiver(mReceiver);
        stopSelf();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
