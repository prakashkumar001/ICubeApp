package com.example.icubeapp.model;

/**
 * Created by Creative IT Works on 04-Aug-17.
 */

public class Slider {
    public String type;
    public Integer image;
    public Integer videopath;
    public String videosec;
    public String imgsec;

    public Slider(String type, Integer image, Integer videopath,String videosec,String imgsec) {
        this.type = type;
        this.image = image;
        this.videopath = videopath;
        this.videosec=videosec;
        this.imgsec=imgsec;
    }
}
