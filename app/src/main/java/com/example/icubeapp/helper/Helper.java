package com.example.icubeapp.helper;

import android.content.Context;


import com.example.icubeapp.database.DaoSession;
import com.example.icubeapp.database.Smiley;
import com.example.icubeapp.database.SmileyDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Creative IT Works on 10-Aug-17.
 */

public class Helper {
    private final DaoSession daoSession;

    Context context;


    public Helper(DaoSession daoSession, Context context) {
        this.daoSession = daoSession;
        this.context = context;
        helper = this;
    }

    public static Helper getHelper() {
        return helper;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public static Helper helper;



    public List<Smiley> getEncodeImage(String FBRateId,String ratingCount)
    {

        QueryBuilder<Smiley> qb = daoSession.queryBuilder(Smiley.class);
        qb.where(SmileyDao.Properties.FBRateID.eq(FBRateId));
        qb.where(SmileyDao.Properties.RatingCount.eq(ratingCount));

        qb.limit(1);


        return  qb.list();

    }

}
