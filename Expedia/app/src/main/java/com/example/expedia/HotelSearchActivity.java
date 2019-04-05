package com.example.expedia;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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

import java.util.ArrayList;
import java.util.Calendar;

public class HotelSearchActivity extends AppCompatActivity {
    ImageView cancelImage;
    TextView destination,date,person;
    Button searchbtn;
    HotelPersonNumDialog dialog;
    String destinationData, date_start, date_end, person_num;
    Calendar calendar_start = Calendar.getInstance();
    Calendar calendar_end = Calendar.getInstance();
    static int adult, kid;
    static ArrayList<Integer> kidAge;

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

        //목적지 선택
        destination = findViewById(R.id.destination_text);
        destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HotelSearchActivity.this, HotelDestinationActivity.class));
            }
        });

        //날짜
        date = findViewById(R.id.date_text);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DatePickerDialog
                DatePickerDialog dialog = new DatePickerDialog(HotelSearchActivity.this,
                        listener, calendar_start.get(Calendar.YEAR), calendar_start.get(Calendar.MONTH),
                        calendar_start.get(Calendar.DAY_OF_MONTH));
                long now = System.currentTimeMillis();
                dialog.getDatePicker().setMinDate(now);
                dialog.show();
            }
        });

        //인원 수
        person = findViewById(R.id.person_text);
        adult = 1;
        kid = 0;
        kidAge = new ArrayList<>();
        dialog = new HotelPersonNumDialog(this, dialogListener);
        person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setCancelable(false);
                dialog.show();
            }
        });
        searchbtn = findViewById(R.id.search_button);
        startActivity(new Intent(HotelSearchActivity.this, HotelDestinationActivity.class));
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar_start.set(year,monthOfYear,dayOfMonth);
            DatePickerDialog dialog = new DatePickerDialog(HotelSearchActivity.this, listener2, year, monthOfYear, dayOfMonth);
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth+1);
            dialog.getDatePicker().setMinDate(calendar.getTime().getTime());
            monthOfYear += 1;
            date_start = monthOfYear+"월 "+ dayOfMonth +"일";
            dialog.show();
        }
    };

    private Dialog.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            int people = adult+kid;
            person_num = people +"명";
            person.setText(person_num);
        }
    };

    private  DatePickerDialog.OnDateSetListener listener2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar_end.set(year, month, dayOfMonth);
            month += 1;
            int night = calendar_end.get(Calendar.DATE) - calendar_start.get(Calendar.DATE);
            date_end = month+"월 "+dayOfMonth+"일 ";
            String dateText = date_start +"~"+date_end +"("+night+"박)";
            date.setText(dateText);
        }
    };
}
