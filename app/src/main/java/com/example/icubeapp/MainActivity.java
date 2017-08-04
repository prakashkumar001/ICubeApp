package com.example.icubeapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.icubeapp.adapters.SliderAdapter;
import com.example.icubeapp.common.GlobalClass;
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

    ArrayList<Slider> data;
    private ViewPager viewpager;
    int page = 0;
    GlobalClass global;
   // Button submit;
    CodeSnippet codeSnippet;
    JSONArray array;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewpager = (ViewPager) findViewById(R.id.pager_introduction);
       // submit=(Button)findViewById(R.id.submit);
        codeSnippet=new CodeSnippet(getApplicationContext());
        global=(GlobalClass)getApplicationContext();



        /*submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if(codeSnippet.hasNetworkConnection())
               {
                  *//* if(array.length()==0)
                   {
                       responseFromServer();

                   }else {*//*
                       Intent i=new Intent(MainActivity.this,FeedBack.class);
                       startActivity(i);

                  // }

               }else
               {
                   Snackbar();
               }

            }
        });


*/

        if(codeSnippet.hasNetworkConnection())
        {

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




               /* data = new ArrayList<>();

                JSONObject object = null;
                try {
                    object = new JSONObject(s);
                    JSONArray array = object.getJSONArray("slider");
                    Log.i("CCCCCCCCCCC", "CCCCCCCCCCCCCCCC" + array);

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject ob = array.getJSONObject(i);
                        String img = ob.getString("slider_img");

                        //data.add("http://bbaministries.org/uploads/sliders/" + img);


                    }

                    SliderAdapter adapter = new SliderAdapter(MainActivity.this, data);
                    viewpager.setAdapter(adapter);
                    loadimageswithsec();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
*/


        }
        new ResultfromServer().execute();
    }

    public void loadimageswithsec(int sec) {


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
        }, 1000, sec);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(codeSnippet.hasNetworkConnection())
        {
            responseFromServer();
        }else
        {
           Snackbar();
        }

        loadimagefromresource();



       /* viewpager.setPageTransformer(false, new ViewPager.PageTransformer() {
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

*/

    }

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
                data = new ArrayList<>();
               /* data.add(decodeSampledBitmapFromResource(getResources(),R.drawable.supermarket,500,1000));
                data.add(decodeSampledBitmapFromResource(getResources(),R.drawable.supermarket2,500,1000));
                data.add(decodeSampledBitmapFromResource(getResources(),R.drawable.supermarket3,500,1000));
*/

               data.add(new Slider("image",R.drawable.car,0));
                data.add(new Slider("image",R.drawable.cat,0));
                data.add(new Slider("image",R.drawable.stone,0));


                data.add(new Slider("video",0,R.raw.splashvideo));
                data.add(new Slider("video",0,R.raw.splashvideo));



                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                dialog.dismiss();
                SliderAdapter adapter = new SliderAdapter(MainActivity.this, data);
                viewpager.setAdapter(adapter);
                loadimageswithsec(10000);

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


        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){

                Intent i=new Intent(MainActivity.this,FeedBack.class);
                startActivity(i);
            }
        }, 30000);
    }

}
