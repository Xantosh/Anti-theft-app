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
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class emergency extends Service {

    public String p ="9865762048";
    SmsManager smsManager = SmsManager.getDefault();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();

        Intent intent1=new Intent(emergency.this,MainActivity.class);

        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent1,0);
        Notification notification= new NotificationCompat.Builder(this,"ChannelId1").setContentTitle("TableView").setContentText("TableView is running").setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pendingIntent).build();
        startForeground(1,notification);
        Toast.makeText(this, "Service class", Toast.LENGTH_SHORT).show();

        sms();
        call();
        return START_NOT_STICKY;
    }

    private void createNotificationChannel() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel= new NotificationChannel(
                    "ChannelId1","foreground notification", NotificationManager.IMPORTANCE_LOW);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);

        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }

    private void sms() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // starting background service for android>= android O
            stopService(new Intent(emergency.this,GPSService.class));
            startForegroundService(new Intent(emergency.this,GPSService.class));
            //service started
        }
        else {
            // starting background service for android<= android O
            stopService(new Intent(emergency.this,GPSService.class));
            startService(new Intent(emergency.this,GPSService.class));
            // service started
        }
    }

    private void call(){
        Intent intent2= new Intent(Intent.ACTION_CALL);
        intent2.setData(Uri.parse("tel: "+ p));
        startActivity(intent2);


    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        stopSelf();
    }
}