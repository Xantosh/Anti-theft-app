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

import androidx.core.app.NotificationCompat;

public class emergencytrigerringservice extends Service {
    final BroadcastReceiver mReceiver = new emergency_trigger();
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createnotification();

        Intent intent1=new Intent(emergencytrigerringservice.this,home_screen.class);

        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent1,0);
        Notification notification= new NotificationCompat.Builder(this,"ChannelId1").setContentTitle("emergencyview").setContentText("TableView is running").setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pendingIntent).build();
        startForeground(1,notification);
        final IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(mReceiver, filter);
        return START_STICKY;
    }

    private void createnotification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel= new NotificationChannel(
                    "ChannelId1","foreground notification", NotificationManager.IMPORTANCE_NONE);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);

        }
    }

    public class LocalBinder extends Binder {
        emergencytrigerringservice getService() {
            return emergencytrigerringservice.this;
        }
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        stopSelf();
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}