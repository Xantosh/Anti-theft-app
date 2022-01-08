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
import android.os.CountDownTimer;
import android.os.IBinder;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.util.concurrent.TimeUnit;

public class smsmode extends Service {
    // packages
    MediaPlayer player;
    AudioManager audioManager;

    // initialization


    String gps="This is my gps location";
    String gps_cmp="locate";
    String ring_cmp="ring";
    String call_cmp="call";
    String code="1234";



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service", Toast.LENGTH_SHORT).show();

        createNotificationChannel();

        String number=intent.getStringExtra("sender_no");
        String message= intent.getStringExtra("message");

        Toast.makeText(this, number + message, Toast.LENGTH_SHORT).show();



        Intent intent1=new Intent(smsmode.this,home_screen.class);

        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent1,0);
        Notification notification= new NotificationCompat.Builder(this,"ChannelId1").setContentTitle("TableView").setContentText("TableView is running").setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pendingIntent).build();
        startForeground(1,notification);
       String send_no= intent.getStringExtra("sender_no");
       String received_msg= intent.getStringExtra("message");

        // checking the condition
        if (message==(gps_cmp)){
            send_gps();

            // function to send gps location;
        }
        else if (message==call_cmp){
            call(send_no);
            // code to call
        }
        else if (message==ring_cmp){
            ring();
            // function to ring the phone
        }


        return START_STICKY;
    }

    // functions




    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel= new NotificationChannel(
                    "ChannelId1","foreground notification", NotificationManager.IMPORTANCE_NONE);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);

        }
    }

    private void send_gps() {
        // code to send gps
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // starting background service for android>= android O
            stopService(new Intent(smsmode.this,GPSService.class));
            startForegroundService(new Intent(smsmode.this,GPSService.class));
            //service started
        }
        else {
            // starting background service for android<= android O
            stopService(new Intent(smsmode.this,GPSService.class));
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

        long duration= TimeUnit.SECONDS.toMillis(5); // 5 is 5 second
        new CountDownTimer(duration, 10000) // timer for 5sec
        {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            // process after completing the counter
            public void onFinish() {
                player.stop();

            }
        }.start();

    }



    private void call(String send_no) {

        // code to call
        Intent intent2= new Intent(Intent.ACTION_CALL);
        intent2.setData(Uri.parse("tel: "+ send_no));
        startActivity(intent2);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

       stopForeground(true);
        stopSelf();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }
}