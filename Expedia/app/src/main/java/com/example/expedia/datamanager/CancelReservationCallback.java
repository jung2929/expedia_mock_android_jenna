package com.example.expedia.datamanager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.expedia.R;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CancelReservationCallback implements Callback {
    private Activity activity;
    private String message;

    public CancelReservationCallback(Activity activity){
        this.activity = activity;
    }
    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        Log.d("TAG", "콜백오류:"+e.getMessage());

        message = activity.getResources().getString(R.string.callback_error);
        Log.d("TAG", "message:"+message);
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
        Log.d("TAG","body = "+body);
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(body);
        if(element.getAsJsonObject().get("code").getAsInt() != 100){
            message = activity.getResources().getString(R.string.callback_error);
            Log.d("TAG", "message:"+message);
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(activity, activity.getResources().getString(R.string.cancel_reservation_success), Toast.LENGTH_SHORT).show();
                }
            });
        }
        Log.d("TAG", "서버에서 응답한 Body:"+element);


    }
}
