package com.example.petya.handler;

import android.os.Handler;

public class MyObservable {

    private static MyCallable mCallable;
    private Handler mHandler;
    private Handler mMainHandler;
    private Callback mCallback;
    private Integer Buffer;

    public static MyObservable from(MyCallable callable) {
        mCallable = callable;
        return new MyObservable();
    }

    void subscribe(Callback myCallback) {
        mCallback = myCallback;

        if (mHandler == null) {
            mCallback.onNext(mCallable.call());
        } else {
            if (mMainHandler == null) {
                mHandler.post(subscribeOnRun);
            } else {
                mHandler.post(observeOnRun);
            }
        }
    }

    MyObservable subscribeOn(Handler handler) {
        mHandler = handler;
        return this;
    }

    MyObservable observeOn(Handler mainHandler) {
        mMainHandler = mainHandler;
        return this;
    }

    private Runnable subscribeOnRun = new Runnable() {
        public void run() {
            mCallback.onNext(mCallable.call());
        }
    };

    private Runnable observeOnRun = new Runnable() {
        public void run() {
            Buffer = mCallable.call();
            mMainHandler.post(observeOnRunMain);
        }
    };

    private Runnable observeOnRunMain = new Runnable() {
        public void run() {
            mCallback.onNext(Buffer);
        }
    };

}
