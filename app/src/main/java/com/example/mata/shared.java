package com.example.mata;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class shared {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    int mode=0;

    String Filename="sdfile";

    String Data="b";

    public shared(Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences(Filename,mode);
        editor=sharedPreferences.edit();

    }

    public void second_time(){ // setting boolean after logging in one time

        editor.putBoolean(Data,true);
        editor.commit();
    }

    public void log_out(){ // setting boolean after logout

        editor.putBoolean(Data,false);
        editor.commit();
    }

    public  void first_time(){ // checking for the first time

        if (!this.login()){
            Intent intent = new Intent(context,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);

        }
    }

    private boolean login() {
        return sharedPreferences.getBoolean(Data,false);
    }
}
