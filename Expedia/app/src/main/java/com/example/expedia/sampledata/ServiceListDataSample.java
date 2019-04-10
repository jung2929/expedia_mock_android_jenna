package com.example.expedia.sampledata;

import com.example.expedia.R;

import java.util.ArrayList;

public class ServiceListDataSample {
    private ArrayList<Integer> items = new ArrayList<>();

    public ArrayList<Integer> getItems(){
        items.add(R.drawable.swimming_pool);
        items.add(R.drawable.parking);
        items.add(R.drawable.free_wifi);
        items.add(R.drawable.breakfirst);
        items.add(R.drawable.businesscenter);
        items.add(R.drawable.kids_care);
        items.add(R.drawable.kids_activity);
        items.add(R.drawable.gym);
        items.add(R.drawable.restaurant);
        items.add(R.drawable.shuttle);
        return items;
    }
}
