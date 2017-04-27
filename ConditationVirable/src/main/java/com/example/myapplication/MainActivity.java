package com.example.myapplication;

import android.os.ConditionVariable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /**
     * ConditionVariable 用于线程同步的类
     *     block:阻塞当前线程，直到条件为open或block 超时
     *     close:置为初始状态，（一般用在block前，因为open后如果不置为初始状态，即使block也会即刻返回）
     *     open：执行被block的线程
     */
    private ConditionVariable mCV = new ConditionVariable();
    private StringBuffer mSbf = new StringBuffer();
    private TextView mTextView;

    private Button mBtn;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            mTextView.setText(mSbf);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.tv);
        mBtn = (Button) findViewById(R.id.button);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    mCV.close();
                    mCV.block(3000);
                    mSbf.append("s");
                    mHandler.sendEmptyMessage(1);
                }
            }
        }).start();

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCV.open();
            }
        });

    }








}
