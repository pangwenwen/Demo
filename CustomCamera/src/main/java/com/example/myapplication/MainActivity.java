package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static final String PERMISSION_ACTION = "com.android.setting.ACTION_GET_PERMISSION_DETAILS";
    private static final int CAMERA_FACE_BACK = 0;
    private static final int CAMERA_FACE_FRONT = 1;

    private Camera mCamera;
    private Button mCaptureBtn;
    private Button mSwitchBtn;
    private Camera.Parameters mParameters;
    private PerssionManager mPerssionManager;
    private AlertDialog.Builder mBuilder;

    private boolean mIsShowDialog;
    private boolean mIsOpenCamera;
    private int mCameraId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!checkCameraHardware(this)){
            return;
        }

        mPerssionManager = new PerssionManager();
        if(mPerssionManager.requesePerssion(this)){
            openCamera();
        }
        mCaptureBtn = (Button) findViewById(R.id.capture);
        mCaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });

        mSwitchBtn = (Button)findViewById(R.id.switch);
        mSwitchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchCamera();
            }
        });

    }

    private void switchCamera() {

    }



    @Override
    protected void onResume() {
        super.onResume();
        if(mPerssionManager.requesePerssion(this)){
            openCamera();
        }


    }

    private void openCamera(){
        if (!mIsOpenCamera){
            mCamera = getCameraInstance();
            if (mCamera == null){
                return;
            }

            setPreSetting();

            FrameLayout layout = (FrameLayout) findViewById(R.id.surface);
            PreviewView mPreview = new PreviewView(this,mCamera);
            layout.addView(mPreview);

            mIsOpenCamera = true;
        }
    }


    /**
     * 注意：在打开所有权限返回程序第一次的时候，grantResults的值有误，所以不能凭借grantResults 的值判断是否需要显示dialog
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0;i<permissions.length;i++){
            Log.e(TAG, "onRequestPermissionsResult: perssion = "+permissions[i] +"; grantResult = "+grantResults[i]);
        }

        boolean isReady = mPerssionManager.isCameraPerssionReady(this);
        if(!isReady){
            showPerssionDialog();
        }

        //PerssionManager.requesePerssion(this,permissions);
    }


    private void setPreSetting(){
        mParameters = mCamera.getParameters();
        mCamera.setDisplayOrientation(90);

        List<String> mFoucusList = mParameters.getSupportedFocusModes();
        if (mFoucusList.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)){
            mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }

        setFocusArea();
        mCamera.setParameters(mParameters);

    }

    private void setFocusArea(){
        if (mParameters.getMaxNumFocusAreas() > 0){
            List<Camera.Area> meteringAreas = new ArrayList<>();
            Rect rect = new Rect(-100,-100,100,100);
            meteringAreas.add(new Camera.Area(rect,100));
            mParameters.setFocusAreas(meteringAreas);
        }
    }

    /**
     * 检查是否有相机硬件
     * @param context
     * @return
     */
    private boolean checkCameraHardware(Context context){
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            return true;
        }
        return false;
    }

    /**
     * 获取相机
     * @return
     */
    private Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open();
        }catch (Exception e){
            Log.e(TAG, "getCameraInstance: e"+e.toString());
        }
        return c;
    }

    @Override
    protected void onDestroy() {
//        releaseCamera();
        super.onDestroy();
    }

    private void takePicture(){
        mCamera.takePicture(null,null,mPictureCallback);
    }

    /**
     * 拍照后保存照片的回调
     */
    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null || !pictureFile.exists()){
                return;
            }
            try {
                FileOutputStream os = new FileOutputStream(pictureFile);
                os.write(bytes);
                os.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * 创建输出额路径
     * @param type
     * @return
     */
    private static File getOutputMediaFile(int type){
        File mediaStorageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = null;
        if (type == MEDIA_TYPE_IMAGE){
            try {
                mediaFile =  File.createTempFile(timeStamp,".jpg",mediaStorageDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return null;
        }

        Log.e(TAG, "getOutputMediaFile: mediaFile = "+mediaFile);
        return mediaFile;
    }

    /**
     * 释放相机
     */
    private void releaseCamera(){
        if (mCamera != null){
            mCamera.release();
            mCamera = null;
        }
    }

    /**
     *
     */
    private  boolean isHasGivenCamera(int face){
        int count = Camera.getNumberOfCameras();
        Camera.CameraInfo info  = new Camera.CameraInfo();
        for (int i = 0; i< count;i++){
            Camera.getCameraInfo(i,info);

        }

        return false;
    }


    /**
     * show dialog
     */
    private void showPerssionDialog(){
        Log.e(TAG, "showPerssionDialog");
        if(mIsShowDialog){
            return;
        }

        if (mBuilder == null){
            mBuilder = new AlertDialog.Builder(this);
            mBuilder.setMessage(R.string.perssion_detial).setPositiveButton(R.string.to_set, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    toSetPerssion();
                }
            }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                     finish();
                }
            }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    Log.e(TAG, "onCancel: setOnCancelListener");
                    finish();
                }
            }).setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    Log.e(TAG, "onDismiss: setOnDismissListener");
                    mIsShowDialog = false;
                }
            });

        }
        mBuilder.show();

        Log.e(TAG, "showPerssionDialog: show");


        mIsShowDialog = true;
    }

    /**
     * jump to open perssion
     */
    private void toSetPerssion(){
        Intent intent = new Intent(PERMISSION_ACTION);
        intent.putExtra("packagename",getPackageName());
        startActivity(intent);
    }

}
