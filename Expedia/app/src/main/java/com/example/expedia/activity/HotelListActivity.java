package com.example.expedia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.expedia.R;
import com.example.expedia.adapter.HotelListRVAdapter;
import com.example.expedia.datamanager.HttpConnection;
import com.example.expedia.datamanager.HotelListCallback;

public class HotelListActivity extends AppCompatActivity {
    private HotelListRVAdapter hotelListRVAdapter = new HotelListRVAdapter();
    //private NestedScrollView scrollView;
    private final int UNDER_80000 = 0;
    private final int EVERYDAY_SPECIAL_PRICE = 1;
    private final int DEADLINE = 2;
    private final int SEARCH_LIST = 3;
    private HttpConnection httpConnection;
    private HotelListCallback callback;
    private Intent intent;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_hotel_list);

        intent = getIntent();
        //scrollView = findViewById(R.id.scrollView);
        RecyclerView recyclerView = findViewById(R.id.rvRecommendHotelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(HotelListActivity.this));
        recyclerView.setAdapter(hotelListRVAdapter);
        int no = intent.getIntExtra("no", 10);
        TextView tvTitle = findViewById(R.id.title);
        TextView tvSubtitle = findViewById(R.id.subtitle);
        TextView tvDeadline = findViewById(R.id.deadline);
        ImageView xImage = findViewById(R.id.cancel_btn);
        xImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView tvAgreement = findViewById(R.id.agreement);

        if (no == DEADLINE || no == SEARCH_LIST){
            tvDeadline.setVisibility(View.VISIBLE);
            if (no == SEARCH_LIST){
                tvDeadline.setText("호텔 검색");
            }
            tvTitle.setVisibility(View.GONE);
            tvSubtitle.setVisibility(View.GONE);
            tvAgreement.setVisibility(View.GONE);
        }else{
            tvTitle.setVisibility(View.VISIBLE);
            tvSubtitle.setVisibility(View.VISIBLE);
            tvAgreement.setVisibility(View.VISIBLE);
            tvDeadline.setVisibility(View.GONE);
        }
        switch(no){
            case UNDER_80000:
                tvTitle.setText(getText(R.string.special_price_title));
                tvSubtitle.setText(getText(R.string.special_price_subtitle));
                break;

            case EVERYDAY_SPECIAL_PRICE:
                tvTitle.setText(getText(R.string.every_day_title));
                tvSubtitle.setText(getText(R.string.every_day_subtitle));
                break;
        }
        sendData(no);
        /*scrollView.post(new Runnable(){
            @Override
            public void run(){
                scrollView.fullScroll(scrollView.FOCUS_UP);
            }
        });*/
    }


    private void sendData(int no){
        switch (no){
            case UNDER_80000:
                httpConnection = new HttpConnection(HotelListActivity.this, "discounted_80000","get");
                break;
            case EVERYDAY_SPECIAL_PRICE:
                httpConnection = new HttpConnection(HotelListActivity.this, "discounted_today","get");
                break;
            case DEADLINE:
                httpConnection = new HttpConnection(HotelListActivity.this, "discounted_fin","get");
                break;
            case SEARCH_LIST:
                String url = intent.getStringExtra("url");
                httpConnection = new HttpConnection(HotelListActivity.this, url,"get");
                break;
        }
        callback = new HotelListCallback(HotelListActivity.this, hotelListRVAdapter);

        new Thread() {
            public void run() {
                httpConnection.requestWebServer(callback);
            }
        }.start();
    }
}
