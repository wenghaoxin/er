package com.example.day02_chat.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class FMAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mListfm;
    private String [] arr;

    public FMAdapter(FragmentManager fm, ArrayList<Fragment> mListfm, String[] arr) {
        super(fm);
        this.mListfm = mListfm;
        this.arr = arr;
    }

    @Override
    public Fragment getItem(int i) {
        return mListfm.get(i);
    }

    @Override
    public int getCount() {
        return mListfm.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return arr[position];
    }
}
