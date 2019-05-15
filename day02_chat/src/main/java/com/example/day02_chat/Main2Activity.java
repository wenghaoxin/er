package com.example.day02_chat;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.day02_chat.fragment.FMAdapter;
import com.example.day02_chat.fragment.InFragment;
import com.example.day02_chat.fragment.MesFragment;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    private TabLayout mTab;
    private ViewPager mVp;
    private ArrayList<Fragment> mListfm;
    private String [] arr={"通知","消息"};
    private FMAdapter mFmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        initFragment();
    }

    private void initFragment() {
        mListfm = new ArrayList<>();
        mListfm.add(new InFragment());
        mListfm.add(new MesFragment());
        mFmAdapter = new FMAdapter(getSupportFragmentManager(), mListfm, arr);
        mVp.setAdapter(mFmAdapter);

    }

    private void initView() {
        mTab = (TabLayout) findViewById(R.id.tab);
        mVp = (ViewPager) findViewById(R.id.vp);
        mTab.setupWithViewPager(mVp);

    }

}
