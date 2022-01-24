package com.example.mata;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class table_mode_trigger extends Service implements SensorEventListener {

    Float xaccel,yaccel,zaccel;
    Float xprevaccel,yprevaccel,zprevaccel;
    Boolean firstupdate=true;
    Float shakethreshold = 1f;
    Boolean ShakeInitiated=false;
    Sensor accleometer;
   int stopping_loop=0;
    SensorManager sm;



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("state","Service");
        createnotification();

        Intent intent1=new Intent(table_mode_trigger.this,home_screen.class);

        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent1,0);
        Notification notification= new NotificationCompat.Builder(this,"ChannelId1").setContentTitle("motion_detect").setContentText("TableView is running").setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pendingIntent).build();
        startForeground(1,notification);

        sm= (SensorManager) getSystemService(SENSOR_SERVICE);
        accleometer= sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this,accleometer,SensorManager.SENSOR_DELAY_NORMAL);

        return START_STICKY;
    }

    private void createnotification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel= new NotificationChannel(
                    "ChannelId1","foreground notification", NotificationManager.IMPORTANCE_LOW);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Log.e("state","something changing");
        updateaccelparamter(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
        if((!ShakeInitiated) && isaccelchanged())
        {
            ShakeInitiated=true;


        }
        else if ((ShakeInitiated) && isaccelchanged())
        {
            executeaction();

        }
        else if((ShakeInitiated) && !isaccelchanged())
        {
            ShakeInitiated=false;
        }


    }
    private Boolean isaccelchanged()
    {
        Float deltaX=Math.abs(xprevaccel-xaccel);
        Float deltaY=Math.abs(yprevaccel-yaccel);
        Float deltaZ=Math.abs(zprevaccel-zaccel);

        return (deltaX>shakethreshold && deltaY>shakethreshold)

                ||(deltaX>shakethreshold && deltaZ>shakethreshold)||
                (deltaZ>shakethreshold && deltaY>shakethreshold);
    }

    private void executeaction()
    {
        Intent ii =new Intent(this,table_service.class);
        ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        stopping_loop++;
        if (stopping_loop==1){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Toast.makeText(this, "reached if", Toast.LENGTH_SHORT).show();
            // starting background service for android>= android O
            this.startForegroundService(ii);
            //service started
        }
        else {
            Toast.makeText(this, "reached else", Toast.LENGTH_SHORT).show();
            this.startService(ii);
            // service started
        }}

    }
    private void updateaccelparamter(Float xnewaceel , Float ynewaccel, Float znewaccel)
    {
        if (firstupdate)
        {
            xprevaccel=xnewaceel;
            yprevaccel=ynewaccel;
            zprevaccel=znewaccel;
            firstupdate=false;

        }
        else
        {
            xprevaccel=xaccel;
            yprevaccel=yaccel;
            zprevaccel=zaccel;

        }
        xaccel=xnewaceel;
        yaccel=ynewaccel;
        zaccel=znewaccel;

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        sm.unregisterListener(this, accleometer);
        ShakeInitiated =false;
            Log.e("state","stop_service");
        stopService(new Intent(table_mode_trigger.this,table_service.class));
        stopping_loop=0;
        stopForeground(true);
        stopSelf();
    }
}