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

public class MoreImageRVAdapter extends RecyclerView.Adapter<MoreImageRVAdapter.ViewHolder> {

    //private ArrayList<RecommendationData> items = new ArrayList<>();
    private ArrayList<String> items = new ArrayList<>();

    @NonNull
    @Override
    public MoreImageRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_images,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MoreImageRVAdapter.ViewHolder viewHolder, final int position){
        final String item = items.get(position);
        final View mView = viewHolder.itemView;
        Glide.with(mView.getContext()).load(item).fitCenter().into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }

    class ViewHolder extends  RecyclerView.ViewHolder{
        ImageView imageView;
        final View mView;

        ViewHolder(View itemView){
            super(itemView);
            mView = itemView;
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
