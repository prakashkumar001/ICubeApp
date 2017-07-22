package com.example.icubeapp.common;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by Prakash on 7/22/2017.
 */

public class GlobalClass extends Application {

    private static GlobalClass mInstance;

    public void onCreate() {


        super.onCreate();
        mInstance = this;
        initImageLoader(getApplicationContext());

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
