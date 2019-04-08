package com.example.expedia.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.expedia.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class LogInFragment extends Fragment {

    ImageView login_facebook, login_google;
    EditText email, password;
    Button log_in;
    TextView forgotPassword;

    public LogInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        login_facebook = view.findViewById(R.id.login_facebook_btn);
        login_google = view.findViewById(R.id.login_google_btn);
        email = view.findViewById(R.id.login_email);
        password = view.findViewById(R.id.login_password);
        log_in = view.findViewById(R.id.login_btn);
        forgotPassword = view.findViewById(R.id.find_password);
        return view;
    }

}
