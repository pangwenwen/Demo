package com.example.lenovo.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int CAMERA_REQUEST_CODE = 1;

    private static final String INTENT_CAMERA = MediaStore.ACTION_IMAGE_CAPTURE;
    private static final String INTENT_VIDEO = MediaStore.ACTION_VIDEO_CAPTURE;

    private String mImgPath;

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File file = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Log.e(TAG, "onCreate: path1 = "+file.getAbsolutePath());
        mImageView = (ImageView)findViewById(R.id.imageView);

    }

    public void takephoto(View view){

        //ONE :
        /*Intent intent = new Intent(INTENT_CAMERA);
        if (intent.resolveActivity(getPackageManager()) != null){
             startActivityForResult(intent,CAMERA_REQUEST_CODE);
        }*/

        //TWO :
        Intent intent = new Intent(INTENT_CAMERA);
        if (intent.resolveActivity(getPackageManager()) != null){
            File photopath = creatImgPath();
            Uri photoURI = FileProvider.getUriForFile(this,
                    "com.lenovo.myapplication.fileprovider",
                    photopath);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        }
    }



    public void takepvideo(View view){
        Intent intent = new Intent(INTENT_VIDEO);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //ONE :
        /*if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(bitmap);
        }*/

        //TWO :
        if (requestCode == CAMERA_REQUEST_CODE){
            Log.e(TAG, " onActivityResult = ");
            addToGallery();
            scaleImg( mImgPath);
        }

    }

    private File creatImgPath(){
        String timeTamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File file = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String imgName = "JPEG_"+timeTamp+"_";
        File imagePath = null;
        try {
            imagePath = File.createTempFile(imgName,".jpg",file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mImgPath = imagePath.getAbsolutePath();
        Log.e(TAG, " mImgPath = "+mImgPath);
        return imagePath;
    }

    private void addToGallery(){
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri photoUri = Uri.fromFile(new File(mImgPath));
        intent.setData(photoUri);
        this.sendBroadcast(intent);
    }

    private void scaleImg(String path){
        int targetWidth = mImageView.getWidth();
        int targetHeight = mImageView.getHeight();
        BitmapFactory.Options options = new BitmapFactory.Options();
        //如果将这个值置为true，那么在解码的时候将不会返回bitmap，只会返回这个bitmap的尺寸。这个属性的目的是，如果你只想知道一个bitmap的尺寸，但又不想将其加载到内存时
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path,options);
        int oriWidth = options.outWidth;
        int oriHeight = options.outHeight;

        int imgScale = Math.max(oriHeight/targetHeight,oriWidth/targetWidth);
        Log.e(TAG, " imgScale = "+imgScale);
        options.inJustDecodeBounds = false;
        //再内存不足时是否被回收
        options.inPurgeable = true;
        options.inSampleSize = imgScale;

        Bitmap bitmap = BitmapFactory.decodeFile(path,options);
        mImageView.setImageBitmap(bitmap);
    }
}
