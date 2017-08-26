package com.example.icubeapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.daimajia.androidanimations.library.Techniques;
import com.example.icubeapp.common.GlobalClass;
import com.example.icubeapp.model.POS;
import com.example.icubeapp.utils.WSUtils;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.model.ConfigSplash;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Creative IT Works on 28-Jul-17.
 */

public class Splash extends AwesomeSplash {
    boolean doubleBackToExitPressedOnce = false;
    JSONArray array;
    String macaddress = "";
    GlobalClass globalClass;
    ProgressDialog dialog;

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0"))
                    continue;  //instead of wlan0 i used eth0
                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }
                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }

                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }

    public void initSplash(ConfigSplash configSplash) {
        globalClass = new GlobalClass();
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
       /* Intent i=new Intent(Splash.this,FeedBack.class);
        startActivity(i);
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
        finish();*/
        dialog = new ProgressDialog(Splash.this);
        dialog.setMessage("Loading....");
        dialog.setCancelable(false);
        dialog.show();
        getPOSId();
    }

    /*@Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            ActivityCompat.finishAffinity(Splash.this);

            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
    }*/

    @Override
    public void animationsStop() {
        ActivityCompat.finishAffinity(Splash.this);
    }

    @Override
    public void onBackPressed() {

        // super.onBackPressed();
        // ActivityCompat.finishAffinity(Splash.this);

    }

    public void getPOSId() {
        class ResultfromServer extends AsyncTask<String, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                macaddress = getMacAddr();
            }

            @Override
            protected String doInBackground(String... strings) {

                //String url="http://192.168.1.16/imageupload/api.php";
                String url = globalClass.globalurl + "/api/Feedback/GetspSelectFeedback?type=CheckPendingFeedback&ExtraString1=" + getMacAddr() + "&ExtraString2=&ExtraString3=&ExtraString4=&ExtraString5=&ExtraString6=&ExtraString7=&ExtraString8=&ExtraString9=&ExtraString10";
                String response = new WSUtils().getResultFromHttpRequest(url, "GET", new HashMap<String, String>());


                Log.i("RESPONSE", "RESPOSE" + response);
                return response;
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if (s == null) {

                    getPOSId();
                    //Toast.makeText(getApplicationContext(),"NULL",Toast.LENGTH_SHORT).show();

                } else {
                    try {
                        array = new JSONArray(s);
                        if (array.length() == 0) {
                            // Toast.makeText(getApplicationContext(),"empty array",Toast.LENGTH_SHORT).show();
                            // dialog.dismiss();
                           // getPOSId();

                            Intent i = new Intent(Splash.this, FeedBack.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                            finish();

                            // Toast.makeText(getApplicationContext(),"Please Try again",Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String ID = object.getString("ID");
                                String POSID = object.getString("POSID");

                                globalClass.pos = new POS(ID, POSID);
                            }

                            Intent i = new Intent(Splash.this, FeedBack.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                            finish();

                            // responseFromServer();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }


        }
        new ResultfromServer().execute();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Test", "Home button pressed!");

        animationsStop();

    }
}
