package com.example.expedia;

import android.app.Application;
import android.content.SharedPreferences;

import com.example.expedia.datamanager.CheckTokenCallback;
import com.example.expedia.datamanager.HttpConnection;

public class MyApplication extends Application {
    public static final int SUCCESS_CODE = 100;
    private static boolean logInStatus;
    private static String token;
    private HttpConnection httpConnection;
    private CheckTokenCallback callback;

    @Override
    public void onCreate(){
        super.onCreate();
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        token = sharedPreferences.getString("jwt","");
        if (token == null){
            logInStatus = false;
        } else {
            logInStatus = true;
            sendData();
        }
    }

    public static boolean isLogInStatus() {
        return logInStatus;
    }

    public static void setLogInStatus(boolean logInStatus) {
        MyApplication.logInStatus = logInStatus;
    }

    public static String getToken(){
        return token;
    }

    public static void setToken(String token){
        MyApplication.token = token;
    }

    private void sendData() {
        httpConnection = new HttpConnection(getApplicationContext(), "token", "get");
        callback = new CheckTokenCallback(getApplicationContext());
        new Thread() {
            public void run() {
                httpConnection.requestWebServer(callback);
            }
        }.start();
    }
}
