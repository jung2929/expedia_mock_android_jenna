package com.example.expedia.adapter;

import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.expedia.MyApplication;
import com.example.expedia.R;
import com.example.expedia.activity.HotelDetailActivity;
import com.example.expedia.activity.LogInActivity;
import com.example.expedia.data.HotelListData;
import com.example.expedia.data.RoomOptionData;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RoomOptionRVAdapter extends RecyclerView.Adapter<RoomOptionRVAdapter.ViewHolder> {

    private ArrayList<RoomOptionData> items = new ArrayList<>();

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
        viewHolder.tvOriginalPrice.setText(item.getOriginalPrice());
        viewHolder.tvOriginalPrice.setPaintFlags(viewHolder.tvOriginalPrice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.tvSpecialPrice.setText(item.getSpecialPrice());
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
                    Toast.makeText(mView.getContext(), "방" + item.getRoomNum() + "을(를) 예약합니다.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(mView.getContext(), mView.getContext().getResources().getString(R.string.login_to_reservation),Toast.LENGTH_LONG).show();
                    mView.getContext().startActivity(new Intent(mView.getContext(), LogInActivity.class));
                }
                /*Intent intent = new Intent(v.getContext(), HotelDetailActivity.class);
                String sDate = item.getStartDate();
                String eDate = item.getEndDate();
                int hNo = item.gethNo();
                intent.putExtra("name", name);
                intent.putExtra("price", price);
                intent.putExtra("sale", sale);
                intent.putExtra("sDate", sDate);
                intent.putExtra("eDate", eDate);
                intent.putExtra("hNo", hNo);
                v.getContext().startActivity(intent);*/
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
