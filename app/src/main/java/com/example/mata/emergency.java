package com.example.mata;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class emergency extends Service {

    //public String p ="9865762048";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();

        Intent intent1=new Intent(emergency.this,MainActivity.class);
        Log.e("place","reached emergency startcommand");

        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent1,0);
        Notification notification= new NotificationCompat.Builder(this,"ChannelId1").setContentTitle("EMERGENCYVIEW").setContentText("Emergencymode is running").setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pendingIntent).build();
        startForeground(1,notification);
        Toast.makeText(this, "Service class", Toast.LENGTH_SHORT).show();

        SharedPreferences sp=getApplicationContext().getSharedPreferences("MyUserData", Context.MODE_PRIVATE);

        String phone1=sp.getString("emn1","");
        String phone2=sp.getString("emn2","");

        sms(phone1,phone2);
        call(phone1);
        return START_STICKY;
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
    public IBinder onBind(Intent intent) { return null; }

    private void sms(String senderNo1, String senderNo2) {
        Log.e("place","reached emergency sms");
        Intent service = new Intent(this, GPSServiceemergency.class);
        service.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.e("place","foregroundggps");
            //for latest version of android
            service.putExtra("sending_no1",senderNo1);
            service.putExtra("sending_no2",senderNo2);

            this.startForegroundService(service);
        }
        else {
            Log.e("place","backgroundgps");
//                for older version of android (before O)
            service.putExtra("sending_no1",senderNo1);
            service.putExtra("sending_no2",senderNo2);
            this.startService(service);
            //}
        }
    }

    private void call(String p){
        Intent service1 = new Intent(this, calling_for_emergency_mode.class);
        service1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // code to send gps
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            service1.putExtra("emergency_no",p);
            Log.e("place","foreground");
            Toast.makeText(this, "reached if", Toast.LENGTH_SHORT).show();
            // starting background service for android>= android O
            this.startForegroundService(service1);

        }
        else {
            service1.putExtra("emergency_no",p);
            Toast.makeText(this, "reached else", Toast.LENGTH_SHORT).show();
            this.startService(service1);
            // starting background service for android<= android O
//            stopService(new Intent(smsmode.this,GPSService.class));
//            startService(new Intent(smsmode.this,GPSService.class));
            // service started
        }


    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        stopSelf();
    }
}