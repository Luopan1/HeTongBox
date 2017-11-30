package com.test720.hetong.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author LuoPan on 2017/11/28 15:27.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> mCars;

    public MyPagerAdapter(List<Fragment> mCars, FragmentManager fm) {
        super(fm);
        this.mCars = mCars;
    }

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return mCars.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mCars.get(position);
    }


}
