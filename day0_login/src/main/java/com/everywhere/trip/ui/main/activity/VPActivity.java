package com.everywhere.trip.ui.main.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.everywhere.trip.R;
import com.everywhere.trip.widget.PreviewIndicator;

import java.security.interfaces.ECKey;
import java.util.ArrayList;

public class VPActivity extends AppCompatActivity {

    private ViewPager mVp;
    private ArrayList<View> mView;
    private PreviewIndicator mPreview;
    private SharedPreferences sp;
    private Button btexe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vp);
        initView();
    }

    private void initView() {
        mVp = (ViewPager) findViewById(R.id.vp);
        mPreview = (PreviewIndicator) findViewById(R.id.preview);

        sp = getSharedPreferences("123", MODE_PRIVATE);
        boolean b = sp.getBoolean("123", false);
        if (b){
            startActivity(new Intent(VPActivity.this,LoginActivity.class));
            finish();
        }

        mView = new ArrayList<>();
        View viewone = LinearLayout.inflate(VPActivity.this, R.layout.vpone, null);
        View viewtwo = LinearLayout.inflate(VPActivity.this, R.layout.vptwo, null);
        View viewthree = LinearLayout.inflate(VPActivity.this, R.layout.vpthree, null);
        mView.add(viewone);
        mView.add(viewtwo);
        mView.add(viewthree);

        initAdapter();

        initLister();

        btexe = viewthree.findViewById(R.id.bt_exe);
       btexe.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(VPActivity.this,LoginActivity.class));
               SharedPreferences.Editor edit = sp.edit();
               edit.putBoolean("123",true);
               edit.commit();
               finish();
           }
       });
    }

    private void initLister() {
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==2){
                    mPreview.setVisibility(View.INVISIBLE);
                    btexe.setVisibility(View.VISIBLE);
                }else {
                    mPreview.setVisibility(View.VISIBLE);
                    btexe.setVisibility(View.INVISIBLE);
                }
                 mPreview.setSelected(position);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initAdapter() {
        mVp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mView.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView(mView.get(position));
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                View view = mView.get(position);
                container.addView(view);
                return view;
            }
        });
    }
}
