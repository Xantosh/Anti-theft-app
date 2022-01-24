package com.example.mata;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

public class home_screen extends AppCompatActivity {


   CardView tablemode,emergencymode,smsmode,rebootmode,allenablemode;
   ImageView profile;
    private long pressedTime;
   Switch tablemode_switch,emergencymode_switch,smsmode_switch,rebootmode_switch;
    int t=0,e=0,va=0,r=0,a=0;



    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shared shared= new shared(getApplicationContext());
        // checking for the first time
        shared.first_time();

        setContentView(R.layout.activity_home_screen);
        final LocationManager manager = (LocationManager) getSystemService(this.LOCATION_SERVICE);



        //Requesting permissions
        int PERMISSION_ALL = 1;


        String[] PERMISSIONS = {
                Manifest.permission.CALL_PHONE,
                Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                Manifest.permission. ACCESS_COARSE_LOCATION, Manifest.permission. ACCESS_FINE_LOCATION,
                Manifest.permission.RECEIVE_SMS, Manifest.permission.PROCESS_OUTGOING_CALLS,Manifest.permission.READ_PHONE_STATE,
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

        // shared preferences for saving state of buttons
        SharedPreferences sharedPreferences1=getSharedPreferences("save_emergency_state",MODE_PRIVATE);
        emergencymode_switch.setChecked(sharedPreferences1.getBoolean("value1",false));

        SharedPreferences sharedPreferences2=getSharedPreferences("save_sms_state",MODE_PRIVATE);
        smsmode_switch.setChecked(sharedPreferences2.getBoolean("value2",false));

        SharedPreferences sharedPreferences3=getSharedPreferences("save_reboot_state",MODE_PRIVATE);
        rebootmode_switch.setChecked(sharedPreferences3.getBoolean("value3",false));

        SharedPreferences sharedPreferences4=getSharedPreferences("save_table_state",MODE_PRIVATE);
        tablemode_switch.setChecked(sharedPreferences4.getBoolean("value4",false));

        SharedPreferences sharedPreferences5=getSharedPreferences("save_table_value",MODE_PRIVATE);
        sharedPreferences5.getInt("value10",t);

        SharedPreferences sharedPreferences6=getSharedPreferences("save_emergency_value",MODE_PRIVATE);
        sharedPreferences6.getInt("value6",e);

        SharedPreferences sharedPreferences7=getSharedPreferences("save_sms_value",MODE_PRIVATE);
        sharedPreferences7.getInt("value7",va);

        SharedPreferences sharedPreferences8=getSharedPreferences("save_reboot_value",MODE_PRIVATE);
        sharedPreferences8.getInt("value8",r);





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
                        SharedPreferences.Editor editor=getSharedPreferences("save_table_value",MODE_PRIVATE).edit();
                        editor.putInt("value10",t);
                        editor.apply();

                        tablemode_switch.setChecked(true);


                }
                else if(t==2 || t==3){
                        t=0;
                        SharedPreferences.Editor editor=getSharedPreferences("save_table_value",MODE_PRIVATE).edit();
                        editor.putInt("value10",t);
                        editor.apply();

                        tablemode_switch.setChecked(false);

//
                }
            }
        });

        tablemode_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean IsChecked) {
                if (IsChecked){

                    // for shared preference setting true
                    SharedPreferences.Editor editor=getSharedPreferences("save_table_state",MODE_PRIVATE).edit();
                    editor.putBoolean("value4",true);
                    editor.apply();
                    tablemode_switch.setChecked(true);
                    // shared preference for setting true

                    //for enabling the broadcast receiver from manifest file

                    PackageManager pm  = home_screen.this.getPackageManager();
                    ComponentName componentName = new ComponentName(home_screen.this, restart_check.class);
                    pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                            PackageManager.DONT_KILL_APP);

                    //enabled

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        // starting foreground service for android>= android O
                        startForegroundService(new Intent(home_screen.this,table_mode_trigger.class));
                        startForegroundService(new Intent(home_screen.this,intent_filter_table_mode.class));
                        //service started
                    }
                    else {
                        // starting background service for android<= android O
                        startService(new Intent(home_screen.this,table_mode_trigger.class));
                        startService(new Intent(home_screen.this,intent_filter_table_mode.class));
                        // service started
                    }
                }

                else {

                    // for shared preference setting true
                    SharedPreferences.Editor editor=getSharedPreferences("save_table_state",MODE_PRIVATE).edit();
                    editor.putBoolean("value4",false);
                    editor.apply();
                    tablemode_switch.setChecked(false);
                    // shared preference for setting true
                    //stopping background service
                    stopService(new Intent(home_screen.this,intent_filter_table_mode.class));
                    stopService(new Intent(home_screen.this,table_mode_trigger.class));
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
                   SharedPreferences.Editor editor=getSharedPreferences("save_emergency_value",MODE_PRIVATE).edit();
                   editor.putInt("value6",e);
                   editor.apply();
                    emergencymode_switch.setChecked(true);


                }
                else if(e==2 || e==3){
                   emergencymode_switch.setChecked(false);
                    e=0;
                   SharedPreferences.Editor editor=getSharedPreferences("save_emergency_value",MODE_PRIVATE).edit();
                   editor.putInt("value6",e);
                   editor.apply();
                    }
            }
        });


        emergencymode_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean IsChecked) {
                if (IsChecked){

                    // to check the location is on or not
                    if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                    }
                    // for shared preference setting true
                    SharedPreferences.Editor editor=getSharedPreferences("save_emergency_state",MODE_PRIVATE).edit();
                    editor.putBoolean("value1",true);
                    editor.apply();
                    emergencymode_switch.setChecked(true);
                    // shared preference for setting true
                    PackageManager pm  = home_screen.this.getPackageManager();
                    ComponentName componentName = new ComponentName(home_screen.this, restart_check_emergency.class);
                    pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                            PackageManager.DONT_KILL_APP);

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

                    // for shared preference setting false
                    SharedPreferences.Editor editor=getSharedPreferences("save_emergency_state",MODE_PRIVATE).edit();
                    editor.putBoolean("value1",false);
                    editor.apply();
                    emergencymode_switch.setChecked(false);
                    // shared preference for setting false

                    PackageManager pm  = home_screen.this.getPackageManager();
                    ComponentName componentName = new ComponentName(home_screen.this, restart_check_emergency.class);
                    pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                            PackageManager.DONT_KILL_APP);

                    stopService(new Intent(home_screen.this,emergencytrigerringservice.class));
                    stopService(new Intent(home_screen.this,GPSServiceemergency.class));

                }

            }
        });
        // emergency mode ended

        // reboot mode started
