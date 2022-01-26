package com.example.mata;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class registration extends AppCompatActivity {
    TextView sign,generate;
    Button signup;
    ImageButton generate_code;
    EditText name,number,password,repassword,emnumber1,emnumber2;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        sign = findViewById(R.id.sign);
        signup = findViewById(R.id.signup);
        generate_code = findViewById(R.id.generate_code);
        name = findViewById(R.id.name);
        number = findViewById(R.id.number);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        generate = findViewById(R.id.generate);
        emnumber1 = findViewById(R.id.emnumber1);
        emnumber2 = findViewById(R.id.emnumber2);

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(registration.this, MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });
        // to generate a random code from the app itself
        generate_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random(); // Random is used to generate random code
                int value = random.nextInt(10000 - 1000) + 1000; // fmla to generate 4 digit code(max - min)+ min
                generate.setText(Integer.toString(value)); // first changed to string then stored in the text view

            }
        });

        // finished generating the code


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String store_name = name.getText().toString();
                String store_number = number.getText().toString();
                String store_pass = password.getText().toString();
                String check_repass = repassword.getText().toString();
                String store_emnumber1 = emnumber1.getText().toString();
                String store_emnumber2 = emnumber2.getText().toString();
                String store_code = generate.getText().toString();

                if (store_code.isEmpty() || store_emnumber2.isEmpty() || store_emnumber1.isEmpty() || store_name.isEmpty() || store_number.isEmpty() || store_pass.isEmpty() || check_repass.isEmpty()) {
                    Toast.makeText(registration.this, "Fill the empty field", Toast.LENGTH_SHORT).show();
                } else if (!store_pass.equals(check_repass)) {
                    Toast.makeText(registration.this, "Password doesn't match", Toast.LENGTH_SHORT).show();

                }
                else if (store_number.length()!=10 || store_emnumber1.length()!=10 || store_emnumber2.length()!=10){
                    Toast.makeText(registration.this, "Enter valid number", Toast.LENGTH_SHORT).show();
                }
                else if(store_number==store_emnumber1 || store_number==store_emnumber2 || store_emnumber1==store_emnumber2){
                    Toast.makeText(registration.this, "Enter different numbers", Toast.LENGTH_SHORT).show();
                }
                
                else {
                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference("data");
                    helperclass_database helperClass = new helperclass_database(store_name, store_number, store_pass, store_code, store_emnumber1, store_emnumber2);
                    reference.child(store_number).setValue(helperClass);
                    Intent intent2 = new Intent(registration.this, MainActivity.class);
                    startActivity(intent2);
                    finish();
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