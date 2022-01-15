package com.example.mata;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

public class calling_for_emergency_mode extends Service {
    public calling_for_emergency_mode() {
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.e("place","onstartcallemergency");
        createNotificationChannel();
        Intent intent1=new Intent(calling_for_emergency_mode.this,home_screen.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent1,0);
        Notification notification= new NotificationCompat.Builder(this,"ChannelId1").setContentTitle("calling feature").setContentText("smsmode is running").setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pendingIntent).build();
        startForeground(1,notification);
        String number=intent.getStringExtra("emergency_no");
        calling(number);



        return START_STICKY;
    }

    private void createNotificationChannel() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel= new NotificationChannel(
                    "ChannelId1","foreground notification", NotificationManager.IMPORTANCE_NONE);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);

        }
    }

    private void calling(String number) {

        Log.e("place","callingservice emeregency calling function");
        Intent intent2= new Intent(Intent.ACTION_CALL);
        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent2.addFlags(Intent.FLAG_FROM_BACKGROUND);
        intent2.setData(Uri.parse("tel: "+ number));

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent2);
        stopForeground(true);
        stopSelf();
    }

    @Override
    public IBinder onBind(Intent intent) {
     return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        stopSelf();

    }
}