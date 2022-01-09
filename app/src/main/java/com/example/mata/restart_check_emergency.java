package com.example.mata;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

public class restart_check_emergency extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Boot complete emergency", Toast.LENGTH_SHORT).show();
        Intent service = new Intent(context, emergencytrigerringservice.class);
        service.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //for latest version of android
            context.startForegroundService(service);
        }
        else {
//                for older version of android (before O)
            context.startService(service);
        }
    }
}