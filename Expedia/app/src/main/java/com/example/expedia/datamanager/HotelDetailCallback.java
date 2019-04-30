package com.example.expedia.datamanager;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.expedia.R;
import com.example.expedia.activity.HotelDetailActivity;
import com.example.expedia.data.HotelDetailRoomData;
import com.example.expedia.data.RoomOptionData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HotelDetailCallback implements Callback {
    private HotelDetailActivity activity;
    private String message;
    private double lat, lng;

    public HotelDetailCallback(HotelDetailActivity activity){
        this.activity = activity;
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        Log.d("TAG", "콜백오류:"+e.getMessage());

        message = activity.getResources().getString(R.string.callback_error);
        activity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        assert response.body() != null;
        String body = response.body().string();
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(body);
        Log.d("TAG", "서버에서 응답한 Body:"+element);
        if(element.getAsJsonObject().get("code").getAsInt() == 100) {
            JsonArray rooms = element.getAsJsonObject().get("result").getAsJsonArray();
            int size = rooms.size();
            if (size == 1){
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "매진되었습니다", Toast.LENGTH_SHORT).show();
                    }
                });
                activity.finish();
                return;
            }
            JsonObject hotelInfo = rooms.get(size-1).getAsJsonObject();
            lat = hotelInfo.get("lat").getAsDouble();
            lng = hotelInfo.get("lng").getAsDouble();
            JsonObject room;

            final ArrayList<HotelDetailRoomData> roomData = new ArrayList<>();
            HotelDetailRoomData roomDatum;
            int rNo, percentage;
            String discountedPrice, grade, bed, originalPrice, image;

            for (int i = 0; i < size-1;i++){
                room = rooms.get(i).getAsJsonObject();
                rNo = room.get("Rno").getAsInt();
                percentage = room.get("Percentage").getAsInt();
                originalPrice = room.get("Price").getAsString();
                discountedPrice = room.get("discounted_Price").getAsString();
                image = room.get("image").getAsString();
                int j = i+1;
                grade = "방 " +room.get("Grade").getAsString();
                bed = room.get("Bed").getAsString();

                ArrayList<RoomOptionData> options = new ArrayList<>();
                RoomOptionData option = new RoomOptionData(rNo, percentage, 1, 1, "5월 1일 (수)까지", 440, originalPrice, discountedPrice);
                options.add(option);
                roomDatum = new HotelDetailRoomData(image, percentage, rNo, grade, bed, options);
                roomData.add(roomDatum);
            }
            activity.hotelDetailRoomRVAdapter.setItems(roomData);
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activity.mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            LatLng location = new LatLng(lat, lng);
                            Log.e("TAG","위도 경도"+ lat +", "+ lng);
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(location);
                            googleMap.addMarker(markerOptions);
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                            googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
                        }
                    });
                    activity.hotelDetailRoomRVAdapter.notifyDataSetChanged();

                }
            });
        } else{
            message = activity.getResources().getString(R.string.callback_error);
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
