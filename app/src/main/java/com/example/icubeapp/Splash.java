package com.example.icubeapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Creative IT Works on 28-Jul-17.
 */

public class Splash extends AppCompatActivity {
    final int SPLASH_DISPLAY_TIME = 2000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable() {
            public void run() {

                Splash.this.finish();
                overridePendingTransition(R.anim.fadeinact,
                        R.anim.fadeoutact);

                Intent mainIntent = new Intent(
                        Splash.this,
                        MainActivity.class);

                Splash.this.startActivity(mainIntent);

                finish();



            }
        }, SPLASH_DISPLAY_TIME);
    }
}
