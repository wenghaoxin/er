package com.everywhere.trip.ui.main.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;

import com.everywhere.trip.R;
import com.everywhere.trip.base.BaseActivity;
import com.everywhere.trip.base.Constants;
import com.everywhere.trip.presenter.LoginPresenter;
import com.everywhere.trip.ui.main.fragment.LoginOrBindFragment;
import com.everywhere.trip.view.main.LoginView;
import com.umeng.socialize.UMShareAPI;

public class LoginActivity extends BaseActivity<LoginView, LoginPresenter> implements LoginView {
public  static final  int TYPR_LOGIN=0;
public  static final  int TYPR_PHONE=1;
private int mType;
public static final String TAG ="LoginorBindFragment";

    /*
    * 启动当前Activity
    * type=0,登录界面
    * type=1 绑定手机界面
    * */
    public static void startAct(Context context, int type){
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(Constants.TYPE,type);
        context.startActivity(intent);
    }
    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        mPresenter.getVerifyCode();
    }

    @Override
    protected void initView() {
            getItentData();
            FragmentManager manager = getSupportFragmentManager();
            LoginOrBindFragment fragment=LoginOrBindFragment.newIntance(mType);
            manager.beginTransaction().add(R.id.fl_container, fragment,TAG).commit();


            if (Build.VERSION.SDK_INT>Build.VERSION_CODES.M){
                String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,};
                ActivityCompat.requestPermissions(this,mPermissionList,123);
            }
    }

    private void getItentData() {
        mType = getIntent().getIntExtra(Constants.TYPE, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void toastShort(String msg) {

    }
}
