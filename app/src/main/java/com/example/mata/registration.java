package com.example.mata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class registration extends AppCompatActivity {
    TextView sign;
    Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        sign= findViewById(R.id.sign);
        signup= findViewById(R.id.signup);

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1= new Intent(registration.this,MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(registration.this,MainActivity.class);
                startActivity(intent2);
                finish();

            }
        });
    }
}