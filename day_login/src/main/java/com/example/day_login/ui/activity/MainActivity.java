package com.example.day_login.ui.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.day_login.R;
import com.example.day_login.base.BaseActivity;
import com.example.day_login.base.BasePresenter;
import com.example.day_login.presenter.LoginPresenter;

public class MainActivity extends BaseActivity {

    @butterknife.BindView(R.id.fmlayout)
    FrameLayout fmlayout;

    @Override
    protected BasePresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

}
