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
import com.example.expedia.data.HotelData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HotelListRVAdapter extends RecyclerView.Adapter<HotelListRVAdapter.ViewHolder> {

    private ArrayList<HotelData> items = new ArrayList<>();

    @NonNull
    @Override
    public HotelListRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend_hotel,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelListRVAdapter.ViewHolder viewHolder, int position){
        final HotelData item = items.get(position);
        View mView = viewHolder.itemView;
        String name = item.getName();
        Glide.with(mView.getContext()).load(item.getImage()).override(430,300).centerCrop().into(viewHolder.imageView);
        viewHolder.tvHotelName.setText(name);
        String price = "￦"+item.getDiscounted_Price();
        viewHolder.tvHotelPrice.setText(price);
        String night = "1박 요금";
        viewHolder.tvHotelNight.setText(night);
        viewHolder.tvHotelLocation.setText(item.getLocation());
        String sale = "-"+item.getPercentage()+"%";
        viewHolder.tvHotelSale.setText(sale);
        String sDate = item.getSdate();
        SimpleDateFormat dateFormatOrigin = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        SimpleDateFormat dateFormatWant = new SimpleDateFormat("M월 d일 (E)", Locale.KOREA);
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
        String period = sDate + " ~ " + eDate;
        viewHolder.tvHotelPeriod.setText(period);

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HotelDetailActivity.class);

                intent.putExtra("sDate", item.getSdate());
                intent.putExtra("eDate", item.getEdate());
                intent.putExtra("hNo", item.getHno());
                intent.putExtra("originalPrice", item.getPrice());
                intent.putExtra("discountedPrice", item.getDiscounted_Price());
                intent.putExtra("name", item.getName());
                intent.putExtra("sale", item.getPercentage());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<HotelData> items) {
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
