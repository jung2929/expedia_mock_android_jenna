package com.example.expedia.adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
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

import java.util.ArrayList;

public class HotelDetailUVPAdapter extends PagerAdapter {
    private ArrayList<Integer> imageList;
    private boolean isMultiScr;

    public HotelDetailUVPAdapter(boolean isMultiScr, ArrayList<Integer> imageList) {
        this.imageList = imageList;
        this.isMultiScr = isMultiScr;
    }

    @Override
    public int getCount() {
        return imageList.size();

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(container.getContext(), "이미지 더보기", Toast.LENGTH_SHORT).show();
            }
        };
        FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.item_hotel_detail_image, null);
        ImageView image = frameLayout.findViewById(R.id.hotel_detail_imageView);
        ImageView moreImage = frameLayout.findViewById(R.id.moreImage_imageView);
        LinearLayout lastLayout = frameLayout.findViewById(R.id.lastImage_layout);
        image.setOnClickListener(listener);
        lastLayout.setOnClickListener(listener);
        //new LinearLayout(container.getContext());
        //TextView textView = linearLayout.findViewById(R.id.pager_textview);
        //textView.setText(position + "");
        //linearLayout.setId(R.id.item_id);

        for(int i = 0; i < imageList.size()-1 ;i++){
            if(position == i){
                Glide.with(container.getContext()).load(imageList.get(i)).centerCrop().into(image);
                moreImage.setVisibility(View.VISIBLE);
                lastLayout.setVisibility(View.GONE);
            }
        }

        if (position == imageList.size()-1){
            Glide.with(container.getContext()).load(imageList.get(position)).centerCrop().into(image);
            moreImage.setVisibility(View.GONE);
            lastLayout.setVisibility(View.VISIBLE);
        }
        container.addView(frameLayout);
        return frameLayout;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        FrameLayout view = (FrameLayout) object;
        container.removeView(view);
    }

}

