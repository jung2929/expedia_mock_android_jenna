package com.example.expedia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expedia.R;
import com.example.expedia.adapter.RecommendationHotelRVAdapter;
import com.example.expedia.data.HotelListData;
import com.example.expedia.sampledata.HotelListDataSample;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecommendHotelListActivity extends AppCompatActivity {
    private RecommendationHotelRVAdapter recommendationHotelRVAdapter = new RecommendationHotelRVAdapter();
    private NestedScrollView scrollView;
    private DiscountedConnection discountedConnection = new DiscountedConnection();
    private String message;
    private ArrayList<HotelListData> hotelList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_hotel_list);

        Intent intent = getIntent();
        scrollView = findViewById(R.id.scrollView);
        RecyclerView recyclerView = findViewById(R.id.rvRecommendHotelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(RecommendHotelListActivity.this));
        recyclerView.setAdapter(recommendationHotelRVAdapter);
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
        sendData(no);
        scrollView.post(new Runnable(){
            @Override
            public void run(){
                scrollView.fullScroll(scrollView.FOCUS_UP);
            }
        });
    }

    private class DiscountedConnection {

        private OkHttpClient client;

        private DiscountedConnection() {
            this.client = new OkHttpClient();
        }
        private void requestWebServer(Callback callback, int no) {
            Request request;
            switch (no){
                case 0:
                    request = new Request.Builder()
                        .url("http://www.kaca5.com/expedia/discounted_80000")
                        .build();
                    client.newCall(request).enqueue(callback);
                    break;

                case 1:
                    request = new Request.Builder()
                            .url("http://www.kaca5.com/expedia/discounted_80000")
                            .build();
                    client.newCall(request).enqueue(callback);
                    break;

                case 2:
                    request = new Request.Builder()
                            .url("http://www.kaca5.com/expedia/discounted_fin")
                            .build();
                    client.newCall(request).enqueue(callback);
                    break;
            }


        }
    }

    private void sendData(final int no) {
        new Thread() {
            public void run() {
                discountedConnection.requestWebServer(callback, no);
            }
        }.start();
    }

    private final Callback callback = new Callback() {
        @Override
        public void onFailure(@NonNull Call call, IOException e) {
            Log.d("TAG", "콜백오류:"+e.getMessage());

            message = getApplicationContext().getResources().getString(R.string.callback_error);
            RecommendHotelListActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(RecommendHotelListActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });

        }
        @Override
        public void onResponse(@NonNull Call call, Response response) throws IOException {
            String body = response.body().string();
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(body);
            if(element.getAsJsonObject().get("code").getAsInt() == 100) {
                hotelList.clear();
                JsonArray jsonArray = element.getAsJsonObject().get("result").getAsJsonArray();
                JsonObject object;
                String name, shortL, sDate, eDate, price;
                int hNo, percentage;
                int i = jsonArray.size();
                Log.e("TAG", "result.size():"+i);
                for (int j = 0; j < i; j++) {
                    object = jsonArray.get(j).getAsJsonObject();
                    hNo = object.get("Hno").getAsInt();
                    name = object.get("Name").getAsString();
                    shortL = object.get("ShortL").getAsString();
                    percentage = object.get("Percentage").getAsInt();
                    sDate = object.get("Sdate").getAsString();
                    SimpleDateFormat dateFormatOrigin = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
                    SimpleDateFormat dateFormatWant = new SimpleDateFormat("M월 d일 (E)", Locale.KOREA);
                    try{
                        Date date = dateFormatOrigin.parse(sDate);
                        sDate = dateFormatWant.format(date);
                    }catch(ParseException e){
                        e.printStackTrace();
                    }
                    eDate = object.get("Edate").getAsString();
                    try{
                        Date date = dateFormatOrigin.parse(eDate);
                        eDate = dateFormatWant.format(date);
                    }catch(ParseException e){
                        e.printStackTrace();
                    }
                    price = object.get("Priced").getAsString();
                    hotelList.add(new HotelListData(hNo, 1, price, name, shortL, sDate, eDate,R.drawable.hotel_list, percentage));
                    recommendationHotelRVAdapter.setItems(hotelList);
                }
                RecommendHotelListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recommendationHotelRVAdapter.notifyDataSetChanged();
                    }
                });
            } else{
                message = getApplicationContext().getResources().getString(R.string.callback_error);
                RecommendHotelListActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(RecommendHotelListActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
            Log.d("TAG", "서버에서 응답한 Body:"+element);

        }
    };
}
