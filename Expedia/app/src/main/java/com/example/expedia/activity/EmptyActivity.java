package com.example.expedia.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.expedia.R;

public class EmptyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        Handler hd = new Handler();
        hd.postDelayed(new emptyHandler(), 500);

    }

    private class emptyHandler implements Runnable{
        public void run(){
          EmptyActivity.this.finish();
        }
    }
}
