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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expedia.R;
import com.example.expedia.datamanager.HttpConnection;
import com.example.expedia.datamanager.LoginCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogInFragment extends Fragment {

    private String email, password, json, fcmToken;
    private Activity activity;
    private EditText etEmail, etPassword;
    private LoginCallback callback;
    private HttpConnection httpConnection;

    public LogInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        fcmToken = Objects.requireNonNull(task.getResult()).getToken();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, fcmToken);
                        Log.e("FirebaseId", msg);
                    }
                });
        activity = getActivity();
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
                object.addProperty("FCM",fcmToken);

                json = body.toJson(object);
                Log.e("input",json);
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

    private void sendData() {
        httpConnection = new HttpConnection(getContext(), "login", "post", json);
        callback = new LoginCallback(activity);
        new Thread() {
            public void run() {
                httpConnection.requestWebServer(callback);
            }
        }.start();
    }
}
