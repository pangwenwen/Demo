package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    int MODE_DEFAULT_PADDING = 20;
    int width;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* DisplayMetrics metrics = getResources().getDisplayMetrics();
        width = metrics.widthPixels;

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_mode_panorama_focus);
        int bitmapWidth = bitmap.getWidth();
        int modewidth = bitmapWidth + MODE_DEFAULT_PADDING * 2;

        int marginbottom = (width - 4*modewidth)/3;
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        Log.e(TAG, "onCreate: marginbottom = "+marginbottom );
        mLayoutParams.setMargins(0,0,0,0);

        ImageView imageView1 = (ImageView) findViewById(R.id.img1);
        ImageView imageView2 = (ImageView) findViewById(R.id.img2);
        ImageView imageView3 = (ImageView) findViewById(R.id.img3);
        ImageView imageView4 = (ImageView) findViewById(R.id.img4);

        imageView1.setLayoutParams(mLayoutParams);
        imageView1.setPadding(MODE_DEFAULT_PADDING,MODE_DEFAULT_PADDING,MODE_DEFAULT_PADDING,MODE_DEFAULT_PADDING);

        imageView2.setLayoutParams(mLayoutParams);
        imageView3.setPadding(MODE_DEFAULT_PADDING,MODE_DEFAULT_PADDING,MODE_DEFAULT_PADDING,MODE_DEFAULT_PADDING);

        imageView3.setLayoutParams(mLayoutParams);
        imageView3.setPadding(MODE_DEFAULT_PADDING,MODE_DEFAULT_PADDING,MODE_DEFAULT_PADDING,MODE_DEFAULT_PADDING);

        imageView4.setLayoutParams(mLayoutParams);
        imageView4.setPadding(MODE_DEFAULT_PADDING,MODE_DEFAULT_PADDING,MODE_DEFAULT_PADDING,MODE_DEFAULT_PADDING);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 0);*/
        /*imageView1.setLayoutParams(params);
        imageView2.setLayoutParams(params);
        imageView3.setLayoutParams(params);
        imageView4.setLayoutParams(params);*/


     /*   TextView textView = (TextView) findViewById(R.id.textView);

        boolean isApp = true;
        boolean isBpp = false;
        boolean a = isBpp = isApp;
        Log.e("haha","a = "+a+"; isBpp = "+isBpp);


        Father father = new Father();
        Father father1 = father;
        Log.e("haha","father == fatherq:"+(father == father1)+"; father.hashcode() = "+father.hashCode()+"; father1.hashcode = "+father1.hashCode()+"; father.equals(father1) :"+father.equals(father1));
    */



        /*ProgressDialog dialog = new ProgressDialog(this);
        dialog.s
        dialog.show();*/


    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

    }
}
