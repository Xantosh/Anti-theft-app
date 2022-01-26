package com.example.mata;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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