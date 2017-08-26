package com.example.icubeapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Creative IT Works on 25-Jul-17.
 */

public class ThankyouPage extends AppCompatActivity {
    Button logout;
    Handler handler;
    boolean doubleBackToExitPressedOnce=false;
    Dialog dialogs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thankyou);
        logout=(Button)findViewById(R.id.log_out);


         handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){

                Intent i=new Intent(ThankyouPage.this,Splash.class);
                startActivity(i);
                ActivityCompat.finishAffinity(ThankyouPage.this);

            }
        }, 15000);


       /* logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });*/
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            if(handler!=null)
            {
                handler.removeCallbacksAndMessages(null);
            }
            ActivityCompat.finishAffinity(ThankyouPage.this);

            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Test", "Home button pressed!");

        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }

        ActivityCompat.finishAffinity(ThankyouPage.this);

    }

    void showDialog()
    {
        dialogs = new Dialog(ThankyouPage.this, R.style.ThemeDialogCustom);
        dialogs.setContentView(R.layout.show_dialog);
        dialogs.setCancelable(false);

        final EditText username=(EditText)dialogs.findViewById(R.id.user);
        final EditText password=(EditText)dialogs.findViewById(R.id.password);
        ImageView close = (ImageView) dialogs.findViewById(R.id.iv_close);
        Button logouts = (Button) dialogs.findViewById(R.id.btn_logout);


        logouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = getSharedPreferences("Data", MODE_PRIVATE);
                String user = preferences.getString("user", "");
                String pass = preferences.getString("pass", "");

                if(username.getText().toString().equalsIgnoreCase(user) && password.getText().toString().equalsIgnoreCase("pass"))
                {

                    SharedPreferences.Editor editor = getSharedPreferences("Data", MODE_PRIVATE).edit();
                    editor = getSharedPreferences("Data", MODE_PRIVATE).edit();
                    editor.putBoolean("loginstatus", false);
                    editor.putString("EmpID", "");
                    editor.putString("language", "");
                    editor.putString("user", "");
                    editor.putString("pass", "");
                    editor.commit();
                    dialogs.dismiss();
                    startActivity(new Intent(getBaseContext(), Login.class));
                    ActivityCompat.finishAffinity(ThankyouPage.this);

                }else
                {
                    Toast.makeText(getApplicationContext(),"Username or Password is Invalid",Toast.LENGTH_SHORT).show();
                }


            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogs.dismiss();
            }
        });


        dialogs.show();
    }

}
