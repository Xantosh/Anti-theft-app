package com.example.mata;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;

public class GPSServiceemergency extends Service implements LocationListener {


    boolean isGPSEnable = false;
    boolean isNetworkEnable = false;
    double latitude,longitude;
    LocationManager locationManager;
    Location location;
    private Handler mHandler = new Handler();
    private Timer mTimer = null;
    long notify_interval = 1000;
    SmsManager smsManager = SmsManager.getDefault();
    public static String str_receiver = "servicetutorial.service.receiver";
    Intent intent;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        createNotificationChannel();
        Intent intent4=new Intent(GPSServiceemergency.this,home_screen.class);

        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent4,0);
        Notification notification= new NotificationCompat.Builder(this,"ChannelId1").setContentTitle("TableView").setContentText("TableView is running").setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pendingIntent).build();
        startForeground(1,notification);

        Log.e("place","reached gpsservice startcommand");
        String number1= intent.getStringExtra("sending_no1");
        String number2=intent.getStringExtra("sending_no2");

        Toast.makeText(this, number1, Toast.LENGTH_SHORT).show();


        mTimer = new Timer();
        mTimer.schedule(new TimerTaskToGetLocation(),5,notify_interval);
        Intent intent1 = new Intent(str_receiver);
        fn_getlocation(number1,number2);
        Toast.makeText(this, "reached on start gps", Toast.LENGTH_SHORT).show();





        return START_STICKY;
    }

    private void createNotificationChannel() {


        //check the version
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel= new NotificationChannel(
                    "ChannelId1","foreground notification", NotificationManager.IMPORTANCE_NONE);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);

        }
    }

//    @Override
//    public void onCreate() {
//        String test= intent.getStringExtra("locate_no");
//        Toast.makeText(this, test, Toast.LENGTH_SHORT).show();
//        super.onCreate();
//
//        String number="9865762048";
//
//        mTimer = new Timer();
//        mTimer.schedule(new TimerTaskToGetLocation(),5,notify_interval);
//        intent = new Intent(str_receiver);
//       fn_getlocation(number);
//    }

    @Override
    public void onLocationChanged(Location location) {


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {


    }

    @Override
    public void onProviderDisabled(String provider) {


    }

    @SuppressLint("MissingPermission")
    private void fn_getlocation(String messaging1, String messaging2){

        locationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnable && !isNetworkEnable){

        }else {

            if (isNetworkEnable){
                location = null;
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,100,0,this);
                if (locationManager!=null){
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location!=null){

                        Log.e("latitude",location.getLatitude()+"");
                        Log.e("longitude",location.getLongitude()+"");
                        Toast.makeText(this, "latitude"+location.getLatitude(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, "longitude"+location.getLongitude(), Toast.LENGTH_SHORT).show();

                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        String lat="This is my location: "+ "https://maps.google.com/?q="+latitude+","+ longitude;
                        smsManager.sendTextMessage(messaging1, null, lat, null, null);
                        smsManager.sendTextMessage(messaging2, null, lat, null, null);


                        stopForeground(true);
                        stopSelf();

//                        fn_update(location);
                    }
                }

            }


            else if (isGPSEnable){
                location = null;
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,100,0,this);
                if (locationManager!=null){
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location!=null){
                        Log.e("latitude",location.getLatitude()+"");
                        Log.e("longitude",location.getLongitude()+"");
                        Toast.makeText(this, "latitude"+location.getLatitude(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, "longitude"+location.getLongitude(), Toast.LENGTH_SHORT).show();
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        String lat="This is my location: "+ "https://maps.google.com/?q="+latitude+","+ longitude;
                        smsManager.sendTextMessage(messaging1, null, lat, null, null);
                        smsManager.sendTextMessage(messaging2, null, lat, null, null);
                        stopForeground(true);
                        stopSelf();
//                        fn_update(location);
                    }
                }
            }


        }

    }

    private class TimerTaskToGetLocation extends TimerTask {
        @Override
        public void run() {

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    // fn_getlocation();
                }
            });

        }
    }

//    private void fn_update(Location location){
//
//        intent.putExtra("latitude",location.getLatitude()+"");
//        intent.putExtra("longitude",location.getLongitude()+"");
//        sendBroadcast(intent);
//    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        stopSelf();
        super.onDestroy();
    }
}