package com.example.expedia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expedia.R;
import com.example.expedia.adapter.RecommendationHotelRVAdapter;
import com.example.expedia.sampledata.HotelListDataSample;

public class RecommendHotelListActivity extends AppCompatActivity {
    private RecommendationHotelRVAdapter recommendationHotelRVAdapter = new RecommendationHotelRVAdapter();
    private NestedScrollView scrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_hotel_list);

        Intent intent = getIntent();
        scrollView = findViewById(R.id.scrollView);
        RecyclerView recyclerView = findViewById(R.id.rvRecommendHotelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(RecommendHotelListActivity.this));
        recyclerView.setAdapter(recommendationHotelRVAdapter);
        recommendationHotelRVAdapter.setItems(new HotelListDataSample().getItems());
        scrollView.post(new Runnable(){
            @Override
            public void run(){
                scrollView.fullScroll(scrollView.FOCUS_UP);
            }
        });
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

        if (no == 2){
            tvDeadline.setVisibility(View.VISIBLE);
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
            case 0:
                tvTitle.setText(getText(R.string.special_price_title));
                tvSubtitle.setText(getText(R.string.special_price_subtitle));
                break;

            case 1:
                tvTitle.setText(getText(R.string.every_day_title));
                tvSubtitle.setText(getText(R.string.every_day_subtitle));
                break;
        }
    }
}
