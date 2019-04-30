package com.example.expedia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expedia.R;
import com.example.expedia.datamanager.HttpConnection;
import com.example.expedia.datamanager.ReservationCallback;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ReservationActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etLastName,etFirstName;
    private int rNo;
    private String sDate,eDate,json;
    private HttpConnection httpConnection;
    private ReservationCallback callback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        ImageView ivCancel = findViewById(R.id.cancel_btn);
        ivCancel.setOnClickListener(this);

        Intent intent = getIntent();
        rNo = intent.getIntExtra("rNo",-1);
        sDate = intent.getStringExtra("sDate");
        eDate = intent.getStringExtra("eDate");

        TextView tvHotelName = findViewById(R.id.hotelName_textView);
        tvHotelName.setText(intent.getStringExtra("hotelName"));
        TextView tvRoom = findViewById(R.id.room_textView);
        tvRoom.setText(intent.getStringExtra("room"));
        TextView tvOption = findViewById(R.id.option_textView);
        tvOption.setText(intent.getStringExtra("option"));
        TextView tvCheckInDate = findViewById(R.id.checkIn_textView);
        tvCheckInDate.setText(sDate);
        TextView tvCheckOutDate = findViewById(R.id.checkOut_textView);
        tvCheckOutDate.setText(eDate);
        TextView tvPrice = findViewById(R.id.price_textView);
        String price = "￦"+intent.getStringExtra("price");
        tvPrice.setText(price);

        etLastName = findViewById(R.id.lastName_textView);
        etFirstName = findViewById(R.id.firstName_textView);

        TextView tvChangeCondition = findViewById(R.id.changeCondition_textView);
        tvChangeCondition.setOnClickListener(this);

        Button btnReservation = findViewById(R.id.reservation_btn);
        btnReservation.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel_btn:
            case R.id.changeCondition_textView:
                ReservationActivity.this.finish();
                break;

            case R.id.reservation_btn:
                String lastName = etLastName.getText().toString();
                String firstName = etFirstName.getText().toString();
                if (lastName.isEmpty() || firstName.isEmpty()) {
                    Toast.makeText(ReservationActivity.this, "빈 칸을 입력해 주세요", Toast.LENGTH_SHORT).show();
                    break;
                }
                Gson body = new Gson();
                JsonObject object = new JsonObject();
                object.addProperty("FName", firstName);
                object.addProperty("LName", lastName);
                object.addProperty("Sdate",sDate);
                object.addProperty("Edate",eDate);
                object.addProperty("Rno",rNo);
                json = body.toJson(object);
                sendData();
                break;
        }
    }

    private void sendData(){
        httpConnection = new HttpConnection(ReservationActivity.this, "book", "post", json);
        callback = new ReservationCallback(ReservationActivity.this);
        new Thread() {
            public void run() {
                httpConnection.requestWebServer(callback);
            }
        }.start();
    }
}
