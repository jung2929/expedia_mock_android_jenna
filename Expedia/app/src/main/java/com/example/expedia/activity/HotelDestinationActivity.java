package com.example.expedia.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;

import com.example.expedia.R;

public class HotelDestinationActivity extends AppCompatActivity {
    private boolean activityStartup = true;
    SearchView searchView;
    ImageView back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_destination);

       searchView = findViewById(R.id.searchView);
       back = findViewById(R.id.cancel_btn);
       back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });

        // SearchView 입력 글자색과 힌트 색상 변경하기
        SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(Color.GRAY);
        searchAutoComplete.setTextColor(Color.BLACK);

        // Inside onCreate

        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (activityStartup) {
                        searchView.clearFocus();
                        activityStartup = false;
                    }
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast.makeText(HotelDestinationActivity.this, "[검색버튼클릭] 검색어 = "+query, Toast.LENGTH_LONG).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Toast.makeText(HotelDestinationActivity.this, "입력하고있는 단어 = "+newText, Toast.LENGTH_LONG).show();
                return true;
            }
        });

    }
}
