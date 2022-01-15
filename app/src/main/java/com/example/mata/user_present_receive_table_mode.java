package com.example.mata;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

public class user_present_receive_table_mode extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(Intent.ACTION_USER_PRESENT)){ // used to test if the device is unlocked
            Log.e("LOB","userpresent");
            Toast.makeText(context, "Turn-off table mode.", Toast.LENGTH_SHORT).show();
            Intent service1 = new Intent(context, table_mode_trigger.class);
            service1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


            context.stopService(service1);

        }


       else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){

            Intent service=new Intent(context,table_mode_trigger.class);
            service.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //for latest version of android
                context.startForegroundService(service);
            }
            else {
//                for older version of android (before O)
                context.startService(service);
                //}
            }


        }
    }
}