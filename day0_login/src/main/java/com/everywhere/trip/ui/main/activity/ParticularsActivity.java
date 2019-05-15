package com.everywhere.trip.ui.main.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.everywhere.trip.R;
import com.everywhere.trip.base.BaseActivity;
import com.everywhere.trip.presenter.ParticularsPresenter;
import com.everywhere.trip.ui.main.adapter.MeterFmAdapter;
import com.everywhere.trip.ui.main.fragment.DetailsFM;
import com.everywhere.trip.ui.main.fragment.DynamicConditionFM;
import com.everywhere.trip.view.main.ParticularsView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParticularsActivity extends BaseActivity<ParticularsView, ParticularsPresenter> implements ParticularsView {
    private static final String TAG = "ParticularsActivity";
    @BindView(R.id.tool_back)
    ImageView mToolBack;
    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.pareiculars_img)
    ImageView mPareicularsImg;
    @BindView(R.id.pareiculars_tvname)
    TextView mPareicularsTvname;
    @BindView(R.id.pareiculars_imgfollow)
    ImageView mPareicularsImgfollow;
    @BindView(R.id.pareiculars_tvatteation)
    TextView mPareicularsTvatteation;
    @BindView(R.id.pareiculars_address)
    TextView mPareicularsAddress;
    @BindView(R.id.pareiculars_author)
    TextView mPareicularsAuthor;
    @BindView(R.id.tv_introduction)
    TextView mTvIntroduction;
     @BindView(R.id.VP)
     ViewPager mVP;
    /*@BindView(R.id.rl)
    LinearLayout mRl;*/
    /*@BindView(R.id.particulars_fm)
     Fragment particularsFm;*/
    @BindView(R.id.rl)
    LinearLayout rl;
    private String[] arr = {"动态", "线路"};
    private ArrayList<Fragment> mListFm;
    private MeterFmAdapter adapter;
    private String id;


    @Override
    protected ParticularsPresenter initPresenter() {
        return new ParticularsPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_particulars;

    }

    @Override
    public void toastShort(String msg) {

    }

    @Override
    protected void initView() {


        mToolBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String photo = intent.getStringExtra("photo");
        String following = intent.getStringExtra("following");
        String location = intent.getStringExtra("location");
        String occupation = intent.getStringExtra("occupation");
        String introduction = intent.getStringExtra("introduction");
        id = intent.getStringExtra("id");
        Log.d(TAG, "initView: " + id);

        mPareicularsTvname.setText(name);
        Glide.with(this).load(photo).into(mPareicularsImg);
        mPareicularsAddress.setText(location);
        mPareicularsTvatteation.setText(occupation);
        mPareicularsAuthor.setText(following + "关注人数");
        mTvIntroduction.setText("简介:" + introduction);

        initFragment();
    }


    private void initFragment() {
        mListFm = new ArrayList<>();
        //动态
        DynamicConditionFM fm = new DynamicConditionFM();
        mListFm.add(fm);
        //线路
        mListFm.add(new DetailsFM());
        adapter = new MeterFmAdapter(getSupportFragmentManager(), mListFm, arr);
        mVP.setAdapter(adapter);
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        fm.setArguments(bundle);
        mTab.setupWithViewPager(mVP);

      /*  mTab.addTab(mTab.newTab().setText("动态"));
        mTab.addTab(mTab.newTab().setText("线路"));
        //获取碎片管理器
        FragmentManager manager = getFragmentManager();
        //开始事物
        FragmentTransaction tran = manager.beginTransaction();
        //构建fragment对象
        //动态
        DynamicConditionFM Dyfm = new DynamicConditionFM();
        //线路
        DetailsFM Detailsfm = new DetailsFM();
        //替换容器中内容
         tran.add(R.id.rl,Dyfm);
         tran.add(R.id.rl,Detailsfm);
         //提交事物
         tran.commit();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        Dyfm.setArguments(bundle);
*/
    }


}
