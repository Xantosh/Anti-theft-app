package com.example.mata;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.telephony.SmsManager;

import androidx.core.app.NotificationCompat;

public class smsmode extends Service {
    // packages
    MediaPlayer player;
    AudioManager audioManager;

    // initialization
    String send_no="9865762048";
    String gps="This is my gps location";
    String received_msg="locate";
    String gps_cmp="locate";
    String ring_cmp="ring";
    String call_cmp="call";
    String code="1234";



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        createNotificationChannel();

        Intent intent1=new Intent(smsmode.this,home_screen.class);

        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent1,0);
        Notification notification= new NotificationCompat.Builder(this,"ChannelId1").setContentTitle("TableView").setContentText("TableView is running").setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pendingIntent).build();
        startForeground(1,notification);

        // checking the condition
        if (received_msg==(gps_cmp)){
            send_gps();

            // function to send gps location;
        }
        else if (received_msg==(new String(code+call_cmp))){
            call();
            // code to call
        }
        else if (received_msg==ring_cmp){
            ring();
            // function to ring the phone
        }


        return START_STICKY;
    }

    // functions




    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel= new NotificationChannel(
                    "ChannelId1","foreground notification", NotificationManager.IMPORTANCE_LOW);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);

        }
    }

    private void send_gps() {
        // code to send gps
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // starting background service for android>= android O
            startForegroundService(new Intent(smsmode.this,GPSService.class));
            //service started
        }
        else {
            // starting background service for android<= android O
            startService(new Intent(smsmode.this,GPSService.class));
            // service started
        }



    }

    private void ring() {
        // code to ring
        player= MediaPlayer.create(smsmode.this, Settings.System.DEFAULT_RINGTONE_URI);
        audioManager= (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,100,AudioManager.FLAG_PLAY_SOUND);
        player.setLooping(true);
        player.start();
    }

    private void call() {

        // code to call
        Intent intent2= new Intent(Intent.ACTION_CALL);
        intent2.setData(Uri.parse("tel: "+ send_no));
        startActivity(intent2);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
        stopForeground(true);
        stopSelf();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }
}