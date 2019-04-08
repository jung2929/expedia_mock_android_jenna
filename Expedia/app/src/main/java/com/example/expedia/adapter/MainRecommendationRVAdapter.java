package com.example.expedia.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.expedia.R;
import com.example.expedia.activity.RecommendHotelListActivity;
import com.example.expedia.data.RecommendationData;

import java.util.ArrayList;

public class MainRecommendationRVAdapter extends RecyclerView.Adapter<MainRecommendationRVAdapter.ViewHolder> {

    private ArrayList<RecommendationData> items = new ArrayList<>();

    @NonNull
    @Override
    public MainRecommendationRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecommendationRVAdapter.ViewHolder viewHolder, final int position){
        final RecommendationData item = items.get(position);
        final View mView = viewHolder.itemView;
        Glide.with(mView.getContext()).load(item.getImage()).override(430,300).centerCrop().into(viewHolder.imageView);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG","클릭 작동");
                Intent intent = new Intent(v.getContext(), RecommendHotelListActivity.class);
                int no = item.getNo();
                intent.putExtra("no", no);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<RecommendationData> items) {
        this.items = items;
    }

    class ViewHolder extends  RecyclerView.ViewHolder{
        ImageView imageView;
        final View mView;

        ViewHolder(View itemView){
            super(itemView);
            mView = itemView;
            imageView = itemView.findViewById(R.id.Main_categoryImage);
        }
    }
}
