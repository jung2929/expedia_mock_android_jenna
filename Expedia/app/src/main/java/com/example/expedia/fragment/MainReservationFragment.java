package com.example.expedia.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.expedia.MyApplication;
import com.example.expedia.activity.HotelListActivity;
import com.example.expedia.adapter.MainRecommendationRVAdapter;
import com.example.expedia.R;
import com.example.expedia.activity.HotelSearchActivity;
import com.example.expedia.activity.LogInSignUpActivity;
import com.example.expedia.sampledata.RecommendationDataSample;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainReservationFragment extends Fragment {

    private ImageView ivLoginImage;
    private TextView tvAfterLogin;
    private MainRecommendationRVAdapter adapter = new MainRecommendationRVAdapter();
    private boolean prev_loginStatus;

    public MainReservationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_reservation, container, false);

        prev_loginStatus = MyApplication.isLogInStatus();
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
        //ImageView ivAirport = view.findViewById(R.id.Main_Air);
        //ImageView ivHotel_airport = view.findViewById(R.id.Main_hotel_air);

        ivLoginImage = view.findViewById(R.id.login_imageView);
        ivLoginImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LogInSignUpActivity.class));
            }
        });
        tvAfterLogin = view.findViewById(R.id.afterLogin_textView);

        ImageView ivDeadlineImage = view.findViewById(R.id.imageView2);
        ivDeadlineImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HotelListActivity.class);
                intent.putExtra("no", 2);
                startActivity(intent);
            }
        });
        checkLoginStatus();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(prev_loginStatus != MyApplication.isLogInStatus()) {
            checkLoginStatus();
        }
    }

    public void checkLoginStatus(){
        if(MyApplication.isLogInStatus()){
            ivLoginImage.setVisibility(View.GONE);
            tvAfterLogin.setVisibility(View.VISIBLE);
        }else{
            ivLoginImage.setVisibility(View.VISIBLE);
            tvAfterLogin.setVisibility(View.GONE);
        }
        prev_loginStatus = MyApplication.isLogInStatus();
    }

}
