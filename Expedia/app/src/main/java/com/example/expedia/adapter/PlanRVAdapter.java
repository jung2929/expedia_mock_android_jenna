package com.example.expedia.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.expedia.R;
import com.example.expedia.activity.ReservationDetailActivity;
import com.example.expedia.data.ReservationRoomData;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PlanRVAdapter extends RecyclerView.Adapter<PlanRVAdapter.ViewHolder> {
    private ArrayList<ReservationRoomData> items = new ArrayList<>();


    @NonNull
    @Override
    public PlanRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan,parent,false);
        return new PlanRVAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final PlanRVAdapter.ViewHolder viewHolder, final int position){
        final ReservationRoomData item = items.get(position);
        viewHolder.tvHotelName.setText(item.getName());

        String sDate = item.getSdate();
        SimpleDateFormat dateFormatOrigin = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        SimpleDateFormat dateFormatWant = new SimpleDateFormat("M월 d일", Locale.KOREA);
        try{
            Date date = dateFormatOrigin.parse(sDate);
            sDate = dateFormatWant.format(date);
        }catch(ParseException e){
            e.printStackTrace();
        }

        String eDate = item.getEdate();
        try{
            Date date = dateFormatOrigin.parse(eDate);
            eDate = dateFormatWant.format(date);
        }catch(ParseException e){
            e.printStackTrace();
        }

        String date = sDate + " ~ " + eDate;

        viewHolder.tvDate.setText(date);
        viewHolder.tvHotelName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewHolder.itemView.getContext(), ReservationDetailActivity.class);
                Gson gson = new Gson();
                String json = gson.toJson(item);
                intent.putExtra("json", json);
                viewHolder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<ReservationRoomData> items) {
        this.items = items;
    }

    class ViewHolder extends  RecyclerView.ViewHolder{
        private TextView tvHotelName, tvDate;
        final View mView;

        ViewHolder(View itemView){
            super(itemView);
            mView = itemView;
            tvHotelName = itemView.findViewById(R.id.hotelName_textView);
            tvDate = itemView.findViewById(R.id.date_textView);
        }
    }
}
