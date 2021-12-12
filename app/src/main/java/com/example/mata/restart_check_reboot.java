package com.example.mata;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class restart_check_reboot extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
        Intent service = new Intent(context, prevent_reboot.class);
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
