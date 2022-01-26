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
import android.os.CountDownTimer;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.util.concurrent.TimeUnit;

public class smsmode extends Service {
    // packages
    MediaPlayer player;
    AudioManager audioManager;

    // initialization







    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        createNotificationChannel();
        Intent intent1=new Intent(smsmode.this,home_screen.class);

        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent1,0);
        Notification notification= new NotificationCompat.Builder(this,"ChannelId1").setContentTitle("smsmode").setContentText("smsmode is running").setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pendingIntent).build();
        startForeground(1,notification);

        String number=intent.getStringExtra("sender_no");
        String message= intent.getStringExtra("message");
        //setting the name
        String codes=intent.getStringExtra("send_code");
        String code=codes;
        String gps="This is my gps location";
        String gps_cmp=code.concat("locate");
        String ring_cmp=code.concat("ring");
        String call_cmp=code.concat("call");

        Toast.makeText(this, number + message, Toast.LENGTH_SHORT).show();
//        String received= message;

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();


        // checking the condition
        if (message.equals(gps_cmp)){
            send_gps(number);

            // function to send gps location;
        }
        else if (message.equals(call_cmp)){
            Log.e("place","into else function");
            cal(number);


        }
        else if (message.equals(ring_cmp)){

            ring();
            // function to ring the phone
        }


        return START_NOT_STICKY;
    }

    private void cal(String number) {
        Log.e("place","callfunction");
        Intent service1 = new Intent(this, calling_for_sms_mode.class);
        service1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // code to send gps
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            service1.putExtra("sms_no",number);
            Log.e("place","foreground");
            Toast.makeText(this, "reached if", Toast.LENGTH_SHORT).show();
            // starting background service for android>= android O
            this.startForegroundService(service1);

        }
        else {
            service1.putExtra("sms_no",number);
            Toast.makeText(this, "reached else", Toast.LENGTH_SHORT).show();
            this.startService(service1);
            // starting background service for android<= android O
//            stopService(new Intent(smsmode.this,GPSService.class));
//            startService(new Intent(smsmode.this,GPSService.class));
            // service started
        }

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

    public void send_gps(String number) {
        Toast.makeText(this, number, Toast.LENGTH_SHORT).show();
        Intent service1 = new Intent(this, GPSService.class);
        service1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // code to send gps
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            service1.putExtra("locate_no",number);
            Toast.makeText(this, "reached if", Toast.LENGTH_SHORT).show();
            // starting background service for android>= android O
            this.startForegroundService(service1);
//            stopService(new Intent(smsmode.this,GPSService.class));
//            startForegroundService(new Intent(smsmode.this,GPSService.class));
            //service started
        }
        else {
            service1.putExtra("locate_no",number);
            Toast.makeText(this, "reached else", Toast.LENGTH_SHORT).show();
            this.startService(service1);
            // starting background service for android<= android O
//            stopService(new Intent(smsmode.this,GPSService.class));
//            startService(new Intent(smsmode.this,GPSService.class));
            // service started
        }



    }

    public void ring() {
        Toast.makeText(this, "ring", Toast.LENGTH_SHORT).show();
        // code to ring
        player= MediaPlayer.create(smsmode.this, Settings.System.DEFAULT_RINGTONE_URI);
        audioManager= (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,100,AudioManager.FLAG_PLAY_SOUND);
        player.setLooping(true);
        player.start();

        long duration= TimeUnit.SECONDS.toMillis(10); // 5 is 5 second
        new CountDownTimer(duration, 1000) // timer for 5sec
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