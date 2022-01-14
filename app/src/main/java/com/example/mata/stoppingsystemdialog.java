package com.example.mata;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class stoppingsystemdialog extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("state","reached on receive");

        Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        Log.e("state","reached intentclosedialog");
        context.sendBroadcast(closeDialog);
        Log.e("state","reached sendbroadcast");

    }
}