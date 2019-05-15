package com.everywhere.trip.ui.main.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class MeterFmAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> mListFm;
    private  String [] arr;

    public MeterFmAdapter(FragmentManager fm, ArrayList<Fragment> mListFm, String[] arr) {
        super(fm);
        this.mListFm = mListFm;
        this.arr = arr;
    }
    @Override
    public Fragment getItem(int position) {
        return mListFm.get(position);
    }

    @Override
    public int getCount() {
        return mListFm.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return arr[position];
    }
}
