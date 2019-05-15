package com.everywhere.trip.ui.main.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.everywhere.trip.R;
import com.everywhere.trip.base.BaseActivity;
import com.everywhere.trip.presenter.EmptyPresenter;
import com.everywhere.trip.view.main.EmptyView;
import com.jaeger.library.StatusBarUtil;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.WebChromeClient;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends BaseActivity<EmptyView, EmptyPresenter> implements EmptyView {
    @BindView(R.id.tool_tv)
    TextView mToolTv;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.web_ll)
    LinearLayout mWebLl;
    private AgentWeb mAgentWeb;

    @Override
    protected EmptyPresenter initPresenter() {
        return new EmptyPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {
    //亮色模式，会将状态栏文字改为黑色
        StatusBarUtil.setLightMode(this);


        mToolbar.setNavigationIcon(R.drawable.back_white);
        mToolTv.setText("");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mWebLl, new LinearLayout.LayoutParams(-1, -1))
               /* .useDefaultIndicator()*/
                .closeIndicator()
                .createAgentWeb()
                .ready()
                .go("https://api.banmi.com/app2017/agreement.html");
   mAgentWeb.getWebCreator().getWebView().setWebChromeClient(new WebChromeClient(){
       @Override
       public void onReceivedTitle(WebView view, String title) {
           if (!TextUtils.isEmpty(title)){
               mToolTv.setText(title);
           }
           super.onReceivedTitle(view, title);
       }
   });

    }
    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @Override
    public void toastShort(String msg) {

    }
}
