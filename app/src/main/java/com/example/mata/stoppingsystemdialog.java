package com.example.mata;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class stoppingsystemdialog extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.sendBroadcast(closeDialog);
    }
}