package com.example.day_hander;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.logging.LogRecord;


public class MyHandler extends Handler {
    private static final String TAG = "MyHandler";
    private WeakReference<MainActivity> mReference;

    public MyHandler(MainActivity activity) {
        mReference = new WeakReference<>(activity);
    }
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        Log.d(TAG, "handleMessage: " + msg.what);
        if (mReference.get() != null) {
            mReference.get().mTv.setText(msg.what + "");
        }
    }

}
