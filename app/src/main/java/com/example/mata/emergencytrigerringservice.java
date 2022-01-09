package com.example.mata;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;

public class emergencytrigerringservice extends Service {
    final BroadcastReceiver mReceiver = new emergency_trigger();
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
        final IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(mReceiver, filter);
        return super.onStartCommand(intent, flags, startId);
    }
    public class LocalBinder extends Binder {
        emergencytrigerringservice getService() {
            return emergencytrigerringservice.this;
        }
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        stopSelf();
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}