package com.everywhere.trip.ui.main.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.everywhere.trip.R;
import com.everywhere.trip.base.BaseActivity;
import com.everywhere.trip.base.Constants;
import com.everywhere.trip.presenter.Mpresenter;
import com.everywhere.trip.ui.main.adapter.FmAdapter;
import com.everywhere.trip.ui.main.fragment.HomeFragment;
import com.everywhere.trip.ui.main.fragment.MeterFragment;
import com.everywhere.trip.util.Logger;
import com.everywhere.trip.util.SPutils;
import com.everywhere.trip.view.main.MView;

import java.util.ArrayList;
import java.util.HashSet;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<MView, Mpresenter> implements MView {

    @BindView(R.id.vp)
    ViewPager mVp;
    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.img_my)
    ImageView mImgMy;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.first_item)
    RelativeLayout mFirstItem;
    @BindView(R.id.naview)
    NavigationView mNaview;
    @BindView(R.id. draw)
    DrawerLayout mDraw;
    private ArrayList<Fragment> mListfm;
    private FmAdapter mFmAdapter;
    private String mName;
    private String mStringExtra;
    private RelativeLayout mAattention;
    private TextView tvname;
    private TextView signature;
    private String headername;
    private String headersig;

    public static void startAct(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void toastShort(String msg) {

    }

    @Override
    protected Mpresenter initPresenter() {
        return new Mpresenter();
    }

    @Override
    protected void initView() {

   //点击图片打开侧滑
        initImgView();
        //监听侧滑
        initNaview();
        mListfm = new ArrayList<>();
        mListfm.add(new HomeFragment());
        mListfm.add(new MeterFragment());
        mFmAdapter = new FmAdapter(getSupportFragmentManager(), mListfm);
        mVp.setAdapter(mFmAdapter);
        mTab.setupWithViewPager(mVp);

        mTab.getTabAt(0).setCustomView(R.layout.mtab);
        mTab.getTabAt(1).setCustomView(R.layout.metab);



    }
    private void initNaview() {
        View headerView = mNaview.getHeaderView(0);
        RelativeLayout mCompile = headerView.findViewById(R.id.compile);
        //昵称
        tvname = headerView.findViewById(R.id.header_tvname);
        //签名
        signature = headerView.findViewById(R.id.header_signature);
        //关注
        mAattention = headerView.findViewById(R.id.attention);

        headername = tvname.getText().toString().trim();
        headersig = signature.getText().toString().trim();
        SPutils.put(this,"name",headername);
        SPutils.put(this,"sig",headersig);

         /*跳转到个个人信息界面*/
        mCompile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get2Activity();
            }
        });

        /*关注*/
        mAattention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AattentionActivity.class));
            }
        });

        /**
         * 动态获取权限，Android 6.0 新特性，一些保护权限，除了要在AndroidManifest中声明权限，还要使用如下代码动态获取
         */
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.INTERNET};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }



    }
    /*个人信息*/
    private void get2Activity() {
        Intent intent = new Intent(this, PIMActivity.class);
        intent.putExtra("name",headername);
        intent.putExtra("sig",headersig);
        startActivityForResult(intent, Constants.CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==Constants.CODE  && resultCode==Constants.INITENTCODE){
            String name = data.getStringExtra(Constants.NAME);
            String stringExtra = data.getStringExtra(Constants.SIGNATURE);
            Log.d("wrewgf", "onActivityResult: "+name+stringExtra);
            tvname.setText(name);
            signature.setText(stringExtra);

            SPutils.put(this,"name",name);
            SPutils.put(this,"sig",stringExtra);
        }
    }

    private void initImgView() {
        mImgMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击toolbar上的图片打开侧滑菜单
                mDraw.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
       String name = (String) SPutils.get(this, "name", "");
       String sig = (String) SPutils.get(this, "sig", "");
       tvname.setText(name);
        signature.setText(sig);
    }
}
