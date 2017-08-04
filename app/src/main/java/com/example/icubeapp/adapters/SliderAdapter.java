package com.example.icubeapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.VideoView;


import com.example.icubeapp.MainActivity;
import com.example.icubeapp.R;
import com.example.icubeapp.model.Slider;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yqritc.scalablevideoview.ScalableVideoView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Prakash on 7/22/2017.
 */

public class SliderAdapter extends PagerAdapter {

    private Context mContext;
    ArrayList<Slider> data;
    DisplayImageOptions options;
    ImageLoader loader;
    public SliderAdapter(Context mContext, ArrayList<Slider> data) {
        this.mContext = mContext;
        this.data = data;
        loader=ImageLoader.getInstance();

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.pager_item, container, false);

      /*  options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loadimage) // resource or drawable
                .showImageForEmptyUri(R.drawable.loadimage) // resource or drawable
                .showImageOnFail(R.drawable.loadimage) // resource or drawable
                .resetViewBeforeLoading(false)  // default
                .delayBeforeLoading(10)
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .build();*/
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);

        final ScalableVideoView videoView = (ScalableVideoView)itemView. findViewById(R.id.Videoview);

        //MainActivity a=(MainActivity)mContext;
        if(data.get(position).type.equals("image"))
        {

            imageView.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.GONE);
            loader.displayImage("drawable://" +data.get(position).image,imageView,options);
        }else
        {

            imageView.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);




            try {
                videoView.setRawData(R.raw.splashvideo);
                videoView.setVolume(0, 0);
                videoView.setLooping(true);
                videoView.prepare(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        videoView.start();
                    }
                });
            } catch (IOException ioe) {
                //ignore
            }


           /* String uriPath = "android.resource://com.example.icubeapp/"+data.get(position).videopath;  //update package name
            Uri uri = Uri.parse(uriPath);
            mVideoView.setVideoURI(uri);
            mVideoView.requestFocus();
            mVideoView.start();
            mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mVideoView.start();
                }
            });*/
        }




        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

    /************************ Resize Bitmap *********************************/
    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {

        int width = bm.getWidth();
        int height = bm.getHeight();

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

// create a matrix for the manipulation
        Matrix matrix = new Matrix();

// resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);

// recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

        return resizedBitmap;
    }
}

