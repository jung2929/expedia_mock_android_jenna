package com.example.expedia.sampledata;


import com.example.expedia.R;
import com.example.expedia.data.RecommendationData;

import java.util.ArrayList;

public class RecommendationDataSample{
    private ArrayList<RecommendationData> items = new ArrayList<>();

    public ArrayList<RecommendationData> getItems(){
        RecommendationData under80000 = new RecommendationData(R.drawable.under80000,0);
        RecommendationData daybyday = new RecommendationData(R.drawable.daybyday,1);

        items.add(under80000);
        items.add(daybyday);

        return items;
    }
}