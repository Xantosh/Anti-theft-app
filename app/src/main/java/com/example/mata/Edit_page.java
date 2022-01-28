package com.example.mata;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class Edit_page extends AppCompatActivity {

    EditText edit_name,edit_emnumber1,edit_emnumber2;
    TextView edit_code;
    ImageButton generate_edit_code;
    Button save;
    FirebaseDatabase rootNode1;
    DatabaseReference reference1;
    String password,edit_number,m_Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_page);


        edit_name=findViewById(R.id.edit_name);
        edit_code=findViewById(R.id.edit_generate);
        edit_emnumber1=findViewById(R.id.edit_emnumber1);
        edit_emnumber2=findViewById(R.id.edit_emnumber2);
        save=findViewById(R.id.save);
        generate_edit_code=findViewById(R.id.generate_edit_code);

        SharedPreferences sp=getApplicationContext().getSharedPreferences("MyUserData", Context.MODE_PRIVATE);

        edit_name.setText(sp.getString("name",""));
        edit_number=sp.getString("number","");
        edit_code.setText(sp.getString("code",""));
        password=sp.getString("password","");
        edit_emnumber1.setText(sp.getString("emn1",""));
        edit_emnumber2.setText(sp.getString("emn2",""));
        
        String check_name=edit_name.getText().toString();
        String check_code=edit_code.getText().toString();
        String check_emnumber1=edit_emnumber1.getText().toString();
        String check_emnumber2=edit_emnumber2.getText().toString();

        generate_edit_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random(); // Random is used to generate random code
                int value = random.nextInt(10000 - 1000) + 1000; // fmla to generate 4 digit code(max - min)+ min
                edit_code.setText(Integer.toString(value)); // first changed to string then stored in the text view

            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String store_editname = edit_name.getText().toString();
                String store_editnumber = edit_number;
                String store_pass = password;
                String store_editemnumber1 = edit_emnumber1.getText().toString();
                String store_editemnumber2 = edit_emnumber2.getText().toString();
                String store_editcode = edit_code.getText().toString();

                if (store_editcode.isEmpty() || store_editemnumber2.isEmpty() || store_editemnumber1.isEmpty() || store_editname.isEmpty() || store_editnumber.isEmpty()) {
                    Toast.makeText(Edit_page.this, "Fill the empty field", Toast.LENGTH_SHORT).show();
                }
                
                else if (store_editname.equals(check_name) && store_editcode.equals(check_code) && store_editemnumber1.equals(check_emnumber1) && store_editemnumber2.equals(check_emnumber2)){
                    Toast.makeText(Edit_page.this, "No Information Is Edited", Toast.LENGTH_SHORT).show();
                }

                else if(store_editnumber==store_editemnumber1 || store_editnumber==store_editemnumber2 || store_editemnumber1==store_editemnumber2){
                    Toast.makeText(Edit_page.this, "Enter different numbers", Toast.LENGTH_SHORT).show();
                }

                else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Edit_page.this);
                    builder.setTitle("Enter Your Password");

// Set up the input
                    final EditText input = new EditText(Edit_page.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    builder.setView(input);

// Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            m_Text = input.getText().toString();
                            if (m_Text.equals(password)){
                                Toast.makeText(Edit_page.this, "Password Verified", Toast.LENGTH_SHORT).show();
                                rootNode1 = FirebaseDatabase.getInstance();
                                reference1 = rootNode1.getReference("data");
                                helperclass_edit_database helperClass_edit = new  helperclass_edit_database(store_editname, store_editnumber,store_pass,store_editcode, store_editemnumber1, store_editemnumber2);
                                reference1.child(edit_number).setValue(helperClass_edit);

                                //for tablemode
                                // for shared preference setting true
                                SharedPreferences.Editor editor=getSharedPreferences("save_table_state",MODE_PRIVATE).edit();
                                editor.putBoolean("value4",false);
                                editor.apply();
                                // shared preference for setting true
                                //stopping background service
                                stopService(new Intent(Edit_page.this,intent_filter_table_mode.class));
                                stopService(new Intent(Edit_page.this,table_mode_trigger.class));
                                stopService(new Intent(Edit_page.this,table_service.class));
                                // service stopped

                                //for disabling the broadcast receiver from manifest file
                                PackageManager pm  = Edit_page.this.getPackageManager();
                                ComponentName componentName = new ComponentName(Edit_page.this, restart_check.class);
                                pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                                        PackageManager.DONT_KILL_APP);
//                    //disabled

                                //  for emergency mode

                                // for shared preference setting false
                                SharedPreferences.Editor editor1=getSharedPreferences("save_emergency_state",MODE_PRIVATE).edit();
                                editor1.putBoolean("value1",false);
                                editor1.apply();

                                // shared preference for setting false

                                PackageManager pm1  = Edit_page.this.getPackageManager();
                                ComponentName componentName1 = new ComponentName(Edit_page.this, restart_check_emergency.class);
                                pm1.setComponentEnabledSetting(componentName1,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                                        PackageManager.DONT_KILL_APP);

                                stopService(new Intent(Edit_page.this,emergencytrigerringservice.class));
                                stopService(new Intent(Edit_page.this,GPSServiceemergency.class));

                                //disabled

                                // for reboot mode

                                SharedPreferences.Editor editor2=getSharedPreferences("save_reboot_state",MODE_PRIVATE).edit();
                                editor2.putBoolean("value3",false);
                                editor2.apply();
                                // shared preference for setting true

                                PackageManager pm2  = Edit_page.this.getPackageManager();
                                ComponentName componentName2 = new ComponentName(Edit_page.this, restart_check_reboot.class);
                                pm2.setComponentEnabledSetting(componentName2,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                                        PackageManager.DONT_KILL_APP);

                                //stopping background service

                                stopService(new Intent(Edit_page.this,prevent_reboot.class));
                                // service stopped

                                //for sms mode

                                SharedPreferences.Editor editor3=getSharedPreferences("save_sms_state",MODE_PRIVATE).edit();
                                editor3.putBoolean("value2",false);
                                editor3.apply();
                                // shared preference for setting false

                                //stopping background service

                                stopService(new Intent(Edit_page.this,smsmode.class));
                                stopService(new Intent(Edit_page.this,GPSService.class));

                                // service stopped

                                //for disabling the broadcast receiver from manifest file
                                PackageManager pm3  = Edit_page.this.getPackageManager();
                                ComponentName componentName3 = new ComponentName(Edit_page.this, smsmode_trigger.class);
                                pm3.setComponentEnabledSetting(componentName3,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                                        PackageManager.DONT_KILL_APP);
                                //disabled

                                Intent intent= new Intent(Edit_page.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                                shared shared= new shared(getApplicationContext());
                                // setting for the logout
                                shared.log_out();

                            }

                            else if (m_Text.isEmpty()){
                                Toast.makeText(Edit_page.this, "Password cannot be Empty", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(Edit_page.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }


            }
        });


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}