//
        rebootmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                r++;
                if(r==1){
                    SharedPreferences.Editor editor=getSharedPreferences("save_reboot_value",MODE_PRIVATE).edit();
                    editor.putInt("value8",r);
                    editor.apply();
                    rebootmode_switch.setChecked(true);

                }

                else if(r==2 || r==3){
                    r=0;
                    SharedPreferences.Editor editor=getSharedPreferences("save_reboot_value",MODE_PRIVATE).edit();
                    editor.putInt("value8",r);
                    editor.apply();
                    rebootmode_switch.setChecked(false);



                }
            }
        });

        rebootmode_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean IsChecked) {
                if (IsChecked){

                    // for shared preference setting true
                    SharedPreferences.Editor editor=getSharedPreferences("save_reboot_state",MODE_PRIVATE).edit();
                    editor.putBoolean("value3",true);
                    editor.apply();
                    rebootmode_switch.setChecked(true);
                    // shared preference for setting true

                    PackageManager pm  = home_screen.this.getPackageManager();
                    ComponentName componentName = new ComponentName(home_screen.this, restart_check_reboot.class);
                    pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                            PackageManager.DONT_KILL_APP);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        // starting background service for android>= android O
                        startForegroundService(new Intent(home_screen.this,prevent_reboot.class));
                        //service started
                    }
                    else {
                        // starting background service for android<= android O
                        startService(new Intent(home_screen.this,prevent_reboot.class));
                        // service started
                    }


                }
                else {

                    // for shared preference setting true
                    SharedPreferences.Editor editor=getSharedPreferences("save_reboot_state",MODE_PRIVATE).edit();
                    editor.putBoolean("value3",false);
                    editor.apply();
                    rebootmode_switch.setChecked(false);
                    // shared preference for setting true

                    PackageManager pm  = home_screen.this.getPackageManager();
                    ComponentName componentName = new ComponentName(home_screen.this, restart_check_reboot.class);
                    pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                            PackageManager.DONT_KILL_APP);

                    //stopping background service

                    stopService(new Intent(home_screen.this,prevent_reboot.class));
                    // service stopped

                }

            }
        });



        // smsmode started

        smsmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                va++;
                if(va==1){
                    SharedPreferences.Editor editor=getSharedPreferences("save_sms_value",MODE_PRIVATE).edit();
                    editor.putInt("value7",va);
                    editor.apply();
                    smsmode_switch.setChecked(true);

                }
                else if(va==2 || va==3){

                    va=0;
                    SharedPreferences.Editor editor=getSharedPreferences("save_sms_value",MODE_PRIVATE).edit();
                    editor.putInt("value7",va);
                    editor.apply();
                    smsmode_switch.setChecked(false);
                }
            }
        });

        smsmode_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean IsChecked) {
                if (IsChecked){

                    // to check the location is on or not
                    if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                    }
                    // location checked

                    // for shared preference setting true
                    SharedPreferences.Editor editor=getSharedPreferences("save_sms_state",MODE_PRIVATE).edit();
                    editor.putBoolean("value2",true);
                    editor.apply();
                    smsmode_switch.setChecked(true);
                    // shared preference for setting true

                    //for enabling the broadcast receiver from manifest file

                    PackageManager pm  = home_screen.this.getPackageManager();
                    ComponentName componentName = new ComponentName(home_screen.this, smsmode_trigger.class);
                    pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                          PackageManager.DONT_KILL_APP);

                }

                else {
                    // for shared preference setting false
                    SharedPreferences.Editor editor=getSharedPreferences("save_sms_state",MODE_PRIVATE).edit();
                    editor.putBoolean("value2",false);
                    editor.apply();
                    smsmode_switch.setChecked(false);
                    // shared preference for setting false

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
//
//                    t++;e++;va++;r++;
//                }
//                else if(a==2){
//
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