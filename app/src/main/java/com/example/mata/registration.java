package com.example.mata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class registration extends AppCompatActivity {
    TextView sign,generate;
    Button signup;
    ImageButton generate_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        sign= findViewById(R.id.sign);
        signup= findViewById(R.id.signup);
        generate_code= findViewById(R.id.generate_code);
        generate=findViewById(R.id.generate);

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
            // to generate a random code from the app itself
        generate_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random= new Random(); // Random is used to generate random code
                int value= random.nextInt(10000-1000)+1000; // fmla to generate 4 digit code(max - min)+ min
                generate.setText(Integer.toString(value)); // first changed to string then stored in the text view

            }
        });

        // finished generating the code

    }
}