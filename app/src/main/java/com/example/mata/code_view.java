package com.example.mata;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class code_view extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView code;
        setContentView(R.layout.activity_code_view);

        code=findViewById(R.id.code);


        SharedPreferences sp=getApplicationContext().getSharedPreferences("MyUserData", Context.MODE_PRIVATE);

        String full_code=sp.getString("code","");

        //setting the name
        code.setText(full_code);
    }
}