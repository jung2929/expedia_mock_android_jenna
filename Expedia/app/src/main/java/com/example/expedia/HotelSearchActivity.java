package com.example.expedia;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

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
        destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HotelSearchActivity.this, HotelDestinationActivity.class));
            }
        });
        date = findViewById(R.id.date_text);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DatePickerDialog
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(HotelSearchActivity.this, listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                long now = System.currentTimeMillis();
                dialog.getDatePicker().setMinDate(now);
                dialog.show();
            }
        });
        person = findViewById(R.id.person_text);
        searchbtn = findViewById(R.id.search_button);
        startActivity(new Intent(HotelSearchActivity.this, HotelDestinationActivity.class));
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            monthOfYear += 1;
            Toast.makeText(getApplicationContext(), year + "년" + monthOfYear + "월" + dayOfMonth +"일", Toast.LENGTH_SHORT).show();
        }
    };
}
