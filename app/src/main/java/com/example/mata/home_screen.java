package com.example.mata;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

public class home_screen extends AppCompatActivity {


   CardView tablemode,emergencymode,smsmode,rebootmode,allenablemode;
   ImageView profile;
    private long pressedTime;
   Switch tablemode_switch,emergencymode_switch,smsmode_switch,rebootmode_switch;
    int t=0,e=0,va=0,r=0,a=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_screen);
        final LocationManager manager = (LocationManager) getSystemService(this.LOCATION_SERVICE);



        //Requesting permissions
        int PERMISSION_ALL = 1;


        String[] PERMISSIONS = {
                android.Manifest.permission.CALL_PHONE,
                android.Manifest.permission.SEND_SMS, android.Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                android.Manifest.permission. ACCESS_COARSE_LOCATION, Manifest.permission. ACCESS_FINE_LOCATION,
                android.Manifest.permission.RECEIVE_SMS
        };
        //checking the permission
        if (!hasPermissions(this, PERMISSIONS)) {

            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        // checking done

       tablemode= findViewById(R.id.tablemode);

      emergencymode= findViewById(R.id.emergencymode);

       smsmode= findViewById(R.id.smsmode);

       rebootmode= findViewById(R.id.rebootmode);

        tablemode_switch= findViewById(R.id.tablemode_switch);

        emergencymode_switch= findViewById(R.id.emergencymode_switch);

        smsmode_switch= findViewById(R.id.smsmode_switch);

        rebootmode_switch= findViewById(R.id.rebootmode_switch);

        profile=findViewById(R.id.profile);








        // button to go to profile page

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1= new Intent(home_screen.this,profilepage.class);
                startActivity(intent1);
            }
        });
        // profile screen completed


        tablemode.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {

               t++;
                    if(t==1){
                        tablemode_switch.setChecked(true);


                }
                else if(t==2 || t==3){
                        t=0;
                    tablemode_switch.setChecked(false);

//
                }
            }
        });

        tablemode_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean IsChecked) {
                if (IsChecked){

                    //for enabling the broadcast receiver from manifest file

                    PackageManager pm  = home_screen.this.getPackageManager();
                    ComponentName componentName = new ComponentName(home_screen.this, restart_check.class);
                    pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                            PackageManager.DONT_KILL_APP);

                    //enabled

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        // starting background service for android>= android O
                        startForegroundService(new Intent(home_screen.this,table_service.class));
                        //service started
                    }
                    else {
                        // starting background service for android<= android O
                        startService(new Intent(home_screen.this,table_service.class));
                        // service started
                    }
                }

                else {
                    //stopping background service

                    stopService(new Intent(home_screen.this,table_service.class));
                    // service stopped

                    //for disabling the broadcast receiver from manifest file
                    PackageManager pm  = home_screen.this.getPackageManager();
                    ComponentName componentName = new ComponentName(home_screen.this, restart_check.class);
                    pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                            PackageManager.DONT_KILL_APP);
//                    //disabled
//                    //also mention every manually build classes,services and reciever in manifest file in android manifest file;
//

                }

            }
        });

        // table mode ended


//      for emergency mode
       emergencymode.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


                e++;
               if(e==1){
                    emergencymode_switch.setChecked(true);


                }
                else if(e==2 || e==3){
                   emergencymode_switch.setChecked(false);

                    e=0;
                    }
            }
        });


        emergencymode_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean IsChecked) {
                if (IsChecked){

                    // to check the location is on or not
                    if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        // starting background service for android>= android O
                        startForegroundService(new Intent(home_screen.this,emergencytrigerringservice.class));
                        //service started
                    }
                    else {
                        // starting background service for android<= android O
                        startService(new Intent(home_screen.this,emergencytrigerringservice.class));
                        // service started
                    }


                }
                else {

                    stopService(new Intent(home_screen.this,emergencytrigerringservice.class));
                    stopService(new Intent(home_screen.this,GPSServiceemergency.class));

                }

            }
        });
        // emergency mode ended
