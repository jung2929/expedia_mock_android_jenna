package com.example.expedia.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.expedia.R;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class HotelPersonNumDialog extends Dialog{
    private Spinner spinner1, spinner2, spinner3, spinner4, spinner5, spinner6;
    private ImageView plus_active, plus_inactive, minus_active, minus_inactive, plus_active_kid, plus_inactive_kid, minus_active_kid, minus_inactive_kid;
    private TextView adultTextView, kidTextView, selectKidAge, complete;
    private String adultText, kidText;
    private LinearLayout one, two, three;
    private int adult, kid;
    private ArrayList<Integer> kidAge;
    private ArrayAdapter<String> spinnerAdapter;
    private CustomDialogListener dialogListener;

    public HotelPersonNumDialog(@NonNull Context context){
        super(context);
        //this.listener = listener;
    }

    public void setDialogListener(CustomDialogListener dialogListener){
        this.dialogListener = dialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_person_num);

        adult = 1;
        kid = 0;
        kidAge = new ArrayList<>();
        spinner1 = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        spinner4 = findViewById(R.id.spinner4);
        spinner5 = findViewById(R.id.spinner5);
        spinner6 = findViewById(R.id.spinner6);

        one = findViewById(R.id.spinnerLayout1);
        two = findViewById(R.id.spinnerLayout2);
        three = findViewById(R.id.spinnerLayout3);
        plus_active = findViewById(R.id.plus_active_adult);
        plus_inactive = findViewById(R.id.plus_inactive_adult);
        plus_active_kid = findViewById(R.id.plus_active_kid);
        plus_inactive_kid = findViewById(R.id.plus_inactive_kid);
        minus_active = findViewById(R.id.minus_active_adult);
        minus_inactive = findViewById(R.id.minus_inactive_adult);
        minus_active_kid = findViewById(R.id.minus_active_kid);
        minus_inactive_kid = findViewById(R.id.minus_inactive_kid);

        ArrayList<String> list = new ArrayList<>();

        for(int i=0;i<18;i++){
            list.add("만 "+ i +"세");
        }

        spinnerAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item,list);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_drop_down_item);

        selectKidAge = findViewById(R.id.select_age);
        complete = findViewById(R.id.complete);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogListener.DialogListener(adult, kid, kidAge);
                dismiss();
            }
        });
        adultTextView = findViewById(R.id.person_adult);
        adultText = "어른 " + adult + "명";
        adultTextView.setText(adultText);
        checkAdultNum(adult);

        plus_active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adult += 1;
                adultText = "어른 " + adult + "명";
                adultTextView.setText(adultText);
                checkAdultNum(adult);
            }
        });
        minus_active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adult -= 1;
                adultText = "어른 " + adult + "명";
                adultTextView.setText(adultText);
                checkAdultNum(adult);
            }
        });

        kidTextView = findViewById(R.id.person_kid);
        kidText = "아동 " + kid +"명";
        kidTextView.setText(kidText);
        checkKidNum(kid);
        checkSpinner(kid);
        plus_active_kid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kid += 1;
                kidText = "아동 " + kid + "명";
                kidTextView.setText(kidText);
                checkKidNum(kid);
                checkSpinner(kid);
            }
        });
        minus_active_kid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kid -= 1;
                kidText = "아동 " + kid + "명";
                kidTextView.setText(kidText);
                checkKidNum(kid);
                checkSpinner(kid);
            }
        });



    }

    private void checkAdultNum(int adult){
        if (adult == 1){
            minus_inactive.setVisibility(View.VISIBLE);
            minus_active.setVisibility(View.INVISIBLE);
            plus_inactive.setVisibility(View.INVISIBLE);
            plus_active.setVisibility(View.VISIBLE);
        } else if(adult == 14) {
            minus_inactive.setVisibility(View.INVISIBLE);
            minus_active.setVisibility(View.VISIBLE);
            plus_inactive.setVisibility(View.VISIBLE);
            plus_active.setVisibility(View.INVISIBLE);
        } else {
            minus_inactive.setVisibility(View.INVISIBLE);
            minus_active.setVisibility(View.VISIBLE);
            plus_inactive.setVisibility(View.INVISIBLE);
            plus_active.setVisibility(View.VISIBLE);
        }
    }

    private void checkKidNum(int kid){
        if (kid == 0){
            minus_inactive_kid.setVisibility(View.VISIBLE);
            minus_active_kid.setVisibility(View.INVISIBLE);
            plus_inactive_kid.setVisibility(View.INVISIBLE);
            plus_active_kid.setVisibility(View.VISIBLE);
            selectKidAge.setVisibility(View.INVISIBLE);
        } else if(kid == 6) {
            minus_inactive_kid.setVisibility(View.INVISIBLE);
            minus_active_kid.setVisibility(View.VISIBLE);
            plus_inactive_kid.setVisibility(View.VISIBLE);
            plus_active_kid.setVisibility(View.INVISIBLE);
            selectKidAge.setVisibility(View.VISIBLE);
        } else {
            minus_inactive_kid.setVisibility(View.INVISIBLE);
            minus_active_kid.setVisibility(View.VISIBLE);
            plus_inactive_kid.setVisibility(View.INVISIBLE);
            plus_active_kid.setVisibility(View.VISIBLE);
            selectKidAge.setVisibility(View.VISIBLE);
        }
    }
    private void checkSpinner(final int kid){
        switch(kid){
            case 0:
                spinner1.setVisibility(View.INVISIBLE);
                spinner2.setVisibility(View.INVISIBLE);
                spinner3.setVisibility(View.INVISIBLE);
                spinner4.setVisibility(View.INVISIBLE);
                spinner5.setVisibility(View.INVISIBLE);
                spinner6.setVisibility(View.INVISIBLE);
                one.setVisibility(View.GONE);
                two.setVisibility(View.GONE);
                three.setVisibility(View.GONE);
                kidAge.clear();


                break;

            case 1:
                spinner1.setVisibility(View.VISIBLE);
                spinner2.setVisibility(View.INVISIBLE);
                spinner3.setVisibility(View.INVISIBLE);
                spinner4.setVisibility(View.INVISIBLE);
                spinner5.setVisibility(View.INVISIBLE);
                spinner6.setVisibility(View.INVISIBLE);
                one.setVisibility(View.VISIBLE);
                two.setVisibility(View.GONE);
                three.setVisibility(View.GONE);

                if (kidAge.size() == 2){
                    kidAge.remove(1);
                }
                spinner1.setAdapter(spinnerAdapter);
                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(kidAge.size() >= 1){
                            kidAge.clear();
                        }
                        kidAge.add(0,position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                try {
                    Field popup = Spinner.class.getDeclaredField("mPopup");
                    popup.setAccessible(true);
                    ListPopupWindow window = (ListPopupWindow)popup.get(spinner1);
                    window.setHeight(700); //pixel
                } catch (Exception e) {
                    e.printStackTrace();
                }
                spinner1.setSelection(10);

                break;

            case 2:
                spinner1.setVisibility(View.VISIBLE);
                spinner2.setVisibility(View.VISIBLE);
                spinner2.setAdapter(spinnerAdapter);
                spinner2.setSelection(10);
                spinner3.setVisibility(View.INVISIBLE);
                spinner4.setVisibility(View.INVISIBLE);
                spinner5.setVisibility(View.INVISIBLE);
                spinner6.setVisibility(View.INVISIBLE);
                one.setVisibility(View.VISIBLE);
                two.setVisibility(View.GONE);
                three.setVisibility(View.GONE);
                if (kidAge.size() == 3){
                    kidAge.remove(2);
                }
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(kidAge.size() >= 2 ){
                            kidAge.remove(1);
                        }
                        kidAge.add(1, position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                try {
                    Field popup = Spinner.class.getDeclaredField("mPopup");
                    popup.setAccessible(true);
                    ListPopupWindow window = (ListPopupWindow)popup.get(spinner2);
                    window.setHeight(700); //pixel
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case 3:
                spinner1.setVisibility(View.VISIBLE);
                spinner2.setVisibility(View.VISIBLE);
                spinner3.setVisibility(View.VISIBLE);
                spinner3.setAdapter(spinnerAdapter);
                spinner3.setSelection(10);
                spinner4.setVisibility(View.INVISIBLE);
                spinner5.setVisibility(View.INVISIBLE);
                spinner6.setVisibility(View.INVISIBLE);
                one.setVisibility(View.VISIBLE);
                two.setVisibility(View.VISIBLE);
                three.setVisibility(View.GONE);
                if (kidAge.size() == 4){
                    kidAge.remove(3);
                }
                spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(kidAge.size() >= 3 ){
                            kidAge.remove(2);
                        }
                        kidAge.add(2, position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                try {
                    Field popup = Spinner.class.getDeclaredField("mPopup");
                    popup.setAccessible(true);
                    ListPopupWindow window = (ListPopupWindow)popup.get(spinner3);
                    window.setHeight(700); //pixel
                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;

            case 4:
                spinner1.setVisibility(View.VISIBLE);
                spinner2.setVisibility(View.VISIBLE);
                spinner3.setVisibility(View.VISIBLE);
                spinner4.setVisibility(View.VISIBLE);
                spinner4.setAdapter(spinnerAdapter);
                spinner4.setSelection(10);
                spinner5.setVisibility(View.INVISIBLE);
                spinner6.setVisibility(View.INVISIBLE);
                one.setVisibility(View.VISIBLE);
                two.setVisibility(View.VISIBLE);
                three.setVisibility(View.GONE);
                if (kidAge.size() == 5){
                    kidAge.remove(4);
                }
                spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(kidAge.size() >= 4 ){
                            kidAge.remove(3);
                        }
                        kidAge.add(3, position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                try {
                    Field popup = Spinner.class.getDeclaredField("mPopup");
                    popup.setAccessible(true);
                    ListPopupWindow window = (ListPopupWindow)popup.get(spinner4);
                    window.setHeight(700); //pixel
                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;

            case 5:
                spinner1.setVisibility(View.VISIBLE);
                spinner2.setVisibility(View.VISIBLE);
                spinner3.setVisibility(View.VISIBLE);
                spinner4.setVisibility(View.VISIBLE);
                spinner5.setVisibility(View.VISIBLE);
                spinner5.setAdapter(spinnerAdapter);
                spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(kidAge.size() >= 5){
                            kidAge.remove(4);
                        }
                        kidAge.add(4, position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                try {
                    Field popup = Spinner.class.getDeclaredField("mPopup");
                    popup.setAccessible(true);
                    ListPopupWindow window = (ListPopupWindow)popup.get(spinner5);
                    window.setHeight(700); //pixel
                } catch (Exception e) {
                    e.printStackTrace();
                }

                spinner5.setSelection(10);
                spinner6.setVisibility(View.INVISIBLE);
                one.setVisibility(View.VISIBLE);
                two.setVisibility(View.VISIBLE);
                three.setVisibility(View.VISIBLE);

                if (kidAge.size() == 6){
                    kidAge.remove(5);
                }

                break;

            case 6:
                spinner1.setVisibility(View.VISIBLE);
                spinner2.setVisibility(View.VISIBLE);
                spinner3.setVisibility(View.VISIBLE);
                spinner4.setVisibility(View.VISIBLE);
                spinner5.setVisibility(View.VISIBLE);
                spinner6.setVisibility(View.VISIBLE);
                spinner6.setAdapter(spinnerAdapter);
                spinner6.setSelection(10);
                one.setVisibility(View.VISIBLE);
                two.setVisibility(View.VISIBLE);
                three.setVisibility(View.VISIBLE);
                spinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(kidAge.size() == 6 ){
                            kidAge.remove(5);
                        }
                        kidAge.add(5, position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                try {
                    Field popup = Spinner.class.getDeclaredField("mPopup");
                    popup.setAccessible(true);
                    ListPopupWindow window = (ListPopupWindow)popup.get(spinner6);
                    window.setHeight(700); //pixel
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

}
