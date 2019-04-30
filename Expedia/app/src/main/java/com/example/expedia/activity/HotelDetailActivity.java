package com.example.expedia.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expedia.R;
import com.example.expedia.adapter.HotelDetailRoomRVAdapter;
import com.example.expedia.adapter.HotelDetailServiceListRVAdapter;
import com.example.expedia.datamanager.HotelDetailCallback;
import com.example.expedia.datamanager.HotelImageCallback;
import com.example.expedia.datamanager.HttpConnection;
import com.example.expedia.sampledata.ServiceListDataSample;
import com.google.android.gms.maps.MapFragment;
import com.tmall.ultraviewpager.UltraViewPager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HotelDetailActivity extends AppCompatActivity{
    private HotelDetailServiceListRVAdapter hdslAdapter = new HotelDetailServiceListRVAdapter();
    public HotelDetailRoomRVAdapter hotelDetailRoomRVAdapter;
    public int hNo;
    public RatingBar rbHotelRating;
    public TextView detailLocation, tvRatingTotal;
    public MapFragment mapFragment;
    private HttpConnection httpConnection, imageHttpConnection;
    private HotelDetailCallback hotelDetailCallback;
    public String sDate, eDate, hotelName;
    private HotelImageCallback hotelImageCallback;
    public UltraViewPager vpHotelImage;
    public NestedScrollView scrollView;
    public RecyclerView rvImages, rvRoomImages;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);
        Intent intent = getIntent();
        hNo = intent.getIntExtra("hNo",0);
        hotelName = intent.getStringExtra("name");
        String originalPrice = intent.getStringExtra("originalPrice");
        String price = intent.getStringExtra("discountedPrice");
        String sale = intent.getStringExtra("sale");
        sDate = intent.getStringExtra("sDate");
        String tsDate="";
        SimpleDateFormat dateFormatOrigin = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        SimpleDateFormat dateFormatWant = new SimpleDateFormat("M월 d일", Locale.KOREA);
        try{
            Date date = dateFormatOrigin.parse(sDate);
            tsDate = dateFormatWant.format(date);
        }catch(ParseException e){
            e.printStackTrace();
        }

        eDate = intent.getStringExtra("eDate");
        String teDate="";
        try{
            Date date = dateFormatOrigin.parse(eDate);
            teDate = dateFormatWant.format(date);
        }catch(ParseException e){
            e.printStackTrace();
        }
        hotelDetailRoomRVAdapter = new HotelDetailRoomRVAdapter(HotelDetailActivity.this);
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
        rbHotelRating.setRating(rate);
        //--------------------------- 스크롤 뷰 ---------------------------------//

        //--------------------------울트라 뷰페이져-----------------------------//
        //ViewPager vpHotelImage = findViewById(R.id.hotelImage_viewPager);
        vpHotelImage = findViewById(R.id.hotelImage_viewPager);
        vpHotelImage.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        //initialize UltraPagerAdapter，and add child view to UltraViewPager
        /*ArrayList<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.hotel_list);
        imageList.add(R.drawable.hotel_list);
        imageList.add(R.drawable.hotel_list);
        imageList.add(R.drawable.hotel_list);*/
        /*uvpAdapter = new HotelDetailUVPAdapter(false, images);
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
        vpHotelImage.getIndicator().build();*/
        //------------------------------------뷰 페이져 끝 ------------------------------------//

        TextView tvSale = findViewById(R.id.sale_textView);
        sale = "-"+sale+"%";
        tvSale.setText(sale);
        TextView tvOriginalPrice = findViewById(R.id.originalPrice_textView);
        originalPrice = "￦" + originalPrice;
        tvOriginalPrice.setText(originalPrice);
        tvOriginalPrice.setPaintFlags(tvOriginalPrice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        LinearLayout llEditPeriod = findViewById(R.id.editPeriod_layout);
        llEditPeriod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HotelDetailActivity.this, "기간 수정 기능", Toast.LENGTH_SHORT).show();
            }
        });
        TextView tvPeriod = findViewById(R.id.period_textView);
        String string = tsDate + " ~ " + teDate;
        tvPeriod.setText(string);
        TextView tvSpecialPrice = findViewById(R.id.specialPrice_textView);
        price = "￦" + price;
        tvSpecialPrice.setText(price);
        TextView tvPerson = findViewById(R.id.person_textView);
        string = "2명";
        //tvPerson.setText(string);
        TextView tvPoint = findViewById(R.id.point_textView);
        string = "276포인트 적립";
        tvPoint.setText(string);
        tvRatingTotal = findViewById(R.id.ratingTotal_textView);
        string = "4.2";
        tvRatingTotal.setText(string);
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
        mapFragment = (MapFragment)fragmentManager
                .findFragmentById(R.id.map_fragment);
        //mapFragment.getMapAsync(this);
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

        scrollView = findViewById(R.id.scrollView);
        rvImages = findViewById(R.id.images_recyclerView);
        scrollView.setVisibility(View.VISIBLE);
        rvImages.setVisibility(View.GONE);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(HotelDetailActivity.this, 3);
        rvImages.setLayoutManager(gridLayoutManager);
        rvRoomImages = findViewById(R.id.roomImages_recyclerView);
        rvRoomImages.setVisibility(View.GONE);


        getHotelImages();
        sendData();
    }

    private void sendData(){
        httpConnection = new HttpConnection(HotelDetailActivity.this, "discounted_more?Hno="+hNo,"get");
        hotelDetailCallback = new HotelDetailCallback(HotelDetailActivity.this);
        new Thread() {
            public void run() {
                httpConnection.requestWebServer(hotelDetailCallback);
            }
        }.start();
    }

    private void getHotelImages(){
        imageHttpConnection = new HttpConnection(HotelDetailActivity.this, "hotel_image?Hno="+hNo,"get");
        hotelImageCallback = new HotelImageCallback(HotelDetailActivity.this);
        new Thread(){
            public void run(){
                imageHttpConnection.requestWebServer(hotelImageCallback);
            }
        }.start();
    }

    @Override
    public void onBackPressed() {
        if(scrollView.getVisibility() == View.GONE){
            scrollView.setVisibility(View.VISIBLE);
            rvImages.setVisibility(View.GONE);
            rvRoomImages.setVisibility(View.GONE);
            return;
        }
        super.onBackPressed();
    }
}
