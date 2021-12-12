package com.example.mata;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;
// broadcast reciever

public class restart_check extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
//        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
        Toast.makeText(context.getApplicationContext(), "Boot completed", Toast.LENGTH_LONG).show();
            Intent service = new Intent(context, table_service.class);
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
