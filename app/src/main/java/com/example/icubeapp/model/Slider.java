package com.example.icubeapp.model;

/**
 * Created by Creative IT Works on 04-Aug-17.
 */

public class Slider {
    public String type;
    public Integer image;
    public Integer videopath;
    public String sec;

    public Slider(String type, Integer image, Integer videopath,String sec) {
        this.type = type;
        this.image = image;
        this.videopath = videopath;
        this.sec=sec;
    }
}
