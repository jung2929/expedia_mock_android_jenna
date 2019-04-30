package com.example.expedia.adapter;

import android.app.Activity;
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
import com.example.expedia.activity.HotelDetailActivity;
import com.example.expedia.data.HotelDetailRoomData;
import com.example.expedia.datamanager.HotelImageCallback;
import com.example.expedia.datamanager.HttpConnection;
import com.example.expedia.datamanager.RoomImageCallback;

import java.util.ArrayList;

public class HotelDetailRoomRVAdapter extends RecyclerView.Adapter<HotelDetailRoomRVAdapter.ViewHolder> {

    private ArrayList<HotelDetailRoomData> items = new ArrayList<>();
    public HotelDetailActivity activity;
    private HttpConnection httpConnection;
    private RoomImageCallback callback;

    public HotelDetailRoomRVAdapter(HotelDetailActivity activity){
        this.activity = activity;
    }

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
        RoomOptionRVAdapter roomOptionRVAdapter = new RoomOptionRVAdapter(activity, item);

        viewHolder.rvOption.setLayoutManager(new LinearLayoutManager(mView.getContext()));
        viewHolder.rvOption.setAdapter(roomOptionRVAdapter);
        roomOptionRVAdapter.setItems(item.getOptions());
        Glide.with(mView.getContext()).load(item.getRoomImage()).centerCrop().into(viewHolder.ivRoom);
        String grade = item.getGrade();
        viewHolder.tvGrade.setText(grade);
        String bed = item.getBed();
        viewHolder.tvBed.setText(bed);
        viewHolder.ivMoreImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                httpConnection = new HttpConnection(activity, "room_image?Hno="+activity.hNo+"&Rno="+item.getRoomNum(),"get");
                callback = new RoomImageCallback(activity);
                new Thread(){
                    public void run(){
                        httpConnection.requestWebServer(callback);
                    }
                }.start();
                activity.scrollView.setVisibility(View.GONE);
                activity.rvRoomImages.setVisibility(View.VISIBLE);

            }
        });
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
        private ImageView ivRoom, ivMoreImage;
        private TextView tvGrade, tvBed;
        private RecyclerView rvOption;

        ViewHolder(View itemView){
            super(itemView);
            mView = itemView;
            ivRoom = itemView.findViewById(R.id.room_imageView);
            tvGrade = itemView.findViewById(R.id.grade_textView);
            tvBed = itemView.findViewById(R.id.bed_textView);
            rvOption = itemView.findViewById(R.id.option_recyclerView);
            ivMoreImage = itemView.findViewById(R.id.moreImage_imageView);
        }
    }
}
