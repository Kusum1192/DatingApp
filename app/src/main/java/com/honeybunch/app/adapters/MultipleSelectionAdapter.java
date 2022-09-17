package com.honeybunch.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.honeybunch.app.R;
import com.honeybunch.app.models.MultiPLeData;

import java.util.List;

public class MultipleSelectionAdapter extends RecyclerView.Adapter<MultipleSelectionAdapter.ViewHolder> {

    List<MultiPLeData> item;
    private Context context;


    public MultipleSelectionAdapter(List<MultiPLeData> listCard, Context context) {
        this.item = listCard;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.select_multiple_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_purpose_date.setText(item.get(position).getName());
//        holder.itemView.setBackgroundColor(item.get(position).isSelected() ? R.color.select_color : Color.WHITE);

//        holder.tv_purpose_date.setTextColor(item.get(position).isSelected() ? R.color.black: Color.BLACK);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.get(position).setSelected(!item.get(position).isSelected());
                if (item.get(position).isSelected()) {
                    holder.itemView.setBackground(ContextCompat.getDrawable(context, R.drawable.custom_bg));

                } else {
                    holder.itemView.setBackground(ContextCompat.getDrawable(context, R.drawable.custom_wtout_bg));

                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return item.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_purpose_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_purpose_date = itemView.findViewById(R.id.tv_purpose_date);

        }


    }
}