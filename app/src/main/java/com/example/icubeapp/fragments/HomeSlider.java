package com.example.icubeapp.fragments;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.icubeapp.R;
import com.example.icubeapp.common.GlobalClass;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yqritc.scalablevideoview.ScalableVideoView;

import java.io.IOException;

/**
 * Created by Creative IT Works on 07-Aug-17.
 */

public class HomeSlider extends Fragment {
    ImageLoader loader;
    ImageView imageView;
    ScalableVideoView videoView;
    GlobalClass globalClass;
    String page;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_item, container, false);
        globalClass=new GlobalClass();

        loader=ImageLoader.getInstance();

        Bundle b=getArguments();
        page=b.getString("position");


        imageView = (ImageView) view.findViewById(R.id.imageView);
        videoView = (ScalableVideoView) view.findViewById(R.id.Videoview);


        fetchData();


        return  view;
    }
    void fetchData() {
        if (globalClass.data.get(Integer.parseInt(page)).type.equals("image")) {

            imageView.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.GONE);
            loader.displayImage("drawable://" + globalClass.data.get(Integer.parseInt(page)).image, imageView);
        } else if (globalClass.data.get(Integer.parseInt(page)).type.equals("video")) {

            imageView.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);


            try {
                videoView.setRawData(globalClass.data.get(Integer.parseInt(page)).videopath);

                // videoView.setVolume(50, 50);
                // videoView.setLooping(true);
                videoView.prepare(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        videoView.start();

                    }
                });
            } catch (IOException ioe) {
                //ignore
            }



        }


        Log.i("DDDDDDDDdd","DDDDDDDD"+globalClass.data.size()+page);

        if(globalClass.data.size()==Integer.parseInt(page)+1)
        {
            globalClass.position=0;
        }else
        {
            globalClass.position=Integer.parseInt(page)+1;
        }
    }
}
