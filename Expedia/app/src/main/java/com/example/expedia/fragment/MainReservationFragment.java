package com.example.expedia.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.expedia.adapter.MainRecommendationAdapter;
import com.example.expedia.R;
import com.example.expedia.activity.HotelSearchActivity;
import com.example.expedia.activity.LogInActivity;
import com.example.expedia.sampledata.RecommendationDataSample;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainReservationFragment extends Fragment {

    ImageView hotel,airport,hotel_airport;
    private MainRecommendationAdapter adapter = new MainRecommendationAdapter();

    public MainReservationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_reservation, container, false);

        final NestedScrollView scrollView = view.findViewById(R.id.scrollView);
        RecyclerView recyclerView = view.findViewById(R.id.Main_category_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setItems(new RecommendationDataSample().getItems());

        hotel = view.findViewById(R.id.Main_Hotel);
        hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), HotelSearchActivity.class));
            }
        });
        airport = view.findViewById(R.id.Main_Air);
        hotel_airport = view.findViewById(R.id.Main_hotel_air);

        ImageView loginImage = view.findViewById(R.id.imageView8);
        loginImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LogInActivity.class));
            }
        });


        scrollView.post(new Runnable(){
           @Override
           public void run(){
               scrollView.fullScroll(scrollView.FOCUS_UP);
           }
        });
        return view;
    }
}
