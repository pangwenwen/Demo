package com.example.myapplication;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    private Camera mCamera;
    private MediaRecorder mMediaRecorder;
    private PreviewView mPreviewView;
    private Button mButton;
    private boolean mIsRecording;
    private static final String STOP = "Stop";
    private static final String RECORDINF = "Record";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!checkCameraHardware()) {
            return;
        }

        getCameraInstance();

        if (mCamera == null) {
            return;
        }

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.surface);
        mPreviewView = new PreviewView(this, mCamera);
        frameLayout.addView(mPreviewView);

        mButton = (Button) findViewById(R.id.capture);
        mButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (mIsRecording) {
            mButton.setText(RECORDINF);
            mMediaRecorder.stop();
            releaseRecorder();
            mIsRecording = false;
        } else {
            if (prepareRecorder()) {
                mMediaRecorder.start();
                mButton.setText(STOP);
                mIsRecording = true;
            } else {
                releaseRecorder();
            }
        }
    }

    private void getCameraInstance() {
        mCamera = Camera.open();
    }

    private boolean checkCameraHardware() {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        }
        return false;
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    private void releaseRecorder() {
        if (mMediaRecorder != null) {
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
            if (mCamera != null) {
                mCamera.lock();
            }
        }
    }

    private boolean prepareRecorder() {
        mMediaRecorder = new MediaRecorder();

        //Step 1: Unlock and set camera to MediaRecorder
        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);

        CamcorderProfile camcorderProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);

        //Step 2: Set sources
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        //Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
        mMediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));


        //Step 4: Set output file
        mMediaRecorder.setOutputFile(getOutPutVideo().toString());

        // Usually it is either 1 (mono 单声道) or 2 (stereo 立体声).
        mMediaRecorder.setAudioChannels(2);

        //设置音频编码比特率
        mMediaRecorder.setAudioEncodingBitRate(camcorderProfile.audioBitRate);
        Log.e(TAG, "prepareRecorder: audioBitRate = "+camcorderProfile.audioBitRate);
        mMediaRecorder.setVideoSize(camcorderProfile.videoFrameWidth,camcorderProfile.videoFrameHeight);
        Log.e(TAG, "prepareRecorder: width = "+camcorderProfile.videoFrameWidth+"; height = "+camcorderProfile.videoFrameHeight);
        mMediaRecorder.setVideoFrameRate(camcorderProfile.videoFrameRate);
        Log.e(TAG, "prepareRecorder: videoFrameRate = "+camcorderProfile.videoFrameRate);
        mMediaRecorder.setAudioSamplingRate(camcorderProfile.audioSampleRate);
        Log.e(TAG, "prepareRecorder: audioSampleRate = "+camcorderProfile.audioSampleRate);

        //Step 5: Set the preview output
        mMediaRecorder.setPreviewDisplay(mPreviewView.getHolder().getSurface());

        //Step 6: Prepare configured MediaRecorder
        try {
            mMediaRecorder.prepare();
        } catch (IOException e) {
            releaseRecorder();
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private File getOutPutVideo() {
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        String videoname = new SimpleDateFormat("yyyy_mm_dd_hh_mm_ss").format(new Date());
        File videofile = null;
        try {
            videofile = File.createTempFile(videoname, ".mp4", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return videofile;
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseRecorder();
        releaseCamera();
    }
}
