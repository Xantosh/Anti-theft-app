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
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class emergency extends Service {

    public String p ="9865762048";
//    SmsManager smsManager = SmsManager.getDefault();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();

        Intent intent1=new Intent(emergency.this,MainActivity.class);

        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent1,0);
        Notification notification= new NotificationCompat.Builder(this,"ChannelId1").setContentTitle("TableView").setContentText("TableView is running").setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pendingIntent).build();
        startForeground(1,notification);
        Toast.makeText(this, "Service class", Toast.LENGTH_SHORT).show();

        sms(p);
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

    private void sms(String senderNo) {
        Intent service = new Intent(this, GPSServiceemergency.class);
        service.addFlags(Intent.FLAG_FROM_BACKGROUND);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //for latest version of android
            service.putExtra("sending_no",senderNo);
            this.startForegroundService(service);
        }
        else {
//                for older version of android (before O)
            service.putExtra("sending_no",senderNo);
            this.startService(service);
            //}
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