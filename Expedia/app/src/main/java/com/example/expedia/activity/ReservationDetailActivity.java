package com.example.expedia.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.expedia.R;
import com.example.expedia.data.ReservationRoomData;
import com.example.expedia.datamanager.CancelReservationCallback;
import com.example.expedia.datamanager.HttpConnection;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ReservationDetailActivity extends AppCompatActivity {
    private HttpConnection httpConnection;
    private CancelReservationCallback callback;
    private String jsonSend;
    private ReservationRoomData room;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        Intent intent = getIntent();
        String json = intent.getStringExtra("json");
        Gson gson = new Gson();
        room = gson.fromJson(json, ReservationRoomData.class);

        TextView tvTitle = findViewById(R.id.title_textView);
        tvTitle.setText(ReservationDetailActivity.this.getResources().getString(R.string.check_reservation));

        TextView tvHotelName = findViewById(R.id.hotelName_textView);
        tvHotelName.setText(room.getName());

        TextView tvRoom = findViewById(R.id.room_textView);
        tvRoom.setText(room.getGrade());

        TextView tvOption = findViewById(R.id.option_textView);
        String option = "(침대:"+room.getBed()+", 무료 주차장, 무료 와이파이)";
        tvOption.setText(option);

        TextView tvCheckIn = findViewById(R.id.checkIn_textView);
        tvCheckIn.setText(room.getSdate());

        TextView tvCheckOut = findViewById(R.id.checkOut_textView);
        tvCheckOut.setText(room.getEdate());

        LinearLayout llPrice = findViewById(R.id.price_layout);
        llPrice.setVisibility(View.GONE);

        LinearLayout llRating = findViewById(R.id.rating_layout);
        llRating.setVisibility(View.VISIBLE);

        TextView tvRating = findViewById(R.id.rating_textView);
        String rating = "★ "+ room.getRatings();
        tvRating.setText(rating);

        TextView tvAdditionalInfo = findViewById(R.id.additionalInfo_textView);
        tvAdditionalInfo.setVisibility(View.GONE);
        LinearLayout llLastName = findViewById(R.id.lastName_layout);
        llLastName.setVisibility(View.GONE);
        LinearLayout llFirstName = findViewById(R.id.firstName_layout);
        llFirstName.setVisibility(View.GONE);
        TextView tvChangeCondition = findViewById(R.id.changeCondition_textView);
        tvChangeCondition.setVisibility(View.GONE);

        Button btnReservation = findViewById(R.id.reservation_btn);
        btnReservation.setText(ReservationDetailActivity.this.getResources().getString(R.string.cancel_reservation));
        btnReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ReservationDetailActivity.this);
                builder.setTitle(ReservationDetailActivity.this.getResources().getString(R.string.cancel_reservation));
                builder.setMessage(ReservationDetailActivity.this.getResources().getString(R.string.cancel_reservation_alert));
                builder.setCancelable(false);
                builder.setPositiveButton(ReservationDetailActivity.this.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Gson body = new Gson();
                        JsonObject object = new JsonObject();
                        object.addProperty("Rno", room.getRno());
                        jsonSend = body.toJson(object);
                        sendData();
                        finish();
                    }
                });
                builder.setNegativeButton(ReservationDetailActivity.this.getResources().getString(R.string.no), null);
                AlertDialog ad = builder.create();
                ad.show();

            }
        });
    }

    private void sendData(){
        httpConnection = new HttpConnection(ReservationDetailActivity.this, "book", "delete", jsonSend);
        callback = new CancelReservationCallback(ReservationDetailActivity.this);
        new Thread() {
            public void run() {
                httpConnection.requestWebServer(callback);
            }
        }.start();
    }
}
