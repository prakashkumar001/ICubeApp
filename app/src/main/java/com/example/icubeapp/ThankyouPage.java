package com.example.icubeapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Creative IT Works on 25-Jul-17.
 */

public class ThankyouPage extends AppCompatActivity {
    //Button submit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thankyou);
        //submit=(Button)findViewById(R.id.submit);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){

                Intent i=new Intent(ThankyouPage.this,Splash.class);
                startActivity(i);
                ActivityCompat.finishAffinity(ThankyouPage.this);

            }
        }, 15000);


    }

    @Override
    public void onBackPressed() {

    }
}
