package com.example.expedia.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.expedia.adapter.LogInPageAdapter;
import com.example.expedia.R;

public class LogInActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TabLayout tabs = findViewById(R.id.login_tab);
        tabs.addTab(tabs.newTab().setText("로그인"));
        tabs.addTab(tabs.newTab().setText("회원가입"));

        ImageView cancelImage = findViewById(R.id.login_cancel_btn);
        cancelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ViewPager viewPager = findViewById(R.id.login_viewPager);
        LogInPageAdapter pageAdapter = new LogInPageAdapter(getSupportFragmentManager(),2);
        viewPager.setAdapter(pageAdapter);

        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
    }
}
