package com.honeybunch.app.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.honeybunch.app.R;

import com.honeybunch.app.models.MultiPLeData;

import java.util.List;

public class SimpleChipAdapter extends RecyclerView.Adapter<SimpleChipAdapter.ViewHolder> {

    List<MultiPLeData> item;
    private Context context;




    public SimpleChipAdapter(List<MultiPLeData> listCard, Context context) {
        this.item = listCard;
        this.context = context;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.select_simple_chip, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_chip_item.setText(item.get(position).getName());

        holder.itemView.setBackgroundColor(item.get(position).isSelected() ? R.color.select_color : Color.WHITE);
        holder.tv_chip_item.setTextColor(item.get(position).isSelected() ? R.color.black: Color.BLACK);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.get(position).setSelected(!item.get(position).isSelected());
                holder.itemView.setBackgroundColor(item.get(position).isSelected() ? R.color.select_color : Color.WHITE);
                holder.tv_chip_item.setTextColor(item.get(position).isSelected() ? R.color.white : Color.BLACK);

            }
        });
    }



    @Override
    public int getItemCount() {
        return item.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_chip_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_chip_item = itemView.findViewById(R.id.tv_chip_item);

        }


    }
}