package com.example.expedia;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class LogInSignUpFragment extends Fragment {

    EditText firstName, lastName, email, password;
    Button sign_up;

    public LogInSignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_in_sign_up, container, false);
        firstName = view.findViewById(R.id.signUp_firstName);
        lastName = view.findViewById(R.id.signUp_lastName);
        email = view.findViewById(R.id.signUp_email);
        password = view.findViewById(R.id.signUp_password);
        sign_up = view.findViewById(R.id.signUp_btn);

        return view;
    }

}
