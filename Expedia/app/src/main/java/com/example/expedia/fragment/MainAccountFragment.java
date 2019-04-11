package com.example.expedia.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expedia.MyApplication;
import com.example.expedia.R;
import com.example.expedia.activity.LogInActivity;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainAccountFragment extends Fragment {
    private Button btnLogin, btnLogout;
    private deleteUserConnection httpConn = new deleteUserConnection();
    private SharedPreferences sharedPreferences;
    private String message;

    public MainAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_account, container, false);
        sharedPreferences = getActivity().getSharedPreferences("login",Context.MODE_PRIVATE);
        btnLogin = view.findViewById(R.id.login_button);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LogInActivity.class));
            }
        });
        btnLogout = view.findViewById(R.id.logout_button);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                MyApplication.setLogInStatus(false);
                btnLogin.setVisibility(View.VISIBLE);
                btnLogout.setVisibility(View.INVISIBLE);
                }
        });

        TextView tvDeleteMyInfo = view.findViewById(R.id.deleteMyInfo_textView);
        tvDeleteMyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogInStatus()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(getActivity().getResources().getString(R.string.delete_my_info));
                    builder.setMessage(getActivity().getResources().getString(R.string.delete_info_alert));
                    builder.setCancelable(false);
                    builder.setPositiveButton(getActivity().getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sendData();
                        }
                    });
                    builder.setNegativeButton(getActivity().getResources().getString(R.string.no), null);
                    AlertDialog ad = builder.create();
                    ad.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(getActivity().getResources().getString(R.string.delete_search_record));
                    builder.setMessage(getActivity().getResources().getString(R.string.delete_search_record_alert));
                    builder.setCancelable(false);
                    builder.setPositiveButton(getActivity().getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.setNegativeButton(getActivity().getResources().getString(R.string.no), null);
                    AlertDialog ad = builder.create();
                    ad.show();
                }
            }
        });



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        checkLoginStatus();
    }

    private void checkLoginStatus(){
        if (MyApplication.isLogInStatus()){
            btnLogin.setVisibility(View.INVISIBLE);
            btnLogout.setVisibility(View.VISIBLE);
        }else{
            btnLogin.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.INVISIBLE);
        }
    }
    private class deleteUserConnection {

        private OkHttpClient client;

        private deleteUserConnection() {
            this.client = new OkHttpClient();
        }
        private void requestWebServer(Callback callback) {
            String token = sharedPreferences.getString("jwt", "0");
            Request request = new Request.Builder()
                    .addHeader("x-access-token", token)
                    .url("http://www.kaca5.com/expedia/user")
                    .delete()
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

            message = getActivity().getResources().getString(R.string.callback_error);
            Log.d(TAG, "message:"+message);
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                }
            });
        }
        @Override
        public void onResponse(@NonNull Call call, Response response) throws IOException {
            String body = response.body().string();
            Log.d("TAG","body = "+body);
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(body);
            if(element.getAsJsonObject().get("code").getAsInt() != 100){
                message = element.getAsJsonObject().get("message").getAsString();
                Log.d(TAG, "message:"+message);
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.invalid_token), Toast.LENGTH_SHORT).show();
                    }
                });
            }else{

            }
            Log.d(TAG, "서버에서 응답한 Body:"+element);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            MyApplication.setLogInStatus(false);
        }
    };
}
