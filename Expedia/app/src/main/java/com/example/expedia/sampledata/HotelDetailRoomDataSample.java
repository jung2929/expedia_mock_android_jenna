package com.example.expedia.sampledata;

import com.example.expedia.R;
import com.example.expedia.data.HotelDetailRoomData;
import com.example.expedia.data.RoomOptionData;

import java.util.ArrayList;

public class HotelDetailRoomDataSample {
    private ArrayList<HotelDetailRoomData> items = new ArrayList<>();
    private ArrayList<RoomOptionData> options = new ArrayList<>();

    public ArrayList<HotelDetailRoomData> getItems(){
        RoomOptionData option = new RoomOptionData(1, 50, 1, 6, "5월 1일 (수)까지", 440, "￦210,000", "￦120,000");
        RoomOptionData option2 = new RoomOptionData(1, 50, 2, 6, "5월 1일 (수)까지", 440, "￦210,000", "￦120,000");
        options.add(option);
        options.add(option2);
        HotelDetailRoomData room1 = new HotelDetailRoomData(R.drawable.room_image, 50, 1, "superior", "single", options);

        items.add(room1);
        items.add(room1);

        return items;
    }
}
