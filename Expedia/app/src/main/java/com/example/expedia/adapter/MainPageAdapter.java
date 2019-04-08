package com.example.expedia.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.expedia.fragment.MainAccountFragment;
import com.example.expedia.fragment.MainPlanFragment;
import com.example.expedia.fragment.MainReservationFragment;

public class MainPageAdapter extends FragmentPagerAdapter {
    int mNumOfTabs;
    MainReservationFragment tab1;
    MainPlanFragment tab2;
    MainAccountFragment tab3;
    public MainPageAdapter(FragmentManager fm, int numOfTabs){
        super(fm);
        this.mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                if(tab1 == null) {
                    tab1 = new MainReservationFragment();
                }
                return tab1;
            case 1:
                if(tab2 == null) {
                    tab2 = new MainPlanFragment();
                }
                return tab2;
            case 2:
                if(tab3 == null) {
                    tab3 = new MainAccountFragment();
                }
                return tab3;

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
