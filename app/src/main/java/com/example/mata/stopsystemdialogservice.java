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
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class stopsystemdialogservice extends Service {
    final BroadcastReceiver mReceiver = new stoppingsystemdialog();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        createNotificationChannel();
        Intent intent1=new Intent(stopsystemdialogservice.this,home_screen.class);

        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent1,0);
        Notification notification= new NotificationCompat.Builder(this,"ChannelId1").setContentTitle("stopdialog").setContentText("TableView is running").setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pendingIntent).build();
        startForeground(1,notification);
        //starting of service;
       // Toast.makeText(this, "service started", Toast.LENGTH_SHORT).show();
        Log.e("lob","service started stop dialog service");
        Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        Log.e("state","reached intentclosedialog");
        this.sendBroadcast(closeDialog);
        intent.setAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        final IntentFilter filter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(mReceiver, filter);
        return START_STICKY;
    }

    private void createNotificationChannel() {
        //check the version
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel= new NotificationChannel(
                    "ChannelId1","foreground notification", NotificationManager.IMPORTANCE_NONE);
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