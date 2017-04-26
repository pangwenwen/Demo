package com.example.myapplication;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    ImageView cx_img;
    ImageView l9_img;

    TextView cx_text;
    TextView l9_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*cx_img = (ImageView) findViewById(R.id.img);
        l9_img = (ImageView)findViewById(R.id.img1);

        cx_text = (TextView) findViewById(R.id.cx_text);
        l9_text = (TextView) findViewById(R.id.l9_text);

        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        String dcim_path = file.getAbsolutePath();
        String cx_path = dcim_path+File.separator+"Camera"+File.separator+"cx.jpg";
        String l9_path = dcim_path+File.separator+"Camera"+File.separator+"L9_.jpg";

        File cx_file = new File(cx_path);
        File l9_file = new File(l9_path);

        Bitmap cx_bitmap = BitmapFactory.decodeFile(cx_path);
        Bitmap l9_bitmap = BitmapFactory.decodeFile(l9_path);

        ExifInterface cx_ex = null;
        ExifInterface l9_ex = null;
        try {
            cx_ex = new ExifInterface(cx_path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            l9_ex = new ExifInterface(l9_path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String cx_info = cx_ex.getAttribute(ExifInterface.TAG_ORIENTATION);
        String l9_info = l9_ex.getAttribute(ExifInterface.TAG_ORIENTATION);

        l9_text.setText(l9_info);
        cx_text.setText(cx_info);

        cx_img.setImageBitmap(cx_bitmap);
        l9_img.setImageBitmap(l9_bitmap);*/

        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("hahaha").setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                MainActivity.this.finish();
            }
        }).create().show();*/
    }
}
