package com.example.icubeapp.model;

/**
 * Created by Prakash on 7/23/2017.
 */

public class POS {
    public String id="";
    public String pos_id="";
    public String memo_no="";
    public String netamount="";
    public POS(String id,String pos_id,String memono,String netamount)
    {
        this.id=id;
        this.pos_id=pos_id;
        this.memo_no=memono;
        this.netamount=netamount;
    }

    public POS() {
    }
}
