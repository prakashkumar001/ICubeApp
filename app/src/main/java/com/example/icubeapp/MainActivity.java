package com.example.icubeapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.icubeapp.adapters.SliderAdapter;
import com.example.icubeapp.utils.WSUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> data;
    private ViewPager viewpager;
    int page = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewpager = (ViewPager) findViewById(R.id.pager_introduction);

        responseFromServer();

        viewpager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View view, float position) {
                // do transformation here
                //page.animate()==null;
                final Animation animationFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
                view.startAnimation(animationFadeIn);

                view.setTranslationX(view.getWidth() * -position);

                if(position <= -1.0F || position >= 1.0F) {
                    view.setAlpha(0.0F);
                } else if( position == 0.0F ) {
                    view.setAlpha(1.0F);
                } else {
                    // position is between -1.0F & 0.0F OR 0.0F & 1.0F
                    view.setAlpha(1.0F - Math.abs(position));
                }
            }
        });

    }


    public void responseFromServer() {
        class ResultfromServer extends AsyncTask<String, Void, String> {

            ProgressDialog dialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = new ProgressDialog(MainActivity.this);
                dialog.setMessage("Loading....");
                dialog.show();
            }

            @Override
            protected String doInBackground(String... strings) {

                String response = new WSUtils().getResultFromHttpRequest("http://bbaministries.org/webservice/sliders", "POST", new HashMap<String, String>());

                return response;
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                dialog.dismiss();

                data = new ArrayList<>();

                JSONObject object = null;
                try {
                    object = new JSONObject(s);
                    JSONArray array = object.getJSONArray("slider");
                    Log.i("CCCCCCCCCCC", "CCCCCCCCCCCCCCCC" + array);

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject ob = array.getJSONObject(i);
                        String img = ob.getString("slider_img");

                        data.add("http://bbaministries.org/uploads/sliders/" + img);


                    }

                    SliderAdapter adapter = new SliderAdapter(MainActivity.this, data);
                    viewpager.setAdapter(adapter);
                    loadimageswithsec();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
        new ResultfromServer().execute();
    }

    public void loadimageswithsec() {
        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {
                                  /*  if (getItem() == 2 - 1) {
                                        page = 0;
                                    }*/
                if (data.size() == page) {
                    page = 0;
                    viewpager.setCurrentItem(page);
                } else {
                    viewpager.setCurrentItem(page++);

                }


            }
        };


        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 1000, 5000);
    }

}
