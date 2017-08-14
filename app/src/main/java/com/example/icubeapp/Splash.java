package com.example.icubeapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.model.ConfigSplash;

/**
 * Created by Creative IT Works on 28-Jul-17.
 */

public class Splash extends AwesomeSplash {
    public void initSplash(ConfigSplash configSplash) {
        configSplash.setBackgroundColor(android.R.color.white);
        configSplash.setAnimCircularRevealDuration(2000);
        configSplash.setRevealFlagX(4);
        configSplash.setRevealFlagY(2);
        configSplash.setLogoSplash(R.drawable.welcome);
        configSplash.setAnimLogoSplashDuration(2000);
        configSplash.setOriginalHeight(2000);
        configSplash.setOriginalWidth(2000);
        configSplash.setAnimLogoSplashTechnique(Techniques.Landing);
        configSplash.setTitleSplash("");
        //configSplash.setTitleTextColor(R.color.colorPrimary);
        //configSplash.setTitleTextSize(25.0f);
        configSplash.setAnimTitleDuration(1000);
        configSplash.setAnimTitleTechnique(Techniques.SlideInUp);
        //configSplash.setTitleFont("fonts/Comfortaa_Bold.ttf");


    }

    public void animationsFinished() {
       /* startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
        finish();
*/
        Intent i=new Intent(Splash.this,FeedBack.class);
        startActivity(i);
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
        finish();
    }
}
