package com.example.expedia.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.expedia.R;
import com.example.expedia.activity.RecommendHotelListActivity;
import com.example.expedia.data.HotelDetailRoomData;
import com.example.expedia.data.RecommendationData;

import java.util.ArrayList;

public class HotelDetailRoomRVAdapter extends RecyclerView.Adapter<HotelDetailRoomRVAdapter.ViewHolder> {

    private ArrayList<HotelDetailRoomData> items = new ArrayList<>();
    private RoomOptionRVAdapter roomOptionRVAdapter = new RoomOptionRVAdapter();

    @NonNull
    @Override
    public HotelDetailRoomRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotel_detail_room,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelDetailRoomRVAdapter.ViewHolder viewHolder, final int position){
        final HotelDetailRoomData item = items.get(position);
        final View mView = viewHolder.itemView;

        viewHolder.rvOption.setLayoutManager(new LinearLayoutManager(mView.getContext()));
        viewHolder.rvOption.setAdapter(roomOptionRVAdapter);
        roomOptionRVAdapter.setItems(item.getOptions());
        Glide.with(mView.getContext()).load(item.getRoomImage()).centerCrop().into(viewHolder.ivRoom);
        String grade = item.getGrade();
        viewHolder.tvGrade.setText(grade);
        String bed = item.getBed();
        viewHolder.tvBed.setText(bed);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<HotelDetailRoomData> items) {
        this.items = items;
    }

    class ViewHolder extends  RecyclerView.ViewHolder{
        final View mView;
        private ImageView ivRoom;
        private TextView tvGrade, tvBed;
        private RecyclerView rvOption;

        ViewHolder(View itemView){
            super(itemView);
            mView = itemView;
            ivRoom = itemView.findViewById(R.id.room_imageView);
            tvGrade = itemView.findViewById(R.id.grade_textView);
            tvBed = itemView.findViewById(R.id.bed_textView);
            rvOption = itemView.findViewById(R.id.option_recyclerView);
        }
    }
}
