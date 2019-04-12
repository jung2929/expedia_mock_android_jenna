package com.example.expedia.sampledata;

import com.example.expedia.R;
import com.example.expedia.data.HotelListData;

import java.util.ArrayList;

public class HotelListDataSample {
    private ArrayList<HotelListData> items = new ArrayList<>();

    public ArrayList<HotelListData> getItems (){
        HotelListData hotel1 = new HotelListData(1,1, "80,000", "랜딩관 제주신화월드 호텔앤리조트", "서귀포, 한국", "7월 10일 (수)", "7월 11일 (목)", R.drawable.hotel_list, 75);
        items.add(hotel1);
        items.add(hotel1);
        items.add(hotel1);
        items.add(hotel1);
        items.add(hotel1);
        items.add(hotel1);
        items.add(hotel1);
        items.add(hotel1);
        items.add(hotel1);
        items.add(hotel1);
        items.add(hotel1);

        return items;
    }
}
