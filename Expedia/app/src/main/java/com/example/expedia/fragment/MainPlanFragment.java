package com.example.expedia.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expedia.MyApplication;
import com.example.expedia.R;
import com.example.expedia.activity.LogInSignUpActivity;
import com.example.expedia.adapter.PlanRVAdapter;
import com.example.expedia.datamanager.CheckReservationCallback;
import com.example.expedia.datamanager.HttpConnection;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainPlanFragment extends Fragment {
    private Button btnLogin,btnRefresh;
    public TextView tvPlanStatus;
    private Context context;
    public RecyclerView rvPlan;
    public PlanRVAdapter adapter = new PlanRVAdapter();
    private HttpConnection httpConnection;
    private CheckReservationCallback callback;

    public MainPlanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_plan, container, false);
        context = getContext();
        LinearLayout llPlusPlanSelf = view.findViewById(R.id.plusPlanSelf_layout);
        llPlusPlanSelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "비회원 여행 추가", Toast.LENGTH_SHORT).show();
            }
        });
        rvPlan = view.findViewById(R.id.plan_recyclerView);
        rvPlan.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPlan.setAdapter(adapter);
        btnLogin = view.findViewById(R.id.login_button);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, LogInSignUpActivity.class));
            }
        });
        tvPlanStatus = view.findViewById(R.id.planStatus_textView);
        btnRefresh = view.findViewById(R.id.refresh_button);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
                //Toast.makeText(context, "여행 새로고침", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        checkLoginStatus();
    }

    public void checkLoginStatus(){
        if(MyApplication.isLogInStatus()){
            btnLogin.setVisibility(View.GONE);
            btnRefresh.setVisibility(View.VISIBLE);
            tvPlanStatus.setText(context.getResources().getString(R.string.no_plan));
            sendData();
        }else{
            btnLogin.setVisibility(View.VISIBLE);
            btnRefresh.setVisibility(View.GONE);
            rvPlan.setVisibility(View.GONE);
            tvPlanStatus.setVisibility(View.VISIBLE);
            tvPlanStatus.setText(context.getResources().getString(R.string.login_to_check_plan));
        }
    }

    private void sendData(){
        httpConnection = new HttpConnection(getContext(), "book","get");
        callback = new CheckReservationCallback(MainPlanFragment.this);
        new Thread() {
            public void run() {
                httpConnection.requestWebServer(callback);
            }
        }.start();
    }
}
