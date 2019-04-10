package com.example.expedia.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.expedia.R;

import java.util.ArrayList;

public class HotelDetailServiceListRVAdapter extends RecyclerView.Adapter<HotelDetailServiceListRVAdapter.ViewHolder> {

    //private ArrayList<RecommendationData> items = new ArrayList<>();
    private ArrayList<Integer> items = new ArrayList<>();

    @NonNull
    @Override
    public HotelDetailServiceListRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_services,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelDetailServiceListRVAdapter.ViewHolder viewHolder, final int position){
        final int item = items.get(position);
        final View mView = viewHolder.itemView;
        Glide.with(mView.getContext()).load(item).fitCenter().into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<Integer> items) {
        this.items = items;
    }

    class ViewHolder extends  RecyclerView.ViewHolder{
        ImageView imageView;
        final View mView;

        ViewHolder(View itemView){
            super(itemView);
            mView = itemView;
            imageView = itemView.findViewById(R.id.serviceList_imageView);
        }
    }
}
