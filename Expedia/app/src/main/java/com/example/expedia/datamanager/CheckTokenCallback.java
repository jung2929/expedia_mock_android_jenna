package com.example.expedia.datamanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.expedia.MyApplication;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.expedia.MyApplication.SUCCESS_CODE;

public class CheckTokenCallback implements Callback {
    private SharedPreferences sharedPreferences;

    public CheckTokenCallback(Context context){
        sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        Log.d("TAG", "콜백오류:"+e.getMessage());
        MyApplication.setLogInStatus(false);
    }
    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        assert response.body() != null;
        String body = response.body().string();
        JsonObject responseObject = new JsonParser().parse(body).getAsJsonObject();

        if(responseObject.get("code").getAsInt() == SUCCESS_CODE){
            MyApplication.setLogInStatus(true);
            Log.d("TAG3", "로그인");
        }else{
            MyApplication.setLogInStatus(false);
            Log.d("TAG2", "비로그인");
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
        }
        Log.d("TAG", "서버에서 응답한 Body:"+ responseObject.toString());

    }
}
