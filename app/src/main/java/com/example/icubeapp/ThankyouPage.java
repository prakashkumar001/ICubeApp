package com.example.icubeapp;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Creative IT Works on 25-Jul-17.
 */

public class ThankyouPage extends AppCompatActivity {
    Handler handler;
    boolean doubleBackToExitPressedOnce=false;
    public boolean done = false;
    Dialog dialogs;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thankyou);


         handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){

                Intent i=new Intent(ThankyouPage.this,Splash.class);
                startActivity(i);
                ActivityCompat.finishAffinity(ThankyouPage.this);

            }
        }, 5000);


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

    /*@Override
    protected void onStop() {
        super.onStop();
        Log.d("Test", "Home button pressed!");

        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }



        ActivityCompat.finishAffinity(ThankyouPage.this);

    }*/

    @Override
    protected void onPause() {
        super.onPause();

        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }


        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);

        activityManager.moveTaskToFront(getTaskId(), 0);
        //ActivityCompat.finishAffinity(ThankyouPage.this);

        showDialog();



    }


    void showDialog() {

        done=true;

        if(dialogs!=null)
        {
            dialogs.dismiss();
        }
        dialogs = new Dialog(ThankyouPage.this, R.style.ThemeDialogCustom);
        dialogs.setContentView(R.layout.show_dialog);
        dialogs.setCancelable(false);
        final TextView users = (TextView) dialogs.findViewById(R.id.user);
        final EditText password = (EditText) dialogs.findViewById(R.id.password);
        ImageView close = (ImageView) dialogs.findViewById(R.id.iv_close);
        Button logouts = (Button) dialogs.findViewById(R.id.btn_logout);
        SharedPreferences preferences = getSharedPreferences("Data", MODE_PRIVATE);
        final String pass = preferences.getString("pass", "");
        final String user = preferences.getString("user", "");
        users.setText(user);

        logouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (password.getText().toString().equalsIgnoreCase(pass)) {

                    done=true;
                    SharedPreferences.Editor editor = getSharedPreferences("Data", MODE_PRIVATE).edit();
                    editor = getSharedPreferences("Data", MODE_PRIVATE).edit();
                    editor.putBoolean("loginstatus", false);
                    editor.putString("EmpID", "");
                    editor.putString("language", "");
                    editor.putString("user", "");
                    editor.putString("pass", "");
                    editor.putString("url", "");
                    editor.commit();
                    dialogs.dismiss();
                    startActivity(new Intent(getBaseContext(), Login.class));
                    ActivityCompat.finishAffinity(ThankyouPage.this);

                } else {
                    Toast.makeText(getApplicationContext(), "Password is Invalid", Toast.LENGTH_SHORT).show();
                }


            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(dialogs!=null)
                {
                    dialogs.dismiss();
                }

                //finish();
               // startActivity(new Intent(getBaseContext(), Splash.class));

            }
        });


        dialogs.show();
    }

}
