package com.example.expedia.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.expedia.MyApplication;
import com.example.expedia.R;
import com.example.expedia.activity.LogInSignUpActivity;
import com.example.expedia.datamanager.DeleteUserCallback;
import com.example.expedia.datamanager.HttpConnection;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainAccountFragment extends Fragment {
    private Button btnLogin, btnLogout;
    private SharedPreferences sharedPreferences;
    private HttpConnection httpConnection;
    private DeleteUserCallback callback;
    private Activity activity;

    public MainAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_account, container, false);
        activity = getActivity();
        assert activity != null;
        sharedPreferences = activity.getSharedPreferences("login",Context.MODE_PRIVATE);
        btnLogin = view.findViewById(R.id.login_button);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LogInSignUpActivity.class));
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
                MyApplication.setToken("");
                checkLoginStatus();
                }
        });

        TextView tvDeleteMyInfo = view.findViewById(R.id.deleteMyInfo_textView);
        tvDeleteMyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogInStatus()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(activity.getResources().getString(R.string.delete_my_info));
                    builder.setMessage(activity.getResources().getString(R.string.delete_info_alert));
                    builder.setCancelable(false);
                    builder.setPositiveButton(activity.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sendData();
                            btnLogin.setVisibility(View.VISIBLE);
                            btnLogout.setVisibility(View.GONE);
                        }
                    });
                    builder.setNegativeButton(activity.getResources().getString(R.string.no), null);
                    AlertDialog ad = builder.create();
                    ad.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(activity.getResources().getString(R.string.delete_search_record));
                    builder.setMessage(activity.getResources().getString(R.string.delete_search_record_alert));
                    builder.setCancelable(false);
                    builder.setPositiveButton(activity.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.setNegativeButton(activity.getResources().getString(R.string.no), null);
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
            btnLogin.setVisibility(View.GONE);
            btnLogout.setVisibility(View.VISIBLE);
        }else{
            btnLogin.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.GONE);
        }
    }

    private void sendData(){
        httpConnection = new HttpConnection(getContext(),"user","delete");
        callback = new DeleteUserCallback(activity);
        new Thread() {
            public void run() {
                httpConnection.requestWebServer(callback);
            }
        }.start();

    }
    /*private class deleteUserConnection {

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

    private void sendData() {
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
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.delete_user_success), Toast.LENGTH_SHORT).show();
                }
            });
        }
    };*/
}
