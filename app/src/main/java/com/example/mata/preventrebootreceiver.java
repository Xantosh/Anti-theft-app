package com.example.mata;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class preventrebootreceiver extends BroadcastReceiver {
    public static boolean wasScreenOn = true;


    @Override
    public void onReceive(Context context, Intent intent) {
       // final IntentFilter filter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);

         if(intent.getAction().equals(Intent.ACTION_USER_PRESENT) ){ // used to test if the device is unlocked
            Log.e("LOB","userpresent");
            Log.e("LOB","wasScreenOn"+wasScreenOn);
             Intent service1 = new Intent(context, stopsystemdialogservice.class);
             service1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.stopService(service1);
        }
         else if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
             wasScreenOn=false;
             Log.e("LOB","wasScreenOn"+wasScreenOn);

             Intent service2 = new Intent(context, stopsystemdialogservice.class);
             service2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                 //for latest version of android
                 context.startForegroundService(service2);
             }
             else {
//                for older version of android (before O)
                 context.startService(service2);
                 //}
             }

         }

        if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)){ // used to test if the screen is on
            Log.e("state ","screen on");
            context.sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));

        }
    }
}