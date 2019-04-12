package com.example.expedia.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expedia.R;
import com.example.expedia.adapter.HotelDetailRoomRVAdapter;
import com.example.expedia.adapter.HotelDetailServiceListRVAdapter;
import com.example.expedia.adapter.HotelDetailUVPAdapter;
import com.example.expedia.data.HotelDetailRoomData;
import com.example.expedia.data.RoomOptionData;
import com.example.expedia.sampledata.HotelDetailRoomDataSample;
import com.example.expedia.sampledata.ServiceListDataSample;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tmall.ultraviewpager.UltraViewPager;

import org.w3c.dom.Text;

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

public class HotelDetailActivity extends AppCompatActivity implements OnMapReadyCallback{
    private NestedScrollView scrollView;
    private HotelDetailServiceListRVAdapter hdslAdapter = new HotelDetailServiceListRVAdapter();
    private HotelDetailRoomRVAdapter hotelDetailRoomRVAdapter = new HotelDetailRoomRVAdapter();
    private HotelDetailConnection httpConn = new HotelDetailConnection();
    private int hNo;
    private String message;
    private RatingBar rbHotelRating;
    private TextView detailLocation, tvRatingTotal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);
        Intent intent = getIntent();
        hNo = intent.getIntExtra("hNo",0);
        String hotelName = intent.getStringExtra("name");
        String price = intent.getStringExtra("price");
        String sale = intent.getStringExtra("sale");
        String sDate = intent.getStringExtra("sDate");
        SimpleDateFormat dateFormatOrigin = new SimpleDateFormat("M월 d일 (E)", Locale.KOREA);
        SimpleDateFormat dateFormatWant = new SimpleDateFormat("M월 d일", Locale.KOREA);
        try{
            Date date = dateFormatOrigin.parse(sDate);
            sDate = dateFormatWant.format(date);
        }catch(ParseException e){
            e.printStackTrace();
        }
        String eDate = intent.getStringExtra("eDate");
        try{
            Date date = dateFormatOrigin.parse(eDate);
            eDate = dateFormatWant.format(date);
        }catch(ParseException e){
            e.printStackTrace();
        }
        //--------------------------- 상단 바 ---------------------------------//
        ImageView backImage = findViewById(R.id.cancel_btn);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView ivShare = findViewById(R.id.share_imageView);
        ivShare.setColorFilter(R.color.expedia_light_blue, PorterDuff.Mode.SRC_IN);
        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HotelDetailActivity.this, "공유하기", Toast.LENGTH_SHORT).show();
            }
        });
        TextView tvHotelName = findViewById(R.id.hotelName_textView);
        tvHotelName.setText(hotelName);
        rbHotelRating = findViewById(R.id.ratingBar);
        float rate = (float)4.3;

        //--------------------------- 스크롤 뷰 ---------------------------------//
        scrollView = findViewById(R.id.scrollView);

        //--------------------------울트라 뷰페이져-----------------------------//
        //ViewPager vpHotelImage = findViewById(R.id.hotelImage_viewPager);
        UltraViewPager vpHotelImage = findViewById(R.id.hotelImage_viewPager);
        vpHotelImage.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        //initialize UltraPagerAdapter，and add child view to UltraViewPager
        ArrayList<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.hotel_list);
        imageList.add(R.drawable.hotel_list);
        imageList.add(R.drawable.hotel_list);
        imageList.add(R.drawable.hotel_list);
        PagerAdapter uvpAdapter = new HotelDetailUVPAdapter(false, imageList);
        vpHotelImage.setAdapter(uvpAdapter);
        //initialize built-in indicator
        vpHotelImage.initIndicator();
        //set style of indicators
        vpHotelImage.getIndicator()
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(Color.WHITE)
                .setNormalColor(Color.GRAY)
                .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics()));
        //set the alignment
        vpHotelImage.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        vpHotelImage.getIndicator().setMargin(0,0,0,20);
        //construct built-in indicator, and add it to  UltraViewPager
        vpHotelImage.getIndicator().build();
        //------------------------------------뷰 페이져 끝 ------------------------------------//

        TextView tvSale = findViewById(R.id.sale_textView);
        tvSale.setText(sale);
        TextView tvOriginalPrice = findViewById(R.id.originalPrice_textView);
        String string = "￦272,727";
        tvOriginalPrice.setText(string);
        tvOriginalPrice.setPaintFlags(tvOriginalPrice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        LinearLayout llEditPeriod = findViewById(R.id.editPeriod_layout);
        llEditPeriod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HotelDetailActivity.this, "기간 수정 기능", Toast.LENGTH_SHORT).show();
            }
        });
        TextView tvPeriod = findViewById(R.id.period_textView);
        string = sDate + " ~ " + eDate;
        tvPeriod.setText(string);
        TextView tvSpecialPrice = findViewById(R.id.specialPrice_textView);
        tvSpecialPrice.setText(price);
        TextView tvPerson = findViewById(R.id.person_textView);
        string = "2명";
        tvPerson.setText(string);
        TextView tvPoint = findViewById(R.id.point_textView);
        string = "276포인트 적립";
        tvPoint.setText(string);
        tvRatingTotal = findViewById(R.id.ratingTotal_textView);
        //string = "4.2";
        //tvRatingTotal.setText(string);
        TextView tvGeneralLiview = findViewById(R.id.generalReview_textView);
        string = "- 매우 좋음!";
        tvGeneralLiview.setText(string);
        TextView tvReview = findViewById(R.id.review_textView);
        string = "473개 이용 후기 보기";
        tvReview.setText(string);
        tvReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HotelDetailActivity.this, "후기로 연결", Toast.LENGTH_SHORT).show();
            }
        });
        RatingBar rbClean = findViewById(R.id.clean_ratingBar);
        rate = (float)4.2;
        rbClean.setRating(rate);
        TextView tvCleanRating = findViewById(R.id.clean_rating_textView);
        string = Float.toString(rate);
        tvCleanRating.setText(string);
        RatingBar rbComfort = findViewById(R.id.comfort_ratingBar);
        rate = (float)4.3;
        rbComfort.setRating(rate);
        TextView tvComfortRate = findViewById(R.id.comfort_rating_textView);
        string = Float.toString(rate);
        tvComfortRate.setText(string);
        RatingBar rbService = findViewById(R.id.service_ratingBar);
        rate = (float)4.1;
        rbService.setRating(rate);
        TextView tvServiceRating = findViewById(R.id.service_rating_textView);
        string = Float.toString(rate);
        tvServiceRating.setText(string);
        RatingBar rvHotelStatus = findViewById(R.id.hotelStatus_ratingBar);
        rate = (float)4.2;
        rvHotelStatus.setRating(rate);
        TextView tvHotelStatusRating = findViewById(R.id.hotelStatus_rating_textView);
        string = Float.toString(rate);
        tvHotelStatusRating.setText(string);

        LinearLayout llPayLaterInfo = findViewById(R.id.payLaterInfo_layout);
        llPayLaterInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HotelDetailActivity.this, "안내사항 액티비티",Toast.LENGTH_SHORT).show();
            }
        });


        RecyclerView rvServicesList = findViewById(R.id.option_recyclerView);
        LinearLayoutManager mllm = new LinearLayoutManager(HotelDetailActivity.this);
        mllm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvServicesList.setLayoutManager(mllm);
        rvServicesList.setAdapter(hdslAdapter);
        hdslAdapter.setItems(new ServiceListDataSample().getItems());

        detailLocation = findViewById(R.id.locationDetail_textView);
        //---------------------------------구글 맵--------------------------//
        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager
                .findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);
        View mapFix = findViewById(R.id.mapFix_view);
        mapFix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        final LinearLayout llPayNowActive = findViewById(R.id.payNowActive_layout);
        final LinearLayout llPayLaterActive = findViewById(R.id.payLaterActive_layout);
        TextView llPayNow = findViewById(R.id.payNow_textView);
        TextView llPayLater = findViewById(R.id.payLater_textView);

        llPayNowActive.setVisibility(View.VISIBLE);
        llPayLaterActive.setVisibility(View.GONE);
        llPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llPayNowActive.setVisibility(View.VISIBLE);
                llPayLaterActive.setVisibility(View.GONE);
            }
        });
        llPayLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llPayNowActive.setVisibility(View.GONE);
                llPayLaterActive.setVisibility(View.VISIBLE);
            }
        });

        //------------------------------ 방 정보 -----------------------------//
        RecyclerView rvRoomList = findViewById(R.id.roomList_recyclerView);
        rvRoomList.setLayoutManager(new LinearLayoutManager(HotelDetailActivity.this));
        rvRoomList.setAdapter(hotelDetailRoomRVAdapter);
        //hotelDetailRoomRVAdapter.setItems(new HotelDetailRoomDataSample().getItems());

        sendData();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng SEOUL = new LatLng(37.56, 126.97);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
    }

    private class HotelDetailConnection {

        private OkHttpClient client;

        private HotelDetailConnection() {
            this.client = new OkHttpClient();
        }
        private void requestWebServer(Callback callback) {
            Request request = new Request.Builder()
                            .url("http://www.kaca5.com/expedia/discounted_more?Hno=" + hNo)
                            .build();
                    client.newCall(request).enqueue(callback);
        }
    }

    private void sendData() {
        new Thread() {
            public void run() {
                httpConn.requestWebServer(callback);
            }
        }.start();
    }

    private final Callback callback = new Callback() {
        @Override
        public void onFailure(@NonNull Call call, IOException e) {
            Log.d("TAG", "콜백오류:"+e.getMessage());

            message = getApplicationContext().getResources().getString(R.string.callback_error);
            HotelDetailActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(HotelDetailActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });

        }
        @Override
        public void onResponse(@NonNull Call call, Response response) throws IOException {
            String body = response.body().string();
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(body);
            if(element.getAsJsonObject().get("code").getAsInt() == 100) {
                JsonArray rooms = element.getAsJsonObject().get("result").getAsJsonArray();
                int size = rooms.size();
                JsonObject hotelInfo = rooms.get(size-1).getAsJsonObject();
                final String description = hotelInfo.get("Name").getAsString()+"은(는) "
                        +hotelInfo.get("LongL").getAsString()+" 인근에 위치해 있습니다.";
                final Float rate = (Float)hotelInfo.get("Ratings").getAsFloat();

                JsonObject room;

                final ArrayList<HotelDetailRoomData> roomData = new ArrayList<>();
                HotelDetailRoomData roomDatum;
                int rNo, percentage;
                String price, grade, bed;
                for (int i = 0; i < size-1;i++){
                    room = rooms.get(i).getAsJsonObject();
                    rNo = room.get("Rno").getAsInt();
                    percentage = room.get("Percentage").getAsInt();
                    price = room.get("Priced").getAsString();
                    int j = i+1;
                    grade = "방"+ j + " " +room.get("Grade").getAsString();
                    bed = room.get("Bed").getAsString();

                    ArrayList<RoomOptionData> options = new ArrayList<>();
                    RoomOptionData option = new RoomOptionData(rNo, percentage, 1, 6, "5월 1일 (수)까지", 440, "￦210,000", price);
                    RoomOptionData option2 = new RoomOptionData(rNo, percentage, 2, 9, "5월 1일 (수)까지", 680, "￦210,000", "￦120,000");
                    options.add(option);
                    options.add(option2);

                    roomDatum = new HotelDetailRoomData(R.drawable.room_image, percentage, rNo, grade, bed, options);
                    roomData.add(roomDatum);
                }
                hotelDetailRoomRVAdapter.setItems(roomData);
                HotelDetailActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rbHotelRating.setRating(rate);
                        detailLocation.setText(description);
                        hotelDetailRoomRVAdapter.notifyDataSetChanged();
                        String stringRate = rate+"";
                        tvRatingTotal.setText(stringRate);
                    }
                });
            } else{
                message = getApplicationContext().getResources().getString(R.string.callback_error);
                HotelDetailActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(HotelDetailActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
            Log.d("TAG", "서버에서 응답한 Body:"+element);

        }
    };
}
