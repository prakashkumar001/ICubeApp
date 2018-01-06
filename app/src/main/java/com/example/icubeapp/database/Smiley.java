package com.example.icubeapp.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Prakash on 1/6/2018.
 */

@Entity
public class Smiley {
    @Id
    Long id;
    public String FBRateID;
    public String RatingCount;
    public String beforeImage;  //AfterImage
    public String AfterImage;
    @Generated(hash = 1345683364)
    public Smiley(Long id, String FBRateID, String RatingCount, String beforeImage,
            String AfterImage) {
        this.id = id;
        this.FBRateID = FBRateID;
        this.RatingCount = RatingCount;
        this.beforeImage = beforeImage;
        this.AfterImage = AfterImage;
    }
    @Generated(hash = 450291847)
    public Smiley() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFBRateID() {
        return this.FBRateID;
    }
    public void setFBRateID(String FBRateID) {
        this.FBRateID = FBRateID;
    }
    public String getRatingCount() {
        return this.RatingCount;
    }
    public void setRatingCount(String RatingCount) {
        this.RatingCount = RatingCount;
    }
    public String getBeforeImage() {
        return this.beforeImage;
    }
    public void setBeforeImage(String beforeImage) {
        this.beforeImage = beforeImage;
    }
    public String getAfterImage() {
        return this.AfterImage;
    }
    public void setAfterImage(String AfterImage) {
        this.AfterImage = AfterImage;
    }


}
