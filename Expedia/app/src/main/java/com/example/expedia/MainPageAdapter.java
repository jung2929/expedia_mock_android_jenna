package com.example.expedia;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainPageAdapter extends FragmentPagerAdapter {
    int mNumOfTabs;
    MainReservation tab1;
    MainPlan tab2;
    MainAccount tab3;
    public MainPageAdapter(FragmentManager fm, int numOfTabs){
        super(fm);
        this.mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                if(tab1 == null) {
                    tab1 = new MainReservation();
                }
                return tab1;
            case 1:
                if(tab2 == null) {
                    tab2 = new MainPlan();
                }
                return tab2;
            case 2:
                if(tab3 == null) {
                    tab3 = new MainAccount();
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
