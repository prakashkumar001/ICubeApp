package com.example.icubeapp.common;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import com.example.icubeapp.model.FEEDBACK;
import com.example.icubeapp.model.FeedBackSelection;
import com.example.icubeapp.model.POS;
import com.example.icubeapp.model.Slider;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;

/**
 * Created by Prakash on 7/22/2017.
 */

public class GlobalClass extends Application {

    private static GlobalClass mInstance;

    public static POS pos=new POS();
    public static ArrayList<FEEDBACK> feedback=new ArrayList<>();
    public static ArrayList<FeedBackSelection> feedbackdata=new ArrayList<>();
    public static ArrayList<Slider> data=new ArrayList<>();
    public static int position=0;
    //http://icube.cloudapp.net:8080/iCubeIOS/
    //public static String globalurl="http://192.168.10.13:81";//117.218.77.219:81
    public static String globalurl="";
    public static String empId;
    public static String languageId="1";
    public void onCreate() {


        super.onCreate();
        mInstance = this;
        initImageLoader(getApplicationContext());

    }
    public ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    }


    public static synchronized GlobalClass getInstance() {
        return mInstance;
    }


    public static void initImageLoader(Context context) {


        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)

                .threadPriority(Thread.NORM_PRIORITY - 2)

                .denyCacheImageMultipleSizesInMemory()

                .discCacheFileNameGenerator(new Md5FileNameGenerator())

                .tasksProcessingOrder(QueueProcessingType.LIFO)

                .build();


        ImageLoader.getInstance().init(config);


    }

}
