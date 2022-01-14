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
        Intent service1 = new Intent(context, stopsystemdialogservice.class);
        service1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

         if(intent.getAction().equals(Intent.ACTION_USER_PRESENT)){ // used to test if the device is unlocked
            Log.e("LOB","userpresent");
            Log.e("LOB","wasScreenOn"+wasScreenOn);

            context.stopService(service1);

        }
         else if((!intent.getAction().equals(Intent.ACTION_USER_PRESENT) ) && (intent.getAction().equals(Intent.ACTION_SCREEN_ON))){
             wasScreenOn=true;
             Log.e("LOB","wasScreenOn"+wasScreenOn);

             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                 //for latest version of android
                 context.startForegroundService(service1);
             }
             else {
//                for older version of android (before O)
                 context.startService(service1);
                 //}
             }

         }
    }
}