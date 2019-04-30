package com.example.expedia.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.expedia.MyApplication;
import com.example.expedia.adapter.MainPageAdapter;
import com.example.expedia.R;
import com.example.expedia.fragment.MainPlanFragment;
import com.example.expedia.fragment.MainReservationFragment;

public class MainActivity extends AppCompatActivity {


    private ViewPager viewPager;
    MenuItem prevMenuItem;
    private boolean prev_loginStatus;
    private MainPageAdapter mpa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prev_loginStatus = MyApplication.isLogInStatus();
        viewPager = findViewById(R.id.MainViewPager);
        mpa = new MainPageAdapter(getSupportFragmentManager(), 3);
        viewPager.setAdapter(mpa);
        viewPager.setOffscreenPageLimit(3);
        final BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }
            @Override
            public void onPageSelected(int i) {
                if(prev_loginStatus != MyApplication.isLogInStatus()){
                    MainReservationFragment mainReservationFragment = (MainReservationFragment)getSupportFragmentManager().findFragmentByTag("android:switcher:"+viewPager.getId()+":"+mpa.getItemId(0));
                    if (mainReservationFragment != null) {
                        mainReservationFragment.checkLoginStatus();
                    }
                    MainPlanFragment mainPlanFragment = (MainPlanFragment)getSupportFragmentManager().findFragmentByTag("android:switcher:"+viewPager.getId()+":"+mpa.getItemId(1));
                    if (mainPlanFragment != null) {
                        mainPlanFragment.checkLoginStatus();
                    }
                    prev_loginStatus = MyApplication.isLogInStatus();
                }

                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else {
                    navigation.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: "+ i);
                navigation.getMenu().getItem(i).setChecked(true);
                prevMenuItem = navigation.getMenu().getItem(i);
            }
            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        prev_loginStatus = MyApplication.isLogInStatus();
    }
}
