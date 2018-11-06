package com.example.petya.handler;

import android.os.Looper;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public class MyCallable implements Callable<Integer>{

    private final String data;

    MyCallable(String data) {
        this.data = data;
    }

    @Override
    public Integer call() {
        return parseInt(data);
    }

    private int parseInt(String text) {
        String thread = Looper.getMainLooper().isCurrentThread() ? "UI" : "IO";
        Log.d("myLogs", "method parseInt " + "[" + thread + "]");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //TODO: проверять исключения
        return Integer.parseInt(text);
    }
}
