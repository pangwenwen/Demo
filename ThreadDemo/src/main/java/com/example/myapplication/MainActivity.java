package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * wait notify 方法必须在synchronized代码块中(object 的方法)
 *   如wait的线程可以被notify所唤醒，必须 同步同一个对象，且调用同一个对象的wait,notify 方法
 * interrupt 方法 wait中的线程被中断 （thread 的方法）
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    Object object = new Object();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*wait notify ntrrupt
        WaitThread waitThread1 = new WaitThread("1 thread");
        WaitThread waitThread2 = new WaitThread("2 thread");
        waitThread1.start();
        waitThread2.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        waitThread1.interrupt();

        NotifyThread notifyThread = new NotifyThread("notify thread");
        notifyThread.start();*/

        YeildThread thread = new YeildThread();
        thread.setPriority(4);
        thread.start();
        /*try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        Log.e(TAG, "onCreate: pro  ="+Thread.currentThread().getPriority());
        for (int i = 0;i<100;i++){
            Log.e(TAG, "onCreate: "+i);
        }
    }

    public class YeildThread extends Thread{
        @Override
        public void run() {
            Log.e(TAG, "run: YeildThread start pro = "+getPriority() );
            for (int i = 0;i<10;i++){
                if (i == 1){
                    yield();
                }
                Log.e(TAG, "run: "+i);
            }
        }
    }

   /* public  class  WaitThread extends Thread{
        public WaitThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (this){
                try {
                    Log.e(TAG, Thread.currentThread().getName()+"waitWork: wait start");
                    //object.wait();
                    wait();
                    Log.e(TAG, Thread.currentThread().getName()+"waitWork: wait end");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public  class  NotifyThread extends Thread{
        public NotifyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (this){
                    Log.e(TAG, Thread.currentThread().getName()+"notifyWork: notify stary");
                    //object.notifyAll();
                notifyAll();
                    Log.e(TAG, Thread.currentThread().getName()+"notifyWork: notify end");

            }
        }
    }*/
}
