package com.example.expedia.datamanager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.expedia.R;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.expedia.MyApplication.SUCCESS_CODE;

public class ReservationCallback implements Callback {

    private Activity activity;
    private String message;

    public ReservationCallback(Activity activity){
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
        JsonObject responseObject = new JsonParser().parse(body).getAsJsonObject();

        if(responseObject.get("code").getAsInt() != SUCCESS_CODE){
            message = responseObject.get("message").getAsString();
            Log.d("TAG", "message:"+message);
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            message = responseObject.get("message").getAsString();
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                }
            });
            activity.finish();
        }
        Log.d("TAG", "서버에서 응답한 Body:"+responseObject.toString());

    }
}
