package com.example.expedia;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MainCategoryAdapter extends RecyclerView.Adapter<MainCategoryAdapter.ViewHolder> {

    private ArrayList<MainReservation.Category> items = new ArrayList<>();

    @NonNull
    @Override
    public MainCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MainCategoryAdapter.ViewHolder viewHolder, int position){
        MainReservation.Category item = items.get(position);
        Glide.with(viewHolder.itemView.getContext()).load(item.getImage()).override(430,300).centerCrop().into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<MainReservation.Category> items) {
        this.items = items;
    }

    class ViewHolder extends  RecyclerView.ViewHolder{
        ImageView imageView;

        ViewHolder(View itemView){
            super(itemView);

            imageView = itemView.findViewById(R.id.Main_categoryImage);
        }
    }
}
