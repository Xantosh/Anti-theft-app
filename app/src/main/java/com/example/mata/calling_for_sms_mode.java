package com.example.mata;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class calling_for_sms_mode extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("place","onstartcallsms");
        createNotificationChannel();
        Intent intent1=new Intent(calling_for_sms_mode.this,home_screen.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent1,0);
        Notification notification= new NotificationCompat.Builder(this,"ChannelId1").setContentTitle("calling feature").setContentText("smsmode is running").setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pendingIntent).build();
        startForeground(1,notification);
        String number=intent.getStringExtra("sms_no");
        calling(number);



        return START_STICKY;
    }

    public void calling(String number) {
        Log.e("place","callingservice calling function");
        Intent intent2= new Intent(Intent.ACTION_CALL);
        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent2.addFlags(Intent.FLAG_FROM_BACKGROUND);
        intent2.setData(Uri.parse("tel: "+ number));
        startActivity(intent2);
        stopForeground(true);
        stopSelf();
    }


    private void createNotificationChannel() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel= new NotificationChannel(
                    "ChannelId1","foreground notification", NotificationManager.IMPORTANCE_NONE);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);

        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {

        stopForeground(true);
        stopSelf();
        super.onDestroy();
    }
}