package com.example.day_shixunyi.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.day_shixunyi.R;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

public class WXEntryActivity extends WXCallbackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);
    }
}
