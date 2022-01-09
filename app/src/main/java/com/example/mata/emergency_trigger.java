package com.example.mata;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class emergency_trigger extends BroadcastReceiver {
    
    

    int Count=0;

    public static boolean wasScreenOn = true;

    @Override
    public void onReceive(final Context context, final Intent intent) {
        Log.e("LOB","onReceive");
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            // do whatever you need to do here
            wasScreenOn = false;
            Log.e("LOB","wasScreenOn"+wasScreenOn);
            Toast.makeText(context, "screen offe", Toast.LENGTH_SHORT).show();
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            // and do whatever you need to do here
            Count++;
            wasScreenOn = true;
            Log.e("LOB","screen is on"+wasScreenOn);
            Toast.makeText(context, "screen one", Toast.LENGTH_SHORT).show();
            if (Count==1){
                long duration= TimeUnit.SECONDS.toMillis(5); // 5 is 5 second
                new CountDownTimer(duration, 1000) // timer for 5sec
                {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    // process after completing the counter
                    public void onFinish() {

                        Log.e("LOB","timeup");
                            Toast.makeText(context, "time over", Toast.LENGTH_SHORT).show();
                            Count=0;
                    }
                }.start();


            }
            if (Count==3){
                Log.e("LOB","start the task");
                Toast.makeText(context, "start you task", Toast.LENGTH_SHORT).show();
                Count=0;
                Intent service = new Intent(context, emergency.class);
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

//        else if(intent.getAction().equals(Intent.ACTION_USER_PRESENT)){
//            Log.e("LOB","userpresent");
//            Log.e("LOB","wasScreenOn"+wasScreenOn);
//            String url = "http://www.stackoverflow.com";
//            Intent i = new Intent(Intent.ACTION_VIEW);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            i.setData(Uri.parse(url));
//            context.startActivity(i);
//        }
    }
}