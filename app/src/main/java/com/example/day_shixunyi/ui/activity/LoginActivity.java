package com.example.day_shixunyi.ui.activity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.day_shixunyi.R;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 发送验证码
     * <p>
     * 5cc7a81c4ca3577fb4000629 key
     */
    private Button mBtCode;
    private ImageView mImgWechat;
    private ImageView mImgSina;
    private ImageView mImgQq;
    /**
     * 请输入手机号码
     */
    private EditText mEtPhone;
    private ImageView mImgBack;
    private EditText mEt1;
    private EditText mEt2;
    private EditText mEt3;
    private EditText mEt4;
    private LinearLayout mVerifyLl;
    private LinearLayout mLoginLl;
    /**
     * 你好，为了获取更好的服务
     */
    private TextView mTitle;
    /**
     * 请登录到处旅行
     */
    private TextView mMsg;
    /**
     * or
     */
    private TextView mOr;
    /**
     * 三方登录
     */
    private TextView mTvQq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
      /*  if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }*/
        initView();
       /* initListener();*/
    }

   /* private void initListener() {
        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 11) {
                    mBtCode.setBackground(getResources().getDrawable(R.mipmap.button_highlight));
                } else {
                    mBtCode.setBackground(getResources().getDrawable(R.mipmap.button_unavailable));
                }
            }
        });
    }
*/
    private void initView() {
        mBtCode = (Button) findViewById(R.id.bt_code);
        mBtCode.setOnClickListener(this);
        mImgWechat = (ImageView) findViewById(R.id.img_wechat);
        mImgSina = (ImageView) findViewById(R.id.img_sina);
        mImgQq = (ImageView) findViewById(R.id.img_qq);
        mImgWechat.setOnClickListener(this);
        mImgSina.setOnClickListener(this);
        mImgQq.setOnClickListener(this);
        mEtPhone = (EditText) findViewById(R.id.et_phone);


        mImgBack = (ImageView) findViewById(R.id.img_back);
        mEt1 = (EditText) findViewById(R.id.et1);
        mEt2 = (EditText) findViewById(R.id.et2);
        mEt3 = (EditText) findViewById(R.id.et3);
        mEt4 = (EditText) findViewById(R.id.et4);
        mVerifyLl = (LinearLayout) findViewById(R.id.verify_ll);
        mLoginLl = (LinearLayout) findViewById(R.id.login_ll);
        mImgBack.setOnClickListener(this);
        mEt1.setOnClickListener(this);
        mEt2.setOnClickListener(this);
        mEt3.setOnClickListener(this);
        mEt4.setOnClickListener(this);
        mVerifyLl.setOnClickListener(this);
        mTitle = (TextView) findViewById(R.id.title);
        mLoginLl.setOnClickListener(this);
        mTitle.setOnClickListener(this);
        mMsg = (TextView) findViewById(R.id.msg);
        mEtPhone.setOnClickListener(this);
        mOr = (TextView) findViewById(R.id.or);
        mTvQq = (TextView) findViewById(R.id.tv_qq);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bt_code:
                String s = mEtPhone.getText().toString();
                if (s.length() == 11 && s.matches("1[0-9]{10}")) {
                    mLoginLl.setVisibility(View.GONE);
                    mVerifyLl.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.img_wechat:

                break;
            case R.id.img_sina:

                break;
            case R.id.img_qq:
               /* UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.QQ, authListener);*/
                break;
            case R.id.img_back:
             /*   mVerifyLl.setVisibility(View.GONE);
                mLoginLl.setVisibility(View.VISIBLE);*/
                break;
            case R.id.et1:
                break;
            case R.id.et2:
                break;
            case R.id.et3:
                break;
            case R.id.et4:
                break;
            case R.id.verify_ll:
                break;
            case R.id.login_ll:
                break;
            case R.id.title:
                break;
            case R.id.et_phone:
                break;
        }
    }


   /* UMAuthListener authListener = new UMAuthListener() {
        *//**
         * @desc 授权开始的回调
         * @param platform 平台名称
         *//*
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        *//**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         *//*
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            Toast.makeText(LoginActivity.this, "成功了", Toast.LENGTH_LONG).show();

        }

        *//**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         *//*
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            Toast.makeText(LoginActivity.this, "失败：" + t.getMessage(),                                     Toast.LENGTH_LONG).show();
        }

        *//**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         *//*
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(LoginActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };*/

}
