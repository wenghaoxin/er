package com.example.day_hander;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG, "handleMessage: " + msg.what);
            mTv.setText("收到发送的数据:" + msg.what);

        }
    };
    /**
     * Hello World!
     */
    public TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniper();
        initView();
    }

    private void initView() {
        mTv = (TextView) findViewById(R.id.tv);
        //发送延时消息,如果要拿LeakCanary检测,延时需要较大
        mHandler.sendEmptyMessageDelayed(500, 8000);
    }

    private void iniper() {
        //集成LeakCanary检测使用sd卡权限
        String[] per = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this, per, 100);
    }
    //第一种
  /*  @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }*/
}
