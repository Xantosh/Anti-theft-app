package com.example.mata;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class profilepage extends AppCompatActivity {
    // defining the views
    CardView logout,code_views;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilepage);
        //linking with the views
        logout=findViewById(R.id.logout);
        name=findViewById(R.id.name);
        code_views=findViewById(R.id.code_views);

        SharedPreferences sp=getApplicationContext().getSharedPreferences("MyUserData", Context.MODE_PRIVATE);

        String full_name=sp.getString("name","");

        //setting the name
        name.setText(full_name);



        // name set

        // for logging out
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //for tablemode
                // for shared preference setting true
                SharedPreferences.Editor editor=getSharedPreferences("save_table_state",MODE_PRIVATE).edit();
                editor.putBoolean("value4",false);
                editor.apply();
                // shared preference for setting true
                //stopping background service
                stopService(new Intent(profilepage.this,intent_filter_table_mode.class));
                stopService(new Intent(profilepage.this,table_mode_trigger.class));
                stopService(new Intent(profilepage.this,table_service.class));
                // service stopped

                //for disabling the broadcast receiver from manifest file
                PackageManager pm  = profilepage.this.getPackageManager();
                ComponentName componentName = new ComponentName(profilepage.this, restart_check.class);
                pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                        PackageManager.DONT_KILL_APP);
//                    //disabled

                //  for emergency mode

                // for shared preference setting false
                SharedPreferences.Editor editor1=getSharedPreferences("save_emergency_state",MODE_PRIVATE).edit();
                editor1.putBoolean("value1",false);
                editor1.apply();

                // shared preference for setting false

                PackageManager pm1  = profilepage.this.getPackageManager();
                ComponentName componentName1 = new ComponentName(profilepage.this, restart_check_emergency.class);
                pm1.setComponentEnabledSetting(componentName1,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                        PackageManager.DONT_KILL_APP);

                stopService(new Intent(profilepage.this,emergencytrigerringservice.class));
                stopService(new Intent(profilepage.this,GPSServiceemergency.class));

                //disabled

                // for reboot mode

                SharedPreferences.Editor editor2=getSharedPreferences("save_reboot_state",MODE_PRIVATE).edit();
                editor2.putBoolean("value3",false);
                editor2.apply();
                // shared preference for setting true

                PackageManager pm2  = profilepage.this.getPackageManager();
                ComponentName componentName2 = new ComponentName(profilepage.this, restart_check_reboot.class);
                pm2.setComponentEnabledSetting(componentName2,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                        PackageManager.DONT_KILL_APP);

                //stopping background service

                stopService(new Intent(profilepage.this,prevent_reboot.class));
                // service stopped

                //for sms mode

                SharedPreferences.Editor editor3=getSharedPreferences("save_sms_state",MODE_PRIVATE).edit();
                editor3.putBoolean("value2",false);
                editor3.apply();
                // shared preference for setting false

                //stopping background service

                stopService(new Intent(profilepage.this,smsmode.class));
                stopService(new Intent(profilepage.this,GPSService.class));

                // service stopped

                //for disabling the broadcast receiver from manifest file
                PackageManager pm3  = profilepage.this.getPackageManager();
                ComponentName componentName3 = new ComponentName(profilepage.this, smsmode_trigger.class);
                pm3.setComponentEnabledSetting(componentName3,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                        PackageManager.DONT_KILL_APP);
                //disabled

                Intent intent= new Intent(profilepage.this,MainActivity.class);
                startActivity(intent);
                finish();
                shared shared= new shared(getApplicationContext());
                // setting for the logout
                shared.log_out();

            }
        });
        // finished logging out

        //for code

        code_views.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(profilepage.this,code_view.class);
                startActivity(intent1);
            }
        });


    }
}