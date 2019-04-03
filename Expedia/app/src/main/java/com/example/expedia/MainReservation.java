package com.example.expedia;


import android.content.Intent;
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

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainReservation extends Fragment {

    ImageView hotel,airport,hotel_airport;
    private MainCategoryAdapter adapter = new MainCategoryAdapter();

    public MainReservation() {
        // Required empty public constructor
    }

    public class Category{
        private int image;
        private int no;// 0 = 80000원 이하 1 = 일일 2 = 마감

        public Category(int image, int no){
            this.image = image;
            this.no = no;
        }

        public int getImage() {
            return image;
        }

        public void setImage(int image) {
            this.image = image;
        }

        public int getNo() {
            return no;
        }

        public void setNo(int no) {
            this.no = no;
        }
    }

    public class CategoryContents{
        ArrayList<Category> items = new ArrayList<>();

        public ArrayList<Category> getItems(){
            Category under80000 = new Category(R.drawable.under80000,0);
            Category daybyday = new Category(R.drawable.daybyday,1);

            items.add(under80000);
            items.add(daybyday);

            return items;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_reservation, container, false);

        final NestedScrollView scrollView = view.findViewById(R.id.scrollView);
        RecyclerView recyclerView = view.findViewById(R.id.Main_category_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setItems(new CategoryContents().getItems());

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
