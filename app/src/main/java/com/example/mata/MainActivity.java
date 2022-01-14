package com.example.mata;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView click;
    Button signin;
    private long pressedTime;
    boolean b=false;
    EditText login_email,login_password;


    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            // since the task are in stack so we have to make new task to exit the application  or else we will be re-directed to the activity in stack
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        click= findViewById(R.id.registeration);
        login_email  = (EditText) findViewById(R.id.login_email);
        login_password = (EditText) findViewById(R.id.login_password);
        signin= findViewById(R.id.signin);

        // for one time login

        SharedPreferences sharedPreferences1=getSharedPreferences("one_time_login",MODE_PRIVATE);
        sharedPreferences1.getBoolean("value1",false);

        if (sharedPreferences1.equals(true)){
            Intent intent= new Intent(MainActivity.this,home_screen.class);
            startActivity(intent);
            finish();


        }

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,registration.class);
                startActivity(intent);


            }
        });



        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (login_email.getText().toString().isEmpty() || login_password.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter in empty field", Toast.LENGTH_SHORT).show();
                }
               else if (login_email.getText().toString().equals("9865762048") && login_password.getText().toString().equals("user")){
                    SharedPreferences.Editor editor=getSharedPreferences("one_time_login",MODE_PRIVATE).edit();
                    editor.putBoolean("value1",true);
                    editor.apply();
                Intent intent= new Intent(MainActivity.this,home_screen.class);
                startActivity(intent);
                finish();}
                else {
                    Toast.makeText(MainActivity.this, "Not Valid Login", Toast.LENGTH_SHORT).show();
                }
            }
        });

        

    }


    }