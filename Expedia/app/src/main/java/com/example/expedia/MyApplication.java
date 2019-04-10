package com.example.expedia;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

public class MyApplication extends Application {
    private static boolean logInStatus;

    @Override
    public void onCreate(){
        super.onCreate();
        SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        String token = sharedPreferences.getString("jwt","");
        Log.d("TAG1", "jwt=" + token);
        //임시 자동 로그인 --> 토큰을 서버에 보내서 토큰이 유효한지 확인하는 방향으로 수정 할 것!!
        if(token.isEmpty()) {
            logInStatus = false;
            Log.d("TAG2", "비로그인");
        }else {
            logInStatus = true;
            Log.d("TAG3", "로그인");
        }
    }

    public static boolean isLogInStatus() {
        return logInStatus;
    }

    public static void setLogInStatus(boolean logInStatus) {
        MyApplication.logInStatus = logInStatus;
    }
}
