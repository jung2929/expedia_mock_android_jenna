package com.example.expedia.adapter;

import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expedia.MyApplication;
import com.example.expedia.R;
import com.example.expedia.activity.HotelDetailActivity;
import com.example.expedia.activity.LogInSignUpActivity;
import com.example.expedia.activity.ReservationActivity;
import com.example.expedia.data.HotelDetailRoomData;
import com.example.expedia.data.RoomOptionData;

import java.util.ArrayList;

public class RoomOptionRVAdapter extends RecyclerView.Adapter<RoomOptionRVAdapter.ViewHolder> {

    private ArrayList<RoomOptionData> items = new ArrayList<>();
    private HotelDetailActivity activity;
    private HotelDetailRoomData room;

    public RoomOptionRVAdapter(HotelDetailActivity activity, HotelDetailRoomData room){
        this.activity = activity;
        this.room = room;
    }

    @NonNull
    @Override
    public RoomOptionRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_option,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomOptionRVAdapter.ViewHolder viewHolder, int position){
        final RoomOptionData item = items.get(position);
        final View mView = viewHolder.itemView;
        String optionNo = "옵션" + item.getOptionNo();
        viewHolder.tvOptionNo.setText(optionNo);
        String originalPrice = "￦"+item.getOriginalPrice();
        viewHolder.tvOriginalPrice.setText(originalPrice);
        viewHolder.tvOriginalPrice.setPaintFlags(viewHolder.tvOriginalPrice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        String specialPrice = "￦"+item.getSpecialPrice();
        viewHolder.tvSpecialPrice.setText(specialPrice);
        viewHolder.tvOption1.setText("무료 와이파이");
        viewHolder.tvOption2.setText("무료 주차장");
        final String sale = "-"+item.getSale()+"%";
        viewHolder.tvSale.setText(sale);
        String stock = item.getStock() + mView.getContext().getResources().getString(R.string.stock);
        viewHolder.tvStock.setText(stock);
        String point = item.getPoint() + mView.getContext().getResources().getString(R.string.point);
        viewHolder.tvPoint.setText(point);
        String freeCancelDeadLine = item.getFreeCancelDeadLine();
        viewHolder.tvFreeCancelDeadLine.setText(freeCancelDeadLine);

        viewHolder.btnSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(MyApplication.isLogInStatus()) {
                    Intent intent = new Intent(v.getContext(), ReservationActivity.class);
                    String sDate = activity.sDate;
                    String eDate = activity.eDate;
                    int rNo = item.getRoomNum();
                    intent.putExtra("hotelName", activity.hotelName);
                    intent.putExtra("room",room.getGrade());
                    intent.putExtra("price", item.getSpecialPrice());
                    String option = "침대:" + room.getBed()+", 무료 와이파이, 무료 주차장";
                    intent.putExtra("option",option);
                    intent.putExtra("sDate", sDate);
                    intent.putExtra("eDate", eDate);
                    intent.putExtra("rNo", rNo);
                    v.getContext().startActivity(intent);
                }else{
                    Toast.makeText(mView.getContext(), mView.getContext().getResources().getString(R.string.login_to_reservation),Toast.LENGTH_LONG).show();
                    mView.getContext().startActivity(new Intent(mView.getContext(), LogInSignUpActivity.class));
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<RoomOptionData> items) {
        this.items = items;
    }

    class ViewHolder extends  RecyclerView.ViewHolder{
        TextView tvOptionNo, tvFreeCancelDeadLine, tvPoint, tvOriginalPrice, tvSpecialPrice, tvSale, tvStock, tvOption1, tvOption2;
        Button btnSelection;
        ViewHolder(View itemView){
            super(itemView);
            tvOptionNo = itemView.findViewById(R.id.option_textView);
            tvFreeCancelDeadLine = itemView.findViewById(R.id.freeCancelDeadLine_textView);
            tvPoint = itemView.findViewById(R.id.point_textView);
            tvOriginalPrice = itemView.findViewById(R.id.originalPrice_textView);
            tvSpecialPrice = itemView.findViewById(R.id.specialPrice_textView);
            tvSale = itemView.findViewById(R.id.sale_textView);
            tvStock = itemView.findViewById(R.id.stock_textView);
            tvOption1 = itemView.findViewById(R.id.option1_textView);
            tvOption2 = itemView.findViewById(R.id.option2_textView);
            btnSelection = itemView.findViewById(R.id.select_button);
        }
    }
}
