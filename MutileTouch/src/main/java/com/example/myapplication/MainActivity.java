package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
        /*switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                int actionIndex = event.getActionIndex();
                int pointCount = event.getPointerCount();
                int pointId = event.getPointerId(actionIndex);
                int pointIndex = event.findPointerIndex(pointId);
                Log.e(TAG, "onTouchEvent: ACTION_DOWN ");

                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
        }*/
    }
}
