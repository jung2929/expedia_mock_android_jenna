package com.example.expedia.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.example.expedia.R;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class LogInSignUpFragment extends Fragment {

    private SignUpConnection httpConn = new SignUpConnection();
    private String Email, Pw, Name, message, json;
    private EditText etEmail, etPassword, etFirstName, etLastName;
    private Activity activity;

    public LogInSignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_in_sign_up, container, false);
        etFirstName = view.findViewById(R.id.signUp_firstName);
        activity = getActivity();
        etLastName = view.findViewById(R.id.signUp_lastName);
        etEmail = view.findViewById(R.id.signUp_email);
        etPassword = view.findViewById(R.id.signUp_password);
        Button sign_up = view.findViewById(R.id.signUp_btn);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = etLastName.getText().toString()+etFirstName.getText().toString();
                Email = etEmail.getText().toString();
                Pw = etPassword.getText().toString();
                Gson body = new Gson();
                JsonObject object = new JsonObject();
                object.addProperty("Email", Email);
                object.addProperty("Pw", Pw);
                object.addProperty("Name", Name);
                json = body.toJson(object);
                sendData();
                getActivity().finish();
            }
        });


        return view;
    }

    private class SignUpConnection {

        private OkHttpClient client;

        private SignUpConnection() {
            this.client = new OkHttpClient();
        }
        private void requestWebServer(Callback callback) {
            Request request = new Request.Builder()
                    .url("http://www.kaca5.com/expedia/user")
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
            message = "오류가 발생했습니다. 잠시 후 다시 시도해 주세요";
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
            if(element.getAsJsonObject().get("code") != null){
                message = element.getAsJsonObject().get("message").getAsString();
                Log.d(TAG, "message:"+message);
            }else{
                message = "회원가입이 완료되었습니다. 로그인 후 사용해 주세요";
                Log.d(TAG, "message:"+message);
            }
            Log.d(TAG, "서버에서 응답한 Body:"+element);
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    };
}
