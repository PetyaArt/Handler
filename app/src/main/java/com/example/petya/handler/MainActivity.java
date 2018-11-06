package com.example.petya.handler;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private Handler mOtherHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler mainHandler = new Handler();

        LooperThread looperThread = new LooperThread();
        looperThread.start();

        MyObservable.from(new MyCallable("5"))
                .subscribeOn(mOtherHandler)
                .observeOn(mainHandler)
                .subscribe(new Callback<Integer>() {
                    @Override
                    public void onNext(Integer number) {
                        String thread = Looper.getMainLooper().isCurrentThread() ? "UI" : "IO";
                        Log.d("myLogs", "Answer: " + String.valueOf(number) + " [" + thread + "]");
                    }
                });
    }

    private class LooperThread extends Thread {
        @Override
        public void run() {
            Looper.prepare();
            mOtherHandler = new Handler();
            Looper.loop();
        }
    }

    @Override
    protected void onDestroy() {
        mOtherHandler.getLooper().quit();
        super.onDestroy();
    }
}
