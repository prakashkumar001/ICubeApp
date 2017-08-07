package com.example.icubeapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.icubeapp.common.GlobalClass;
import com.example.icubeapp.fragments.HomeSlider;
import com.example.icubeapp.model.POS;
import com.example.icubeapp.model.Slider;
import com.example.icubeapp.utils.CodeSnippet;
import com.example.icubeapp.utils.WSUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public int secs;
    ArrayList<Slider> data;
    int page = 0;
    GlobalClass global;
   // Button submit;
    CodeSnippet codeSnippet;
    JSONArray array;
    ProgressDialog dialog;
    Handler handler=new Handler();
    Runnable runnable;
    int sec=3000;
    TimerTask timerTask=null;
    Timer timer=null;
    CountDownTimer countDownTimer;
    boolean doubleBackToExitPressedOnce=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        codeSnippet=new CodeSnippet(getApplicationContext());
        global=(GlobalClass)getApplicationContext();




        if(codeSnippet.hasNetworkConnection())
        {
            responseFromServer();
            loadimagefromresource();
        }else {
            Snackbar();
        }
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

                String response = new WSUtils().getResultFromHttpRequest("http://icube.cloudapp.net:8080/iCubeIOS/api/Feedback/GetspSelectFeedback?type=CheckPendingFeedback&ExtraString1=1234&ExtraString2=&ExtraString3=&ExtraString4=&ExtraString5=&ExtraString6=&ExtraString7=&ExtraString8=&ExtraString9=&ExtraString10", "GET", new HashMap<String, String>());

                Log.i("RESPONSE","RESPOSE"+response);
                return response;
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                dialog.dismiss();


                    try {
                         array=new JSONArray(s);
                        if(array.length()==0)
                        {
                            responseFromServer();
                            Toast.makeText(getApplicationContext(),"Please Try again",Toast.LENGTH_SHORT).show();
                        }else
                        {
                            for(int i=0;i<array.length();i++)
                            {
                                JSONObject object=array.getJSONObject(i);
                                String ID=object.getString("ID");
                                String POSID=object.getString("POSID");

                                global.pos=new POS(ID,POSID);
                            }

                            switvhtonextscreen();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }





        }
        new ResultfromServer().execute();
    }





      /*  viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i("SCREOLL","SCROLL"+viewpager.getCurrentItem());
                page=position;
                if(data.get(position).type.equals("video"))
                {
                    sec=Integer.parseInt(data.get(position).videosec);


                }else {
                    sec=Integer.parseInt(data.get(position).imgsec);

                }

            }

            @Override
            public void onPageSelected(int position) {


                //loadimageswithsec(3000);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/




    public void Snackbar()
    {
        Snackbar snack= Snackbar.make(findViewById(android.R.id.content), "No Internet Connection",Snackbar.LENGTH_LONG);
        View vv=snack.getView();
        TextView textView=(TextView)vv.findViewById(android.support.design.R.id.snackbar_text);
        textView.setGravity(Gravity.CENTER);
        snack.show();
        snack.setAction("Enable", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(codeSnippet.hasNetworkConnection())
                {
                    responseFromServer();
                }else
                {
                    startActivityForResult(new Intent(Settings.ACTION_SETTINGS),1);
                }
            }
        });
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public void loadimagefromresource()
    {
        class loadimage extends AsyncTask<Void,Void,Void>
        {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog=new ProgressDialog(MainActivity.this);
                dialog.setMessage("Loading....");
                dialog.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                global.data = new ArrayList<>();
                global.data.add(new Slider("image",R.drawable.car,0,"5000"));
                global.data.add(new Slider("image",R.drawable.cat,0,"5000"));
                global.data.add(new Slider("video",0,R.raw.video,"10000"));
                global.data.add(new Slider("image",R.drawable.stone,0,"5000"));


                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                dialog.dismiss();
                secs= Integer.parseInt(global.data.get(global.position).sec);
                //loadimageswithsec(secs);
                countdown();

            }
        } new loadimage().execute();;
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        if ( dialog!=null && dialog.isShowing() ){
            dialog.cancel();
        }
    }

    public void switvhtonextscreen() {


         handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){

                countDownTimer.cancel();
                Intent i=new Intent(MainActivity.this,FeedBack.class);
                startActivity(i);
                finish();

            }
        }, 30000);
    }

    private void loadFragments( int position) {
        HomeSlider newFragment = new HomeSlider();
        Bundle b=new Bundle();
        b.putString("position", String.valueOf(position));

        newFragment.setArguments(b);
        FragmentTransaction transcation=getSupportFragmentManager().beginTransaction();
        transcation.setCustomAnimations(R.anim.fadein,R.anim.fadeout);
        transcation.replace(R.id.container,newFragment,newFragment.getClass().getSimpleName()).commit();

// Commit the transaction
    }


    public void countdown()
    {
        long secs=Long.parseLong(global.data.get(global.position).sec);
         countDownTimer = new CountDownTimer(secs, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {

              Log.i("POSTIOM","POSITION"+global.position+"pppp"+global.data.size());

                    countdown();


            }
        }.start();
        loadFragments(global.position);
    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            if(countDownTimer!=null)
            {
                countDownTimer.cancel();
            }
            if(handler!=null)
            {
                handler.removeCallbacksAndMessages(null);
            }


            finish();

            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

    }
}
