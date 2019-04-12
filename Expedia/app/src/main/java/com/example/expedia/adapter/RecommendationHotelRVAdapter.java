package com.example.expedia.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.expedia.R;
import com.example.expedia.activity.HotelDetailActivity;
import com.example.expedia.data.HotelListData;

import java.util.ArrayList;

public class RecommendationHotelRVAdapter extends RecyclerView.Adapter<RecommendationHotelRVAdapter.ViewHolder> {

    private ArrayList<HotelListData> items = new ArrayList<>();

    @NonNull
    @Override
    public RecommendationHotelRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend_hotel,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendationHotelRVAdapter.ViewHolder viewHolder, int position){
        final HotelListData item = items.get(position);
        View mView = viewHolder.itemView;
        final String name = item.getName();
        Glide.with(mView.getContext()).load(item.getImage()).override(430,300).centerCrop().into(viewHolder.imageView);
        viewHolder.tvHotelName.setText(name);
        final String price = "￦"+item.getPrice();
        viewHolder.tvHotelPrice.setText(price);
        String night = item.getNight()+"박 요금";
        viewHolder.tvHotelNight.setText(night);
        viewHolder.tvHotelLocation.setText(item.getLocation());
        final String sale = "-"+item.getSale()+"%";
        viewHolder.tvHotelSale.setText(sale);
        String period = item.getStartDate() + " ~ " + item.getEndDate();
        viewHolder.tvHotelPeriod.setText(period);

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HotelDetailActivity.class);
                String sDate = item.getStartDate();
                String eDate = item.getEndDate();
                int hNo = item.gethNo();
                intent.putExtra("name", name);
                intent.putExtra("price", price);
                intent.putExtra("sale", sale);
                intent.putExtra("sDate", sDate);
                intent.putExtra("eDate", eDate);
                intent.putExtra("hNo", hNo);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<HotelListData> items) {
        this.items = items;
    }

    class ViewHolder extends  RecyclerView.ViewHolder{
        ImageView imageView;
        TextView tvHotelName, tvHotelLocation, tvHotelPeriod, tvHotelSale, tvHotelPrice, tvHotelNight;

        ViewHolder(View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.hotelImageView);
            tvHotelName = itemView.findViewById(R.id.hotelNameTextView);
            tvHotelLocation = itemView.findViewById(R.id.locationTextView);
            tvHotelPeriod = itemView.findViewById(R.id.periodTextView);
            tvHotelNight = itemView.findViewById(R.id.nightTextView);
            tvHotelPrice = itemView.findViewById(R.id.priceTextView);
            tvHotelSale = itemView.findViewById(R.id.saleTextView);
        }
    }
}
