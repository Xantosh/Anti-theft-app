package com.example.mata;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

// service for getting the location and messaging

import androidx.annotation.Nullable;

public class messaging_location extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