//
//        rebootmode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                r++;
//                if(r==1){
//                    rebootmode_switch.setChecked(true);
//
//
//                    //for enabling the broadcast receiver from manifest file
//
//                    PackageManager pm  = home_screen.this.getPackageManager();
//                    ComponentName componentName = new ComponentName(home_screen.this, restart_check_reboot.class);
//                    pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//                            PackageManager.DONT_KILL_APP);
//
//                    //enabled
//
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        // starting background service for android>= android O
//                        startForegroundService(new Intent(home_screen.this,prevent_reboot.class));
//                        //service started
//                    }
//                    else {
//                        // starting background service for android<= android O
//                        startService(new Intent(home_screen.this,prevent_reboot.class));
//                        // service started
//                    }
//                }
//                else if(r==2 || r==3){
//                    rebootmode_switch.setChecked(false);
//                    r=0;
//
//                    //stopping background service
//
//                    stopService(new Intent(home_screen.this,prevent_reboot.class));
//                    // service stopped
//
//                    //for disabling the broadcast receiver from manifest file
//                    PackageManager pm  = home_screen.this.getPackageManager();
//                    ComponentName componentName = new ComponentName(home_screen.this, restart_check_reboot.class);
//                    pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                            PackageManager.DONT_KILL_APP);
////                    //disabled
//                }
//            }
//        });

        // smsmode started

        smsmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                va++;
                if(va==1){
                    smsmode_switch.setChecked(true);

                }
                else if(va==2 || va==3){
                    smsmode_switch.setChecked(false);
                    va=0;
                }
            }
        });

        smsmode_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean IsChecked) {
                if (IsChecked){

                    // to check the location is on or not
                    if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                    }
                    // location checked

                    //for enabling the broadcast receiver from manifest file

                    PackageManager pm  = home_screen.this.getPackageManager();
                    ComponentName componentName = new ComponentName(home_screen.this, smsmode_trigger.class);
                    pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                          PackageManager.DONT_KILL_APP);

                    //enabled


//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        // starting background service for android>= android O
//                        startForegroundService(new Intent(home_screen.this,smsmode.class));
//                        //service started
//                    }
//                    else {
//                        // starting background service for android<= android O
//                        startService(new Intent(home_screen.this,smsmode.class));
//                        // service started
//                    }
                }

                else {
                    //stopping background service

                    stopService(new Intent(home_screen.this,smsmode.class));
                    stopService(new Intent(home_screen.this,GPSService.class));

                    // service stopped

                    //for disabling the broadcast receiver from manifest file
                    PackageManager pm1  = home_screen.this.getPackageManager();
                    ComponentName componentName = new ComponentName(home_screen.this, smsmode_trigger.class);
                    pm1.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                            PackageManager.DONT_KILL_APP);
                    //disabled
                    //also mention every manually build classes,services and reciever in manifest file in android manifest file;


                }

            }
        });

        // smsmode ended

//        allenablemode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                a++;
//                if(a==1){
//                    allenablemodecolor.setBackgroundResource(R.drawable.gradient_start);
//                    emergencymodecolor.setBackgroundResource(R.drawable.gradient_start);
//                    tablemodecolor.setBackgroundResource(R.drawable.gradient_start);
//                    vehiclemodecolor.setBackgroundResource(R.drawable.gradient_start);
//                    rebootmodecolor.setBackgroundResource(R.drawable.gradient_start);
//                    t++;e++;va++;r++;
//                }
//                else if(a==2){
//                    allenablemodecolor.setBackgroundResource(R.drawable.gradient);
//                    tablemodecolor.setBackgroundResource(R.drawable.gradient);
//                    emergencymodecolor.setBackgroundResource(R.drawable.gradient);
//                    rebootmodecolor.setBackgroundResource(R.drawable.gradient);
//                    vehiclemodecolor.setBackgroundResource(R.drawable.gradient);
//                    a=t=e=va=r=0;
//                }
//            }
//        });


    }
        // boolean for function
    private boolean hasPermissions(home_screen home_screen, String[] permissions) {

        if (home_screen != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(home_screen, permission) != PackageManager.PERMISSION_GRANTED) {

                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }


}