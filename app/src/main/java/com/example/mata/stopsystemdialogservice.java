package com.example.mata;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class stopsystemdialogservice extends Service {
    final BroadcastReceiver mReceiver = new stoppingsystemdialog();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        createNotificationChannel();
        //starting of service;
        Toast.makeText(this, "service started", Toast.LENGTH_SHORT).show();
        Log.e("lob","service started");
        final IntentFilter filter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(mReceiver, filter);
        return super.onStartCommand(intent, flags, startId);


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
        stopsystemdialogservice getService() {
            return stopsystemdialogservice.this;
        }
    }


    @Override
    public void onDestroy() {
        Log.e("LOB","service stopped");

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