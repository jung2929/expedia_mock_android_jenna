package com.example.expedia.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.expedia.R;
import com.example.expedia.activity.HotelDetailActivity;

import java.util.ArrayList;

public class HotelDetailUVPAdapter extends PagerAdapter {
    private ArrayList<String> imageList;
    private boolean isMultiScr;
    private Activity activity;

    public HotelDetailUVPAdapter(boolean isMultiScr, ArrayList<String> imageList, Activity activity) {
        this.imageList = imageList;
        this.isMultiScr = isMultiScr;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return imageList.size();

    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HotelDetailActivity detailActivity = (HotelDetailActivity)activity;
                detailActivity.scrollView.setVisibility(View.GONE);
                detailActivity.rvImages.setVisibility(View.VISIBLE);
                //Toast.makeText(container.getContext(), "이미지 더보기", Toast.LENGTH_SHORT).show();
            }
        };
        FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.item_hotel_detail_image, null);
        ImageView image = frameLayout.findViewById(R.id.hotel_detail_imageView);
        ImageView ivMoreImage = frameLayout.findViewById(R.id.moreImage_imageView);
        FrameLayout lastLayout = frameLayout.findViewById(R.id.lastImage_layout);
        LinearLayout llMoreImage = frameLayout.findViewById(R.id.moreImage_layout);
        ivMoreImage.setOnClickListener(listener);
        llMoreImage.setOnClickListener(listener);
        //new LinearLayout(container.getContext());
        //TextView textView = linearLayout.findViewById(R.id.pager_textview);
        //textView.setText(position + "");
        //linearLayout.setId(R.id.item_id);

        for(int i = 0; i < imageList.size()-1 ;i++){
            if(position == i){
                Glide.with(container.getContext()).load(imageList.get(i)).centerCrop().into(image);
                ivMoreImage.setVisibility(View.VISIBLE);
                lastLayout.setVisibility(View.GONE);
            }
        }

        if (position == imageList.size()-1){
            Glide.with(container.getContext()).load(imageList.get(position)).centerCrop().into(image);
            ivMoreImage.setVisibility(View.GONE);
            lastLayout.setVisibility(View.VISIBLE);
        }
        container.addView(frameLayout);
        return frameLayout;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        FrameLayout view = (FrameLayout) object;
        container.removeView(view);
    }

}

