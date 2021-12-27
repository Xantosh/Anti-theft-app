package com.example.mata;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;




public class restart_check_reboot extends BroadcastReceiver {

    static int n=0;

    private static int countPowerOff = 0;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF))
        { n++;

            if (n==1){
                Toast.makeText(context, "time started", Toast.LENGTH_SHORT).show();

                long duration= TimeUnit.SECONDS.toMillis(5); // 5 is 5 second
                new CountDownTimer(duration, 1000) // timer for 5sec
                {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    // process after completing the counter
                    public void onFinish() {
                        n=0;
                        Toast.makeText(context, "time over", Toast.LENGTH_SHORT).show();

                    }
                }.start();
            }
            if (n==5){
                Toast.makeText(context, "task started", Toast.LENGTH_SHORT).show();
                Intent service = new Intent(context, emergency.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    //for latest version of android
                    context.startForegroundService(service);
                }
                else {
//                for older version of android (before O)
                    context.startService(service);

                }
            }
            else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON))
            {

                n++;
                if (n==1){
                    Toast.makeText(context, "time started", Toast.LENGTH_SHORT).show();

                    long duration= TimeUnit.SECONDS.toMillis(5); // 5 is 5 second
                    new CountDownTimer(duration, 1000) // timer for 5sec
                    {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        // process after completing the counter
                        public void onFinish() {
                            n=0;
                            Toast.makeText(context, "time over", Toast.LENGTH_SHORT).show();

                        }
                    }.start();
                }
                if (n==5){
                    Toast.makeText(context, "task started", Toast.LENGTH_SHORT).show();
                    Log.e("In on receive", "In Method:  task started");


//                Intent service = new Intent(context, emergency.class);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    //for latest version of android
//                    context.startForegroundService(service);
//                }
//                else {
////                for older version of android (before O)
//                    context.startService(service);

                }
            }

            Log.e("In on receive", "In Method:  ACTION_SCREEN_ON");
        }
        else if(intent.getAction().equals(Intent.ACTION_USER_PRESENT))
        {
            Log.e("In on receive", "In Method:  ACTION_USER_PRESENT");
            if (countPowerOff > 2)
            {
                countPowerOff=0;
                Toast.makeText(context, "MAIN ACTIVITY IS BEING CALLED ", Toast.LENGTH_LONG).show();
                Intent i = new Intent(context, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);
            }
        }
    }


}