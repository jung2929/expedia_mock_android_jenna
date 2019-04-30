package com.example.expedia.datamanager;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Toast;

import com.example.expedia.R;
import com.example.expedia.activity.HotelDetailActivity;
import com.example.expedia.adapter.HotelDetailUVPAdapter;
import com.example.expedia.adapter.MoreImageRVAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tmall.ultraviewpager.UltraViewPager;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HotelImageCallback implements Callback {
    private HotelDetailActivity activity;
    private String message;

    public HotelImageCallback(HotelDetailActivity activity){
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

            final HotelDetailUVPAdapter uvpAdapter = new HotelDetailUVPAdapter(false, images, activity);
            final MoreImageRVAdapter moreImageRVAdapter = new MoreImageRVAdapter();
            moreImageRVAdapter.setItems(images);

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activity.vpHotelImage.setAdapter(uvpAdapter);
                    //initialize built-in indicator
                    activity.vpHotelImage.initIndicator();
                    //set style of indicators
                    activity.vpHotelImage.getIndicator()
                            .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                            .setFocusColor(Color.WHITE)
                            .setNormalColor(Color.GRAY)
                            .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, activity.getResources().getDisplayMetrics()));
                    //set the alignment
                    activity.vpHotelImage.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
                    activity.vpHotelImage.getIndicator().setMargin(0,0,0,20);
                    //construct built-in indicator, and add it to  UltraViewPager
                    activity.vpHotelImage.getIndicator().build();
                    activity.rvImages.setAdapter(moreImageRVAdapter);
                }
            });
        }
    }
}
