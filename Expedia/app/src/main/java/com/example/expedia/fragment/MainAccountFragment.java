package com.example.expedia.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.expedia.MyApplication;
import com.example.expedia.R;
import com.example.expedia.activity.EmptyActivity;
import com.example.expedia.activity.LogInActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainAccountFragment extends Fragment {
    private Button btnLogin, btnLogout;

    public MainAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public void onStart() {
        super.onStart();
        if (MyApplication.isLogInStatus()){
            btnLogin.setVisibility(View.INVISIBLE);
            btnLogout.setVisibility(View.VISIBLE);
        }else{
            btnLogin.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_account, container, false);
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
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                MyApplication.setLogInStatus(false);
                btnLogin.setVisibility(View.VISIBLE);
                btnLogout.setVisibility(View.INVISIBLE);
                startActivity(new Intent(getActivity(), EmptyActivity.class));
            }
        });

        return view;
    }

}
