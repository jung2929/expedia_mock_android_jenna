package com.example.expedia.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.expedia.R;
import com.example.expedia.activity.LogInActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainAccountFragment extends Fragment {


    public MainAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_account, container, false);
        Button login_btn = view.findViewById(R.id.button);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LogInActivity.class));
            }
        });
        return view;
    }

}
