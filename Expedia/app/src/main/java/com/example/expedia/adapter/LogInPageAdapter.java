package com.example.expedia.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.expedia.fragment.LogInFragment;
import com.example.expedia.fragment.LogInSignUpFragment;

public class LogInPageAdapter extends FragmentPagerAdapter {
    int mNumOfTabs;
    public LogInPageAdapter(FragmentManager fm, int numOfTabs){
        super(fm);
        this.mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new LogInFragment();
            case 1:
                return new LogInSignUpFragment();

            default:
                return null;
        }
        //return null;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
