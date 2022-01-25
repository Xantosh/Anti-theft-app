package com.example.mata;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

        sign= findViewById(R.id.sign);
        signup= findViewById(R.id.signup);
        generate_code= findViewById(R.id.generate_code);
        name= findViewById(R.id.name);
        number= findViewById(R.id.number);
        password= findViewById(R.id.password);
        repassword= findViewById(R.id.repassword);
        generate=findViewById(R.id.generate);
        emnumber1= findViewById(R.id.emnumber1);
        emnumber2= findViewById(R.id.emnumber2);

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1= new Intent(registration.this,MainActivity.class);
                startActivity(intent1);
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


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String store_name=name.getText().toString();
                String store_number=number.getText().toString();
                String store_pass=password.getText().toString();
                String check_repass=repassword.getText().toString();
                String store_emnumber1=emnumber1.getText().toString();
                String store_emnumber2=emnumber2.getText().toString();
                String store_code=generate.getText().toString();
                if (store_code.isEmpty() || store_emnumber2.isEmpty()|| store_emnumber1.isEmpty()|| store_name.isEmpty() || store_number.isEmpty() || store_pass.isEmpty() || check_repass.isEmpty()){
                    Toast.makeText(registration.this, "Fill the empty field", Toast.LENGTH_SHORT).show();
                }
                else if (!store_pass.equals(check_repass)){
                     Toast.makeText(registration.this, "Password doesn't match", Toast.LENGTH_SHORT).show();

                }
                else{
                    rootNode=FirebaseDatabase.getInstance();
                    reference=rootNode.getReference("data");
                    helperclass_database helperClass=new helperclass_database(store_name,store_number,store_pass,store_code,store_emnumber1,store_emnumber2);
                    reference.child(store_number).setValue(helperClass);
                Intent intent2=new Intent(registration.this,MainActivity.class);
                startActivity(intent2);
                finish();}

            }
        });

    }
}