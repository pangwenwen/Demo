package com.example.myapplication;

/**
 * Created by lenovo on 2017/4/24.
 */

public final class Log{
    private static final String TAG = "ph_cam";

    public Log() {
    }

    public static final void v(String tag,String msg){
        android.util.Log.v(TAG+tag,msg);
    }

    public static final void v(String tag,String msg,Throwable throwable){
        android.util.Log.v(TAG+tag,msg,throwable);
    }
    public static final void d(String tag,String msg){
        android.util.Log.d(TAG+tag,msg);
    }
    public static final void d(String tag,String msg,Throwable throwable){
        android.util.Log.d(TAG+tag,msg,throwable);
    }
    public static final void i(String tag,String msg){
        android.util.Log.i(TAG+tag,msg);
    }
    public static final void i(String tag,String msg,Throwable throwable){
        android.util.Log.i(TAG+tag,msg,throwable);
    }
    public static final void w(String tag,String msg){
        android.util.Log.w(TAG+tag,msg);
    }
    public static final void w(String tag,String msg,Throwable throwable){
        android.util.Log.w(TAG+tag,msg,throwable);
    }
    public static final void e(String tag,String msg){
        android.util.Log.e(TAG+tag,msg);
    }
    public static final void e(String tag,String msg,Throwable throwable){
        android.util.Log.e(TAG+tag,msg,throwable);
    }


}
