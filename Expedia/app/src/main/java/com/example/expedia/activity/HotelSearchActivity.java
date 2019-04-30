package com.example.expedia.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expedia.dialog.CustomDialogListener;
import com.example.expedia.dialog.HotelPersonNumDialog;
import com.example.expedia.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HotelSearchActivity extends AppCompatActivity {
    ImageView cancelImage;
    TextView destination,date,person;
    Button searchbtn;
    HotelPersonNumDialog dialog;
    String destinationData, date_start, date_end, person_num, search_date_start, search_date_end;
    Calendar calendar_start = Calendar.getInstance();
    Calendar calendar_end = Calendar.getInstance();
    int adult, kid;
    ArrayList<Integer> kidAge;

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
                startActivityForResult(new Intent(HotelSearchActivity.this, HotelDestinationActivity.class),0);
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
        kidAge = new ArrayList<>();
        dialog = new HotelPersonNumDialog(this);
        person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setDialogListener(dialogListener);
                dialog.setCancelable(false);
                dialog.show();
            }
        });
        searchbtn = findViewById(R.id.search_button);
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(destination.getText().toString().equals("목적지 입력")|| date.getText().toString().equals("날짜 선택")||person.getText().toString().equals("인원 수")){
                    Toast.makeText(HotelSearchActivity.this, HotelSearchActivity.this.getResources().getString(R.string.check_condition), Toast.LENGTH_SHORT).show();
                    return;
                }
                int people = adult+kid;
                String url = "hotel_filter?Name="+destinationData+"&Sdate="+search_date_start+"&Edate="+search_date_end+"&People="+people;
                Intent intent = new Intent(HotelSearchActivity.this, HotelListActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("no",3);
                startActivity(intent);
                Log.e("TAG","url="+url);
            }
        });
        startActivityForResult(new Intent(HotelSearchActivity.this, HotelDestinationActivity.class),0);
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
            search_date_start = year +"-"+monthOfYear+"-"+dayOfMonth;
            SimpleDateFormat dateFormatOrigin = new SimpleDateFormat("yyyy-M-d", Locale.KOREA);
            SimpleDateFormat dateFormatWant = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
            try{
                Date date = dateFormatOrigin.parse(search_date_start);
                search_date_start = dateFormatWant.format(date);
            }catch(ParseException e){
                e.printStackTrace();
            }
            dialog.show();
        }
    };

    private CustomDialogListener dialogListener = new CustomDialogListener() {
        @Override
        public void DialogListener(int adult, int kid, ArrayList<Integer> kidAge) {
            HotelSearchActivity.this.adult = adult;
            HotelSearchActivity.this.kid = kid;
            Log.d("TAG1", "아동의 나이 : " + kidAge);
            if(kidAge.size() > kid){
                for(int i=kidAge.size()-1; i >=kid; i--) {
                    kidAge.remove(kid);
                }
            }
            HotelSearchActivity.this.kidAge = kidAge;
            Log.d("TAG2", "아동의 나이 : " + kidAge);
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
            search_date_end = year +"-"+month+"-"+dayOfMonth;
            SimpleDateFormat dateFormatOrigin = new SimpleDateFormat("yyyy-M-d", Locale.KOREA);
            SimpleDateFormat dateFormatWant = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
            try{
                Date date = dateFormatOrigin.parse(search_date_end);
                search_date_end = dateFormatWant.format(date);
            }catch(ParseException e){
                e.printStackTrace();
            }
            String dateText = date_start +"~"+date_end +"("+night+"박)";
            date.setText(dateText);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode ==0){
            destinationData = data.getStringExtra("destination");
            destination.setText(destinationData);
        }
    }
}
