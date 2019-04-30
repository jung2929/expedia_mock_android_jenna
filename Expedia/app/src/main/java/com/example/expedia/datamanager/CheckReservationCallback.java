package com.example.expedia.datamanager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.expedia.R;
import com.example.expedia.data.ReservationRoomData;
import com.example.expedia.fragment.MainPlanFragment;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.expedia.MyApplication.SUCCESS_CODE;

public class CheckReservationCallback implements Callback {
    private ArrayList<ReservationRoomData> rooms = new ArrayList<>();
    private Activity activity;
    private MainPlanFragment fragment;
    private String message;

    public CheckReservationCallback(MainPlanFragment fragment){
        this.fragment = fragment;
        activity = fragment.getActivity();
    }


    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        Log.d("TAG", "콜백오류:"+e.getMessage());
    }
    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        assert response.body() != null;
        String body = response.body().string();
        JsonObject responseObject = new JsonParser().parse(body).getAsJsonObject();

        if(responseObject.get("code").getAsInt() == SUCCESS_CODE){
            JsonArray jsonArray = responseObject.getAsJsonObject().get("result").getAsJsonArray();
            String jsonString;
            int i = jsonArray.size();
            Log.e("TAG", "result.size():"+i);
            if (i == 0){
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        fragment.tvPlanStatus.setVisibility(View.VISIBLE);
                        fragment.rvPlan.setVisibility(View.GONE);
                    }
                });
                return;
            }

            activity.runOnUiThread(new Runnable() {
                public void run() {
                    fragment.tvPlanStatus.setVisibility(View.GONE);
                    fragment.rvPlan.setVisibility(View.VISIBLE);
                }
            });

            for (int j = 0; j < i; j++) {
                Gson gson = new Gson();
                jsonString = jsonArray.get(j).toString();
                rooms.add(gson.fromJson(jsonString, ReservationRoomData.class));

                fragment.adapter.setItems(rooms);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fragment.adapter.notifyDataSetChanged();
                    }
                });
            }
        }else{
            message = activity.getResources().getString(R.string.callback_error);
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
        Log.d("TAG", "서버에서 응답한 Body:"+ responseObject.toString());

    }
}
