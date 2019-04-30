package com.example.expedia.datamanager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.expedia.R;
import com.example.expedia.adapter.HotelListRVAdapter;
import com.example.expedia.data.HotelData;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HotelListCallback implements Callback {
    private Activity activity;
    private String message;
    private ArrayList<HotelData> hotelList = new ArrayList<>();
    private HotelListRVAdapter hotelListRVAdapter;

    public HotelListCallback(Activity activity, HotelListRVAdapter adapter){
        this.activity = activity;
        this.hotelListRVAdapter = adapter;
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        Log.d("TAG", "콜백오류:"+e.getMessage());

        message = activity.getResources().getString(R.string.callback_error);
        activity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                activity.finish();
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
            hotelList.clear();
            JsonArray jsonArray = element.getAsJsonObject().get("result").getAsJsonArray();
            String jsonString;
            int i = jsonArray.size();
            if(i == 0){
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity,activity.getResources().getString(R.string.nothing_to_search),Toast.LENGTH_LONG).show();
                    }
                });
            }
            Log.e("TAG", "result.size():"+i);
            for (int j = 0; j < i; j++) {
                Gson gson = new Gson();
                jsonString = jsonArray.get(j).toString();
                hotelList.add(gson.fromJson(jsonString, HotelData.class));
            }
            hotelListRVAdapter.setItems(hotelList);
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hotelListRVAdapter.notifyDataSetChanged();
                }
            });
        } else{
            message = activity.getResources().getString(R.string.callback_error);
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                    activity.finish();
                }
            });
        }
    }
}
