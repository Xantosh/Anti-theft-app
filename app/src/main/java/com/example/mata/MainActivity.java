package com.example.mata;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    TextView click;
    Button signin;
    private long pressedTime;
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

                if (login_email.getText().toString().isEmpty()){
                    login_email.setError("Enter the field");
                }

                else if (login_password.getText().toString().isEmpty()){
                    login_password.setError("Enter the field");
                }


                else {
                    is_user();
                }
            }

            private void is_user() {
                String username=login_email.getText().toString();
                String password=login_password.getText().toString();

                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("data");
                Query checkUser=reference.orderByChild("phone_number").equalTo(username);

                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            login_email.setError(null);
                            String password_from_db=snapshot.child(username).child("password").getValue(String.class);

                            if (password_from_db.equals(password)){
                                login_password.setError(null);
                                String name_from_db=snapshot.child(username).child("full_name").getValue(String.class);
                                String phoneNo_from_db=snapshot.child(username).child("phone_number").getValue(String.class);
                                String code_from_db=snapshot.child(username).child("secret_code").getValue(String.class);
                                String emergency1_from_db=snapshot.child(username).child("emergency1").getValue(String.class);
                                String emergency2_from_db=snapshot.child(username).child("emergency2").getValue(String.class);

                                Intent intent= new Intent(MainActivity.this,home_screen.class);
                                intent.putExtra("send_name",name_from_db);
                                 startActivity(intent);
                                 finish();
                                 shared shared= new shared(getApplicationContext());
                                // setting for the second time
                                shared.second_time();
                            }
                            else {

                                login_password.setError("Incorrect Password");
                                login_password.requestFocus();
                            }
                        }

                        else {
                            login_email.setError("No user found");
                            login_email.requestFocus();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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