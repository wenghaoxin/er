package com.everywhere.trip.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.everywhere.trip.R;
import com.everywhere.trip.base.Constants;
import com.everywhere.trip.bean.WebData;
import com.everywhere.trip.net.ApiHome;
import com.just.agentweb.AgentWeb;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebJsActivity extends AppCompatActivity {

    private static final String TAG = "WebJsActivity";
    private ImageView mWebBlack;
    private AgentWeb mAgentWeb;
    private LinearLayout mLl;
    /**
     * 艺术之旅
     */
    private TextView mWebjsTv;
    private String cardURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_js);
        initView();
        //这是hot
    }

    private void initView() {
        mLl = (LinearLayout) findViewById(R.id.ll);
        mWebBlack = (ImageView) findViewById(R.id.web_black);
        mWebjsTv = (TextView) findViewById(R.id.webjs_tv);


        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String title = intent.getStringExtra("title");

        int q=3;
        int a=4;
        int y=6;


        mWebjsTv.setText(title);


        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((LinearLayout) mLl, new LinearLayout.LayoutParams(-1, -1))
                .closeIndicator()
                .createAgentWeb()
                .ready()
                .go(url+"?os=android");
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new AndroidInterface(mAgentWeb, this));


        mWebBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WebJsActivity.this, MainActivity.class));
                finish();
            }
        });
        
    }

}
