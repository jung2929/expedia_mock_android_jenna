package com.example.expedia.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expedia.MyApplication;
import com.example.expedia.R;
import com.example.expedia.activity.LogInActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainPlanFragment extends Fragment {
    private Button btnLogin,btnRefresh;
    private TextView tvPlanStatus;

    public MainPlanFragment() {
        // Required empty public constructor
    }


    @Override
    public void onStart() {
        super.onStart();
        if(MyApplication.isLogInStatus()){
            btnLogin.setVisibility(View.GONE);
            btnRefresh.setVisibility(View.VISIBLE);
            tvPlanStatus.setText(getActivity().getResources().getString(R.string.no_plan));
        }else{
            btnLogin.setVisibility(View.VISIBLE);
            btnRefresh.setVisibility(View.GONE);
            tvPlanStatus.setText(getActivity().getResources().getString(R.string.login_to_check_plan));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_plan, container, false);
        LinearLayout llPlusPlanSelf = view.findViewById(R.id.plusPlanSelf_layout);
        llPlusPlanSelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "비회원 여행 추가", Toast.LENGTH_SHORT).show();
            }
        });
        btnLogin = view.findViewById(R.id.login_button);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LogInActivity.class));
            }
        });
        tvPlanStatus = view.findViewById(R.id.planStatus_textView);
        btnRefresh = view.findViewById(R.id.refresh_button);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "여행 새로고침", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
