package com.example.expedia.datamanager;


import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.example.expedia.R;
import com.example.expedia.activity.HotelDetailActivity;
import com.example.expedia.adapter.MoreImageRVAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RoomImageCallback implements Callback {
    private HotelDetailActivity activity;
    private String message;

    public RoomImageCallback(HotelDetailActivity activity){
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
            JsonArray hotelImages = element.getAsJsonObject().get("result").getAsJsonArray();
            int resultSize = hotelImages.size();
            ArrayList<String> images = new ArrayList<>();
            for(int i = 0; i<resultSize; i++){
                images.add(hotelImages.get(i).getAsString());
            }

            final MoreImageRVAdapter moreImageRVAdapter = new MoreImageRVAdapter();
            moreImageRVAdapter.setItems(images);

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activity.rvRoomImages.setLayoutManager(new GridLayoutManager(activity, 3));
                    activity.rvRoomImages.setAdapter(moreImageRVAdapter);
                }
            });
        }
    }
}
