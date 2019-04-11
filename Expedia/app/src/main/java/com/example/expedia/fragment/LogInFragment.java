package com.example.expedia.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expedia.MyApplication;
import com.example.expedia.R;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class LogInFragment extends Fragment {

    private LogInConnection httpConn = new LogInConnection();
    private String email, password, message, json;
    private Activity activity;
    private EditText etEmail, etPassword;
    private SharedPreferences sharedPreferences;

    public LogInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        activity = getActivity();
        sharedPreferences = activity.getSharedPreferences("login", Context.MODE_PRIVATE);
        ImageView ivLogin_facebook = view.findViewById(R.id.login_facebook_btn);
        ivLogin_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "페이스북 로그인", Toast.LENGTH_SHORT).show();
            }
        });
        ImageView ivLogin_google = view.findViewById(R.id.login_google_btn);
        ivLogin_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "구글 로그인", Toast.LENGTH_SHORT).show();
            }
        });

        etEmail = view.findViewById(R.id.login_email);
        etPassword = view.findViewById(R.id.login_password);

        Button BtnLog_in = view.findViewById(R.id.login_btn);
        BtnLog_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                Gson body = new Gson();
                JsonObject object = new JsonObject();
                object.addProperty("Email", email);
                object.addProperty("Pw", password);
                json = body.toJson(object);
                sendData();
            }
        });

        TextView tvForgotPassword = view.findViewById(R.id.find_password);
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "비밀번호 찾기", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    private class LogInConnection {

        private OkHttpClient client;

        private LogInConnection() {
            this.client = new OkHttpClient();
        }
        private void requestWebServer(Callback callback) {
            Request request = new Request.Builder()
                    .url("http://www.kaca5.com/expedia/login")
                    .post(RequestBody.create(MediaType.parse("application/json"), json))
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

            message = activity.getResources().getString(R.string.callback_error);
            Log.d(TAG, "message:"+message);
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
        @Override
        public void onResponse(@NonNull Call call, Response response) throws IOException {
            String body = response.body().string();
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(body);
            if(element.getAsJsonObject().get("code").getAsInt() != 100){
                message = element.getAsJsonObject().get("message").getAsString();
                Log.d(TAG, "message:"+message);
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                JsonElement token1 = element.getAsJsonObject().get("token");
                String token = token1.getAsJsonObject().get("jwt").getAsString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("jwt",token);
                editor.apply();
                MyApplication.setLogInStatus(true);
                activity.finish();
            }
            Log.d(TAG, "서버에서 응답한 Body:"+element);

        }
    };
}
