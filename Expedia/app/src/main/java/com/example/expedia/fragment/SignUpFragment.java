package com.example.expedia.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.expedia.R;
import com.example.expedia.datamanager.HttpConnection;
import com.example.expedia.datamanager.SignUpCallback;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    private String Email, Pw, Name, json;
    private EditText etEmail, etPassword, etFirstName;
    private Activity activity;
    private HttpConnection httpConnection;
    private SignUpCallback callback;

    public SignUpFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        etFirstName = view.findViewById(R.id.signUp_firstName);
        activity = getActivity();
        etEmail = view.findViewById(R.id.signUp_email);
        etPassword = view.findViewById(R.id.signUp_password);
        Button sign_up = view.findViewById(R.id.signUp_btn);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = etFirstName.getText().toString();
                Email = etEmail.getText().toString();
                Pw = etPassword.getText().toString();
                Gson body = new Gson();
                JsonObject object = new JsonObject();
                object.addProperty("Email", Email);
                object.addProperty("Pw", Pw);
                object.addProperty("Name", Name);
                json = body.toJson(object);
                sendData();
            }
        });
        return view;
    }

    private void sendData() {
        httpConnection = new HttpConnection(activity, "user", "post", json);
        callback = new SignUpCallback(activity);
        new Thread() {
            public void run() {
                httpConnection.requestWebServer(callback);
            }
        }.start();
    }
}
