package com.example.mata;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class emergency_trigger extends BroadcastReceiver {

  static  int Count=0;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            Count++;
            Toast.makeText(context, "onnn", Toast.LENGTH_SHORT).show();
            if (Count==1){
                long duration= TimeUnit.SECONDS.toMillis(5); // 5 is 5 second
                new CountDownTimer(duration, 10000) // timer for 5sec
                {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    // process after completing the counter
                    public void onFinish() {
                        Count=0;
                        Toast.makeText(context, "time up", Toast.LENGTH_SHORT).show();

                    }
                }.start();

            }

            if(Count==5){
                //Send SMS code..
                Intent service = new Intent(context, emergency.class);
                Toast.makeText(context, "activity started", Toast.LENGTH_SHORT).show();
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


        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            //This is for screen ON option.
        }

    }
}