package com.example.myapplication;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.FaceDetectionListener;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

import static android.content.ContentValues.TAG;

/**
 * Problems
 *    1. 6.0后不会在安装时打开权限，所以如果没有添加权限申请会导致界面无法获取权限，打开相机失败
 *
 * Created by lenovo on 2017/2/9.
 */

public class PreviewView extends SurfaceView implements SurfaceHolder.Callback{


    private Camera mCamera;
    private SurfaceHolder mSurfaceHolder;


    public PreviewView(Context context,Camera camera) {
        super(context);
        mSurfaceHolder = getHolder();
        mCamera = camera;

        mCamera.setFaceDetectionListener(new MyFaceDectionListener());

        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }



    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCamera.startPreview();
        startFaceDection();

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        if (mSurfaceHolder.getSurface() == null){
            return;
        }
        mCamera.stopFaceDetection();
        mCamera.stopPreview();
        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCamera.startPreview();
        mCamera.startFaceDetection();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (mCamera != null){
            mCamera.stopFaceDetection();
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    class MyFaceDectionListener implements FaceDetectionListener{
        @Override
        public void onFaceDetection(Camera.Face[] faces, Camera camera) {
            Log.e(TAG, "onFaceDetection: face size = "+faces.length);
        }
    }

    private void startFaceDection(){
        int cunt = mCamera.getParameters().getMaxNumDetectedFaces();
        if (cunt > 0){
            mCamera.startFaceDetection();
        }
    }


}
