package com.example.mata;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class table_service extends Service {
     MediaPlayer player;
     AudioManager audioManager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //notification channel for foreground process
        createNotificationChannel();

        Intent intent1=new Intent(table_service.this,home_screen.class);

        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent1,0);
        Notification notification= new NotificationCompat.Builder(this,"ChannelId1").setContentTitle("TableView").setContentText("TableView is running").setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pendingIntent).build();
        startForeground(1,notification);

        //starting of service;
        player= MediaPlayer.create(table_service.this, Settings.System.DEFAULT_RINGTONE_URI);
        audioManager= (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,100,AudioManager.FLAG_PLAY_SOUND);


        player.setLooping(true);
        player.start();

        return START_STICKY;


    }



    private void createNotificationChannel() {
        //check the version
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel= new NotificationChannel(
                    "ChannelId1","foreground notification", NotificationManager.IMPORTANCE_LOW);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);

        }
    }

    @Override
    public void onDestroy() {
        //stopping the service;
        super.onDestroy();
        stopForeground(true);
        stopSelf();
       player.stop();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
