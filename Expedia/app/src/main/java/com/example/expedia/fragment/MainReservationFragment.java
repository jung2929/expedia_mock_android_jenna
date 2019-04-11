package com.example.expedia.fragment;


import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.expedia.MyApplication;
import com.example.expedia.activity.RecommendHotelListActivity;
import com.example.expedia.adapter.MainRecommendationRVAdapter;
import com.example.expedia.R;
import com.example.expedia.activity.HotelSearchActivity;
import com.example.expedia.activity.LogInActivity;
import com.example.expedia.sampledata.RecommendationDataSample;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainReservationFragment extends Fragment {

    private ImageView ivLoginImage;
    private TextView tvAfterLogin;
    private MainRecommendationRVAdapter adapter = new MainRecommendationRVAdapter();
    private NestedScrollView scrollView;

    public MainReservationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_reservation, container, false);

        scrollView = view.findViewById(R.id.scrollView);
        RecyclerView recyclerView = view.findViewById(R.id.Main_category_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setItems(new RecommendationDataSample().getItems());


        ImageView ivHotel = view.findViewById(R.id.Main_Hotel);
        ivHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), HotelSearchActivity.class));
            }
        });
        ImageView ivAirport = view.findViewById(R.id.Main_Air);
        ImageView ivHotel_airport = view.findViewById(R.id.Main_hotel_air);

        ivLoginImage = view.findViewById(R.id.login_imageView);
        ivLoginImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LogInActivity.class));
            }
        });
        tvAfterLogin = view.findViewById(R.id.afterLogin_textView);

        ImageView ivDeadlineImage = view.findViewById(R.id.imageView2);
        ivDeadlineImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RecommendHotelListActivity.class);
                intent.putExtra("no", 2);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        checkLoginStatus();
    }

    public void checkLoginStatus(){
        if(MyApplication.isLogInStatus()){
            ivLoginImage.setVisibility(View.GONE);
            tvAfterLogin.setVisibility(View.VISIBLE);
        }else{
            ivLoginImage.setVisibility(View.VISIBLE);
            tvAfterLogin.setVisibility(View.GONE);
        }
        scrollView.post(new Runnable(){
            @Override
            public void run(){
                scrollView.fullScroll(NestedScrollView.FOCUS_UP);
            }
        });
    }

}
