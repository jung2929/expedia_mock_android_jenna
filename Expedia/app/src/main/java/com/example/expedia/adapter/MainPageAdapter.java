package com.example.expedia.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.expedia.fragment.MainAccountFragment;
import com.example.expedia.fragment.MainPlanFragment;
import com.example.expedia.fragment.MainReservationFragment;

public class MainPageAdapter extends FragmentPagerAdapter {
    private int mNumOfTabs;

    public MainPageAdapter(FragmentManager fm, int numOfTabs){
        super(fm);
        this.mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new MainReservationFragment();
            case 1:
                return new MainPlanFragment();
            case 2:

                return new MainAccountFragment();

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
