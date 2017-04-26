package com.example.myapplication;

import android.content.Context;

/**
 * Created by lenovo on 2017/2/27.
 */

public class Util {

    private static  Util mUtil;
    private Util(Context context) {
    }

    public static Util getInstance(Context context){
        if (mUtil == null){
            mUtil = new Util(context);
        }
        return mUtil;
    }

}
