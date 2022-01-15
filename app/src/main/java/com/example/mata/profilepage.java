package com.example.mata;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class profilepage extends AppCompatActivity {
    // defining the views
    CardView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilepage);
        //linking with the views
        logout=findViewById(R.id.logout);

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


    }
}