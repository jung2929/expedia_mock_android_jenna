package com.example.expedia;

import android.app.Application;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.support.constraint.Constraints.TAG;

public class MyApplication extends Application {
    private static boolean logInStatus;
    private checkLoginConnection httpConn = new checkLoginConnection();
    private String token;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(){
        super.onCreate();
        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        token = sharedPreferences.getString("jwt","");

        sendData();
    }

    public static boolean isLogInStatus() {
        return logInStatus;
    }

    public static void setLogInStatus(boolean logInStatus) {
        MyApplication.logInStatus = logInStatus;
    }

    private class checkLoginConnection {

        private OkHttpClient client;

        private checkLoginConnection() {
            this.client = new OkHttpClient();
        }
        private void requestWebServer(Callback callback) {
            Request request = new Request.Builder()
                    .addHeader("x-access-token", token)
                    .addHeader("Content-Type", "application/json")
                    .url("http://www.kaca5.com/expedia/token")
                    .build();
            client.newCall(request).enqueue(callback);
        }
    }
    /** 웹 서버로 데이터 전송 */
    private void sendData() {
// 네트워크 통신하는 작업은 무조건 작업스레드를 생성해서 호출 해줄 것!!
        new Thread() {
            public void run() {
                httpConn.requestWebServer(callback);
            }
        }.start();
    }

    private final Callback callback = new Callback() {
        @Override
        public void onFailure(@NonNull Call call, IOException e) {
            Log.d(TAG, "콜백오류:"+e.getMessage());

            String message = getApplicationContext().getResources().getString(R.string.callback_error);
            Log.d(TAG, "message:"+message);

        }
        @Override
        public void onResponse(@NonNull Call call, Response response) throws IOException {
            String body = response.body().string();
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(body);
            if(element.getAsJsonObject().get("code").getAsInt() == 100){
                logInStatus = true;
                Log.d("TAG3", "로그인");
            }else{
                logInStatus = false;
                Log.d("TAG2", "비로그인");
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
            }
            Log.d(TAG, "서버에서 응답한 Body:"+element);

        }
    };
}
