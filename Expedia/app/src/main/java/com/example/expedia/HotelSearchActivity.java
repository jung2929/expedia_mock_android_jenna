package com.example.expedia;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HotelSearchActivity extends AppCompatActivity {
    ImageView cancelImage;
    TextView destination,date,person;
    Button searchbtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_search);

        cancelImage = findViewById(R.id.cancel_btn);
        cancelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        destination = findViewById(R.id.destination_text);
        date = findViewById(R.id.date_text);
        person = findViewById(R.id.person_text);
        searchbtn = findViewById(R.id.search_button);
    }
}
