package com.example.day_hander;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


public class BaseApp extends Application {
    public static RefWatcher mWatcher;
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysi
            // You should not init your app in this process.
            return;
        }
        mWatcher = LeakCanary.install(this);
        // Normal app init code...
    }

}
