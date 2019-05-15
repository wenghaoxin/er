package com.everywhere.trip.ui.main.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class FmAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mListfm;

    public FmAdapter(FragmentManager fm, ArrayList<Fragment> mListfm) {
        super(fm);
        this.mListfm = mListfm;
    }
    @Override
    public Fragment getItem(int position) {
        return mListfm.get(position);
    }

    @Override
    public int getCount() {
        return mListfm.size();
    }

}
