package com.example.mata;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView click;
    Button signin;
    private long pressedTime;

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

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,registration.class);
                startActivity(intent);


            }
        });

        signin= findViewById(R.id.signin);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,home_screen.class);
                startActivity(intent);
                finish();
            }
        });

        

    }


    }